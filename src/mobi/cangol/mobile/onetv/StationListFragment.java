/** 
 * Copyright (c) 2013 Cangol.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mobi.cangol.mobile.onetv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import mobi.cangol.mobile.onetv.adapter.StationAdapter;
import mobi.cangol.mobile.onetv.adapter.StationAdapter.OnActionClickListener;
import mobi.cangol.mobile.onetv.api.ApiHttpResult;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.StationService;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.log.Log;
import mobi.cangol.mobile.onetv.view.PromptView;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @Description StationFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class StationListFragment extends BaseContentFragment {
	private ListView listView;
	private PromptView promptView;
	private LoadMoreAdapter<Station> loadMoreAdapter;
	private StationAdapter dataAdapter;
	private int page=1;
	private int pageSize=10;
	private StationService stationService;
	private String position;
	private boolean tvIsInit=false;
	private SharedPreferences sp ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stationService=new StationService(this.getActivity());
		position=this.getArguments().getString("position");
		 sp = this.getActivity().getSharedPreferences("OneTv", Context.MODE_PRIVATE);
		tvIsInit=sp.getBoolean("tv", false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, container,false);
		findViews(v);
		initViews(savedInstanceState);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	protected void findViews(View view) {
		listView= (ListView) view.findViewById(R.id.listview);
		promptView=(PromptView) view.findViewById(R.id.promptView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.menu_station);
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataAdapter = new StationAdapter(this.getActivity());
		if("left".equals(position)){
			dataAdapter.setLeft(true);
		}
		loadMoreAdapter = new LoadMoreAdapter<Station>(dataAdapter,mInflater.inflate(R.layout.common_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Station item=dataAdapter.getItem(position);
				if("left".equals(StationListFragment.this.position)){
					playStationF(item);
				}else{
					toStationProgram(item);
				}
			}
			
		});
		dataAdapter.setOnActionClickListener(new OnActionClickListener(){

			@Override
			public void onClick(View v, int position) {
				Station item=dataAdapter.getItem(position);
				if("left".equals(StationListFragment.this.position)){
					playStationF(item);
				}else{
					playStationA(item);
				}
			}
			
		});
		loadMoreAdapter.setOnLoadCallback(new OnLoadCallback(){

			@Override
			public boolean hasNext(int count) {
				return count>=page*pageSize;
			}

			@Override
			public void loadMoreData() {
				    page++;
					getStationList((page-1)*pageSize,pageSize);
		     }
		});
	}
	private void toStationProgram(Station station){
		Bundle bundle=new Bundle();
		bundle.putSerializable("station", station);
		this.setContentFragment(StationProgramFragment.class, "StationProgramFragment", bundle);
	}
	private void playStationF(Station station){
		Bundle bundle=new Bundle();
		bundle.putSerializable("station", station);
		this.setContentFragment(PlayVideoFragment.class, "PlayVideoFragment", bundle);
	}
	private void playStationA(Station station){
		Intent intent=new Intent(this.getActivity(),PlayerActivity.class);
		intent.putExtra("station", station);
		this.startActivity(intent);
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		if(!tvIsInit){
			initTvData();
			sp.edit().putBoolean("tv", true).commit();
			tvIsInit=true;
		}
		getStationList((page-1)*pageSize,pageSize);
	}
	private void updateView(List<Station> list){
		if(page==1){
			dataAdapter.clear();
		}
		loadMoreAdapter.addMoreData(list);
		if(loadMoreAdapter.getCount()>0){
			promptView.showContent();
		}else{
			promptView.showEmpty();
		}
		loadMoreAdapter.addMoreComplete();
	}
	public  String inputStream2String(InputStream   is)   throws   IOException{ 
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
        int   i=-1; 
        while((i=is.read())!=-1){ 
        	baos.write(i); 
        } 
       return   baos.toString(); 
	}
	private void parserTvData(){
		String response =null;
		ApiHttpResult<Station> result = null;
		try {
			response =inputStream2String(this.getResources().openRawResource(R.raw.tv));
			result = ApiHttpResult.parserObject(Station.class, new JSONObject(response));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Station> list=result.getList();
		stationService.deleteAll();
		for(Station station:list){
			stationService.save(station);
		}
	}
	protected void initTvData() {
		new AsyncTask<Void,Void,Void>(){

			@Override	
			protected void onPreExecute() {
				super.onPreExecute();
				promptView.showLoading();
			}

			@Override
			protected Void doInBackground(Void... params) {
				parserTvData();
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.execute();
		
//		AsyncHttpClient client=new AsyncHttpClient();
//		RequestParams params=new RequestParams(ApiContants.stationSync(""));
//		client.get(ApiContants.URL_STATION_SYNC, params, new JsonHttpResponseHandler(){
//
//			@Override
//			public void onSuccess(JSONObject response) {
//				super.onSuccess(response);
//				Log.d(response.toString());
//				ApiHttpResult<Station> result=ApiHttpResult.parserObject(Station.class, response);
//				List<Station> list=result.getList();
//				for(Station station:list){
//					stationService.save(station);
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable error, String content) {
//				super.onFailure(error, content);
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//			}
//
//			@Override
//			public void onStart() {
//				super.onStart();
//			}
//			
//		});
	}
	private void getStationList(final long from,final long max){
		new AsyncTask<Void,Void,List<Station>>(){

			@Override	
			protected void onPreExecute() {
				super.onPreExecute();
				if(from==0)
				promptView.showLoading();
			}

			@Override
			protected List<Station> doInBackground(Void... params) {
				return stationService.findList(from,  max);
			}
			@Override
			protected void onPostExecute(List<Station> result) {
				super.onPostExecute(result);
				Log.d(TAG, "from ="+from+"max="+max+"result="+result.size());
				updateView(result);
			}
		}.execute();
	}
	public boolean isCleanStack(){
		return true;
	}
}
