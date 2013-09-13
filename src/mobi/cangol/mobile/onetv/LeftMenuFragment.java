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

import mobi.cangol.mobile.onetv.adapter.VideoTvAdapter;
import mobi.cangol.mobile.onetv.adapter.VideoTvAdapter.OnStarClickListener;
import mobi.cangol.mobile.onetv.db.model.VideoTv;
import mobi.cangol.mobile.onetv.view.ListViewTips;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @Description LeftMenuFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class LeftMenuFragment extends BaseFragment {
	private PlayVideoChangeListener onChangeListener;
	private TextView historyTv;
	private TextView followTv;
	private TextView favoriteTv;
	private ListView listView;
	private ListViewTips listViewTips;
	private LoadMoreAdapter<VideoTv> loadMoreAdapter;
	private VideoTvAdapter videoTvAdapter;
	private int page=1;
	private int pageSize=15;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_leftmenu, container,false);
		findViews(v);
		initViews(savedInstanceState);
		return v;
	}

	@Override
	protected void findViews(View view) {
		historyTv=(TextView) view.findViewById(R.id.menu_user_history);
		followTv=(TextView) view.findViewById(R.id.menu_user_follow);
		favoriteTv=(TextView) view.findViewById(R.id.menu_user_favorites);
		listView= (ListView) view.findViewById(R.id.menu_listview);
		listViewTips=(ListViewTips) view.findViewById(R.id.listViewTips);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		historyTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
		
		});
		followTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
		
		});
		favoriteTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
		
		});
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		videoTvAdapter = new VideoTvAdapter(this.getActivity());
		loadMoreAdapter = new LoadMoreAdapter<VideoTv>(videoTvAdapter,mInflater.inflate(R.layout.commons_list_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				VideoTv item=videoTvAdapter.getItem(position);
				if(onChangeListener!=null)
				onChangeListener.OnChangeListener(item.get_id(), item.getUrl());
			}
			
		});
		videoTvAdapter.setOnStarClickListener(new OnStarClickListener(){

			@Override
			public void onClick(View v, int position) {
				
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
				}
		});
		initData();
	}
	protected void initData() {
		List<VideoTv> list=new ArrayList<VideoTv>();
		list.add(new VideoTv(1,"http://live.gslb.letv.com/gslb?stream_id=cctv1&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(2,"http://live.gslb.letv.com/gslb?stream_id=cctv2&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(3,"http://live.gslb.letv.com/gslb?stream_id=cctv3&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(4,"http://live.gslb.letv.com/gslb?stream_id=cctv4&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(5,"http://live.gslb.letv.com/gslb?stream_id=cctv5&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(6,"http://live.gslb.letv.com/gslb?stream_id=cctv6&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(7,"http://live.gslb.letv.com/gslb?stream_id=cctv7&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(8,"http://live.gslb.letv.com/gslb?stream_id=cctv8&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(9,"http://live.gslb.letv.com/gslb?stream_id=cctv9&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(10,"http://live.gslb.letv.com/gslb?stream_id=cctv10&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(11,"http://live.gslb.letv.com/gslb?stream_id=cctv11&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(12,"http://live.gslb.letv.com/gslb?stream_id=cctv12&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(13,"http://live.gslb.letv.com/gslb?stream_id=cctvnew&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(14,"http://live.gslb.letv.com/gslb?stream_id=cctvshaoer&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(15,"http://live.gslb.letv.com/gslb?stream_id=cctvmusic&tag=live&ext=m3u8&sign=live_iphone"));
		list.add(new VideoTv(16,"http://live.gslb.letv.com/gslb?stream_id=zhejiang&tag=live&ext=m3u8&sign=live_iphone"));
		updateView(list);
	}
	private void updateView(List<VideoTv> list){
		if(page==1){
			videoTvAdapter.clear();
		}
		videoTvAdapter.addAll(list);
		if(videoTvAdapter.getCount()>0){
			listViewTips.showContent();
		}else{
			listViewTips.showEmpty();
		}
		loadMoreAdapter.addMoreComplete();
	}
	public void setOnChangeListener(PlayVideoChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	
}
