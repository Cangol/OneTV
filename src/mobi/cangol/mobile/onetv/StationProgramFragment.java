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

import java.util.List;

import mobi.cangol.mobile.onetv.adapter.StationAdapter;
import mobi.cangol.mobile.onetv.adapter.StationAdapter.OnStarClickListener;
import mobi.cangol.mobile.onetv.api.ApiContants;
import mobi.cangol.mobile.onetv.api.ApiHttpResult;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.StationService;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.log.Log;
import mobi.cangol.mobile.onetv.view.ListViewTips;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cangol.mobile.http.AsyncHttpClient;
import com.cangol.mobile.http.JsonHttpResponseHandler;
import com.cangol.mobile.http.RequestParams;

/**
 * @Description StationFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class StationProgramFragment extends BaseContentFragment {
	private ListView listView;
	private ListViewTips listViewTips;
	private LoadMoreAdapter<Station> loadMoreAdapter;
	private StationAdapter dataAdapter;
	private StationService stationService;
	private Station station;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		station=(Station) this.getArguments().getSerializable("station");
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
	protected void findViews(View view) {
		listView= (ListView) view.findViewById(R.id.listview);
		listViewTips=(ListViewTips) view.findViewById(R.id.listViewTips);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.menu_station);
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataAdapter = new StationAdapter(this.getActivity());
		loadMoreAdapter = new LoadMoreAdapter<Station>(dataAdapter,mInflater.inflate(R.layout.commons_list_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Station item=dataAdapter.getItem(position);
				playStation(item);
			}
			
		});
		dataAdapter.setOnStarClickListener(new OnStarClickListener(){

			@Override
			public void onClick(View v, int position) {
				
			}
			
		});
		loadMoreAdapter.setOnLoadCallback(new OnLoadCallback(){

			@Override
			public boolean hasNext(int count) {
				return false;
			}

			@Override
			public void loadMoreData() {
				}
		});
		initData();
	}
	private void playStation(Station station){
			Intent intent=new Intent(this.getActivity(),PlayerActivity.class);
			intent.putExtra("station", "right");
			this.startActivity(intent);
	}
	protected void initData() {
		AsyncHttpClient client=new AsyncHttpClient();
		RequestParams params=new RequestParams(ApiContants.stationSync(""));
		client.get(ApiContants.URL_STATION_SYNC, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				Log.d(response.toString());
				ApiHttpResult<Station> result=ApiHttpResult.parserObject(Station.class, response);
				List<Station> list=result.getList();
				for(Station station:list){
					stationService.save(station);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onStart() {
				super.onStart();
			}
			
		});
	}
	private void updateView(List<Station> list){
		dataAdapter.addAll(list);
		if(dataAdapter.getCount()>0){
			listViewTips.showContent();
		}else{
			listViewTips.showEmpty();
		}
		loadMoreAdapter.addMoreComplete();
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
	
}
