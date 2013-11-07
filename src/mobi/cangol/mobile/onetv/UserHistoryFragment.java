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

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.onetv.adapter.UserHistoryAdapter;
import mobi.cangol.mobile.onetv.adapter.UserHistoryAdapter.OnStarClickListener;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.StationService;
import mobi.cangol.mobile.onetv.db.UserHistoryService;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;
import mobi.cangol.mobile.onetv.view.PromptView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @Description LeftMenuFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class UserHistoryFragment extends BaseContentFragment {
	private ListView listView;
	private PromptView listViewTips;
	private ArrayList<UserHistory> mItemList;
	private LoadMoreAdapter<UserHistory> loadMoreAdapter;
	private UserHistoryAdapter dataAdapter;
	private int page=1;
	private int pageSize=10;
	private UserHistoryService userHistoryService;
	private StationService stationService;
	private PopupMenu mPopupMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userHistoryService=new UserHistoryService(this.getActivity());
		stationService=new StationService(this.getActivity());
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
		listViewTips=(PromptView) view.findViewById(R.id.promptView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.menu_history);
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataAdapter = new UserHistoryAdapter(this.getActivity());
		loadMoreAdapter = new LoadMoreAdapter<UserHistory>(dataAdapter,mInflater.inflate(R.layout.common_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UserHistory item=dataAdapter.getItem(position);
				playStation(item);
			}
			
		});
		dataAdapter.setOnStarClickListener(new OnStarClickListener(){

			@Override
			public void onClick(View v, int position) {
				UserHistory item=dataAdapter.getItem(position);
				showPopuMenu(item,v);
			}
			
		});
		loadMoreAdapter.setOnLoadCallback(new OnLoadCallback(){

			@Override
			public boolean hasNext(int count) {
				return count>=page*pageSize;
			}

			@Override
			public void loadMoreData() {
					getUserHistoryList((page-1)*pageSize,pageSize);
					page++;
				}
		});
	}
	private void showPopuMenu(final UserHistory userHistory,View actionView){
		mPopupMenu=new PopupMenu(this.getActivity(),actionView);
		mPopupMenu.inflate(R.menu.list_action_menu);
		mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch(item.getItemId()){
				case R.id.menu_action_play:
					playStation(userHistory);
					break;
				case R.id.menu_action_delete:
					userHistoryService.delete(userHistory.get_id());
					dataAdapter.remove(userHistory);
					break;
				}
				mPopupMenu.dismiss();
				return true;
			}
		});
		mPopupMenu.show();
	}
	private void playStation(UserHistory userHistory){
		Intent intent=new Intent(this.getActivity(),PlayerActivity.class);
		intent.putExtra("station", stationService.findByStationoId(userHistory.getStationId()));
		this.startActivity(intent);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("items", mItemList);
	}
	protected void initData(Bundle savedInstanceState) {
		if(savedInstanceState!=null)
			mItemList=(ArrayList<UserHistory>) savedInstanceState.getSerializable("items");
		if(mItemList==null){
			getUserHistoryList((page-1)*pageSize,pageSize);
		}else{
			updateView(mItemList);
		}
		
	}
	private void getUserHistoryList(final long from,final long max){
		new AsyncTask<Void,Void,List<UserHistory>>(){

			@Override	
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected List<UserHistory> doInBackground(Void... params) {
				return userHistoryService.findList(from,  max);
			}
			@Override
			protected void onPostExecute(List<UserHistory> result) {
				super.onPostExecute(result);
				updateView(result);
			}
		}.execute();
	}
	private void updateView(List<UserHistory> list){
		if(page==1){
			dataAdapter.clear();
		}
		dataAdapter.addAll(list);
		if(dataAdapter.getCount()>0){
			listViewTips.showContent();
		}else{
			listViewTips.showEmpty();
		}
		loadMoreAdapter.addMoreComplete();	
		mItemList=dataAdapter.getItems();
	}
	@Override
	public boolean isCleanStack() {
		return true;
	}
}
