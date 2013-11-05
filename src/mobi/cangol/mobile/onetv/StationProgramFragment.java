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
import java.util.Calendar;
import java.util.List;

import mobi.cangol.mobile.onetv.adapter.ProgramAdapter;
import mobi.cangol.mobile.onetv.adapter.ProgramAdapter.OnActionClickListener;
import mobi.cangol.mobile.onetv.api.ApiContants;
import mobi.cangol.mobile.onetv.api.CommSAXParserUtil;
import mobi.cangol.mobile.onetv.api.WebServicesFeed;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.UserFavoriteService;
import mobi.cangol.mobile.onetv.db.UserRemindService;
import mobi.cangol.mobile.onetv.db.model.Program;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.db.model.UserFavorite;
import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.db.model.UserRemind;
import mobi.cangol.mobile.onetv.log.Log;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter;
import mobi.cangol.mobile.onetv.view.LoadMoreAdapter.OnLoadCallback;
import mobi.cangol.mobile.onetv.view.PromptView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cangol.mobile.http.AsyncHttpClient;
import com.cangol.mobile.http.AsyncHttpResponseHandler;
import com.cangol.mobile.http.RequestParams;
import com.cangol.mobile.utils.TimeUtils;

/**
 * @Description StationFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class StationProgramFragment extends BaseContentFragment {
	private TextView nameTv;
	private TextView descTv;
	private ImageView logoImg;
	private ImageView favoriteImg;
	private ListView listView;
	private PromptView listViewTips;
	private ArrayList<Program> mItemList;
	private LoadMoreAdapter<Program> loadMoreAdapter;
	private ProgramAdapter dataAdapter;
	private Station station;
	private UserFavoriteService userFavoriteService;
	private UserRemindService userRemindService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userRemindService=new UserRemindService(this.getActivity());
		userFavoriteService=new UserFavoriteService(this.getActivity());
		station=(Station) this.getArguments().getSerializable("station");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_program, container,false);
		findViews(v);
		initViews(savedInstanceState);
		updateFavoriteView();
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}
	@Override
	protected void findViews(View view) {
		nameTv=(TextView) view.findViewById(R.id.station_name);
		descTv=(TextView) view.findViewById(R.id.station_desc);
		logoImg=(ImageView) view.findViewById(R.id.station_logo);
		favoriteImg=(ImageView) view.findViewById(R.id.station_favorite);
		listView= (ListView) view.findViewById(R.id.listview);
		listViewTips=(PromptView) view.findViewById(R.id.promptView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(station.getName());
		LayoutInflater mInflater=(LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataAdapter = new ProgramAdapter(this.getActivity());
		loadMoreAdapter = new LoadMoreAdapter<Program>(dataAdapter,mInflater.inflate(R.layout.common_view_footer,null));
		loadMoreAdapter.setAbsListView(listView);
		listView.setAdapter(loadMoreAdapter);
		dataAdapter.setOnActionClickListener(new OnActionClickListener(){

			@Override
			public void onClick(View v, int position) {
			    Program item=dataAdapter.getItem(position);
			    String[] time=item.getPlayTime().split(":");
			    if(time!=null&&time.length==2){
			    	 int hour=Integer.parseInt(time[0]);
					 int minute=Integer.parseInt(time[1]);
					 addAlarm(item,hour,minute);
			    }else{
			    	Log.d("PlayTime 格式错误!");
			    }
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
		logoImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				playStation(station);
			}
			
		});
		favoriteImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					UserFavorite userFavorite=userFavoriteService.findByStationId(station.getId());
					if(userFavorite==null){
						userFavorite=new UserFavorite();
						userFavorite.setStationId(station.getId());
						userFavorite.setStationName(station.getName());
						userFavorite.setStationUrl(station.getUrl());
						userFavorite.setLastPlayTime(TimeUtils.getCurrentTime());
						userFavoriteService.save(userFavorite);
						favoriteImg.setImageResource(R.drawable.ic_star_selected);
						Toast.makeText(getActivity(), R.string.add_favorite_success, Toast.LENGTH_SHORT).show();
					}else{
						userFavoriteService.delete(userFavorite.get_id());
						favoriteImg.setImageResource(R.drawable.ic_star_unselected);
						Toast.makeText(getActivity(), R.string.del_favorite_success, Toast.LENGTH_SHORT).show();
					}
			}
			
		});
		nameTv.setText(station.getName());
		descTv.setText(station.getDesc());
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("items", mItemList);
	}
	@Override
	protected void initData(Bundle savedInstanceState) {
		if(savedInstanceState!=null)
			mItemList=(ArrayList<Program>) savedInstanceState.getSerializable("items");
		if(mItemList==null){
			getStationProgram();
		}else{
			updateView(mItemList);
		}
		
	}
	private void addAlarm(final Program program,int hour,int minute){
			    final Calendar calendar=Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker arg0, int h, int m) {
						
						UserRemind userRemind=new UserRemind();
						userRemind.setStationId(station.getId());
						userRemind.setStationName(station.getName());
						userRemind.setStationUrl(station.getUrl());
						userRemind.setPlayTime(program.getPlayTime());
						userRemind.setProgram(program.getTvProgram());
						int _id=userRemindService.save(userRemind);
						Log.d("save  _id=" + _id);
						//设置日历的时间，主要是让日历的年月日和当前同步
						calendar.setTimeInMillis(System.currentTimeMillis());
						//设置日历的小时和分钟
						calendar.set(Calendar.HOUR_OF_DAY, h);
						calendar.set(Calendar.MINUTE, m);
						//将秒和毫秒设置为0
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
						//建立Intent和PendingIntent来调用闹钟管理器
						Intent intent = new Intent(getActivity(),AlarmReceiver.class);
						intent.putExtra("remind_id", _id);
						PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
						//获取闹钟管理器
						AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
						//设置闹钟
						alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
						//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);
						Toast.makeText(getActivity(), "设置提醒的时间："+String.valueOf(h)+":"+String.valueOf(m), Toast.LENGTH_SHORT).show();
					}
				},hour,minute,true).show();				
	}
	private void playStation(Station station){
			Intent intent=new Intent(this.getActivity(),PlayerActivity.class);
			intent.putExtra("station", station);
			this.startActivity(intent);
	}
	protected void getStationProgram() {
		AsyncHttpClient client=new AsyncHttpClient();
		RequestParams params=new RequestParams(ApiContants.stationProgram(station.getId(), TimeUtils.getCurrentDate(), ""));
		client.get(ApiContants.URL_STATION_PROGRAM, params, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				Log.d(response);
				CommSAXParserUtil parserUtil=new CommSAXParserUtil();
				WebServicesFeed feed=(WebServicesFeed)parserUtil.getFeed(CommSAXParserUtil.WEBSERVICES_HANDLER, response);
				if(feed==null){
					return;
				}
				String[] temp=new String[3];
				List<Program>   programs= new ArrayList<Program>();
				for (int i = 0; i < feed.getAllData().size(); i++) {
					temp=feed.getAllData().get(i).split("@@@");
					if(temp[0].length()>=8){
						programs.add(new Program(temp[0].substring(0, 5),temp[0].substring(6, 8),temp[1],temp[2]));
					}
				}
				updateView(programs);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				Log.d(" onFailure:"+content);
				listViewTips.showEmpty(content);
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onStart() {
				super.onStart();
				listViewTips.showLoading();
			}
			
		});
	}
	private void updateFavoriteView(){
		UserFavorite userFavorite=userFavoriteService.findByStationId(station.getId());
		if(userFavorite==null){
			favoriteImg.setImageResource(R.drawable.ic_star_unselected);
		}else{
			favoriteImg.setImageResource(R.drawable.ic_star_selected);
		}
	}
	private void updateView(List<Program> list){
		dataAdapter.addAll(list);
		if(dataAdapter.getCount()>0){
			listViewTips.showContent();
		}else{
			listViewTips.showEmpty();
		}
		loadMoreAdapter.addMoreComplete();
		mItemList=dataAdapter.getItems();
	}
}
