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

import mobi.cangol.mobile.onetv.db.StationService;
import mobi.cangol.mobile.onetv.db.UserRemindService;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.db.model.UserRemind;
import mobi.cangol.mobile.onetv.log.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @Description RemindActivity.java 
 * @author Cangol
 * @date 2013-11-5
 */
public class RemindActivity extends Activity {
	private TextView titleTv;
	private TextView messageTv;
	private Button leftBtn;
	private Button rightBtn;
	
	private UserRemindService userRemindService =null;
	private UserRemind userRemind =null;
	private int _id =-1;
	
	private StationService stationService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		findViews();
		initViews();
	}
	private void findViews(){
		 titleTv=(TextView) this.findViewById(R.id.dialog_title);
		 messageTv=(TextView) this.findViewById(R.id.dialog_message);
		 leftBtn=(Button) this.findViewById(R.id.dialog_left);
		 rightBtn=(Button) this.findViewById(R.id.dialog_right);
	}
	private void initViews(){
		userRemindService = new UserRemindService(this);
		stationService=new StationService(this);
		_id=this.getIntent().getIntExtra("remind_id", -1);
		userRemind = userRemindService.find(_id);
		Log.d("提醒时间到:" + userRemind.getProgram());
		titleTv.setText("OneTv提醒");
		messageTv.setText(userRemind.getStationName()
				+ "<<" + userRemind.getProgram() + ">>将于" + userRemind.getPlayTime()
				+ "开始播放，请即时收看.");
		leftBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Station item=stationService.find(_id);
				playStationA(item);
				finish();
			}
		});
		rightBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		//userRemindService.delete(_id);
	}
	private void playStationA(Station station){
		Intent intent=new Intent(this,PlayerActivity.class);
		intent.putExtra("station", station);
		this.startActivity(intent);
	}
}
