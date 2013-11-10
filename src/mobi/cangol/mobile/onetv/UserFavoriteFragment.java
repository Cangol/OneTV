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

import com.google.analytics.tracking.android.MapBuilder;

import mobi.cangol.mobile.onetv.adapter.UserFavoriteAdapter;
import mobi.cangol.mobile.onetv.adapter.UserFavoriteAdapter.OnStarClickListener;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.StationService;
import mobi.cangol.mobile.onetv.db.UserFavoriteService;
import mobi.cangol.mobile.onetv.db.model.UserFavorite;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;
import mobi.cangol.mobile.onetv.view.PromptView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
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
public class UserFavoriteFragment extends BaseContentFragment {
	private ListView listView;
	private PromptView listViewTips;
	private ArrayList<UserFavorite> mItemList;
	private LoadMoreAdapter<UserFavorite> loadMoreAdapter;
	private UserFavoriteAdapter dataAdapter;
	private int page=1;
	private int pageSize=10;
	private UserFavoriteService userFavoriteService;
	private StationService stationService;
	private PopupMenu mPopupMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userFavoriteService=new UserFavoriteService(this.getActivity());
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
		this.setTitle(R.string.menu_favorites);
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataAdapter = new UserFavoriteAdapter(this.getActivity());
		loadMoreAdapter = new LoadMoreAdapter<UserFavorite>(dataAdapter,mInflater.inflate(R.layout.common_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UserFavorite item=dataAdapter.getItem(position);
				playStation(item);
				tracker.send(MapBuilder.createEvent("ui_action", "Click", "item", null).build());
			}
			
		});
		dataAdapter.setOnStarClickListener(new OnStarClickListener(){

			@Override
			public void onClick(View v, int position) {
				UserFavorite item=dataAdapter.getItem(position);
				showPopuMenu(item,v);
				tracker.send(MapBuilder.createEvent("ui_action", "Click", "item action", null).build());
			}
			
		});
		loadMoreAdapter.setOnLoadCallback(new OnLoadCallback(){

			@Override
			public boolean hasNext(int count) {
				return count>=page*pageSize;
			}

			@Override
			public void loadMoreData() {
					getUserFavoriteList((page-1)*pageSize,pageSize);
					page++;
					tracker.send(MapBuilder.createEvent("ui_action", "scroll", "list", null).build());
				}
		});
	}
	private void showPopuMenu(final UserFavorite userFavorite,View actionView){
		mPopupMenu=new PopupMenu(this.getActivity(),actionView);
		mPopupMenu.inflate(R.menu.list_action_menu);
		mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch(item.getItemId()){
				case R.id.menu_action_play:
					playStation(userFavorite);
					tracker.send(MapBuilder.createEvent("ui_action", "Click", "menu play", null).build());
					break;
				case R.id.menu_action_delete:
					userFavoriteService.delete(userFavorite.get_id());
					dataAdapter.remove(userFavorite);
					tracker.send(MapBuilder.createEvent("ui_action", "Click", "menu del", null).build());
					break;
				}
				mPopupMenu.dismiss();
				return true;
			}
		});
		mPopupMenu.show();
	}
	private void playStation(UserFavorite userFavorite){
		Intent intent=new Intent(this.getActivity(),PlayerActivity.class);
		intent.putExtra("station", stationService.findByStationoId(userFavorite.getStationId()));
		this.startActivity(intent);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("items", mItemList);
	}
	protected void initData(Bundle savedInstanceState) {
		if(savedInstanceState!=null)
			mItemList=(ArrayList<UserFavorite>) savedInstanceState.getSerializable("items");
		if(mItemList==null){
			getUserFavoriteList((page-1)*pageSize,pageSize);
		}else{
			updateView(mItemList);
		}
	}
	private void getUserFavoriteList(final long from,final long max){
		new AsyncTask<Void,Void,List<UserFavorite>>(){

			@Override	
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected List<UserFavorite> doInBackground(Void... params) {
				return userFavoriteService.findList(from,  max);
			}
			@Override
			protected void onPostExecute(List<UserFavorite> result) {
				super.onPostExecute(result);
				updateView(result);
			}
		}.execute();
	}
	private void updateView(List<UserFavorite> list){
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
