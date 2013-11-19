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
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Vibrator;
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
	 private Vibrator mVibrator;
	private MediaPlayer mediaPlayer;
	 private static final long[] sVibratePattern = new long[] {100 , 200 };
	 // Volume suggested by media team for in-call alarms.
    private static final float IN_CALL_VOLUME = 0.125f;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		findViews();
		initViews();
		initData();
	}
	private void findViews(){
		 titleTv=(TextView) this.findViewById(R.id.dialog_title);
		 messageTv=(TextView) this.findViewById(R.id.dialog_message);
		 leftBtn=(Button) this.findViewById(R.id.dialog_left);
		 rightBtn=(Button) this.findViewById(R.id.dialog_right);
	}
	private void initViews(){
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
		
	}
	private void initData(){
		userRemindService = new UserRemindService(this);
		stationService=new StationService(this);
		_id=this.getIntent().getIntExtra("remind_id", -1);
		userRemind = userRemindService.find(_id);
		if(userRemind!=null){
			Log.d("提醒时间到:" + userRemind.getProgram());
			titleTv.setText("OneTv提醒");
			messageTv.setText(userRemind.getStationName()
					+ "<<" + userRemind.getProgram() + ">>将于" + userRemind.getPlayTime()
					+ "开始播放，请即时收看.");
			initAlarm();
		}
		userRemindService.delete(_id);
	}
	private void initAlarm(){
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.v("Error occurred while playing audio.");
                mp.stop();
                mp.release();
                mediaPlayer = null;
                return true;
            }
        });
		try {
			mediaPlayer.setVolume(IN_CALL_VOLUME, IN_CALL_VOLUME);
            setDataSourceFromResource(getResources(), mediaPlayer, R.raw.in_call_alarm);
			startAlarm(mediaPlayer);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
    private void startAlarm(MediaPlayer player)
            throws java.io.IOException, IllegalArgumentException,
                   IllegalStateException {
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        // do not play alarms if stream volume is 0
        // (typically because ringer mode is silent).
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(false);
            player.prepare();
            player.start();
        }
        mVibrator.vibrate(sVibratePattern, 0);
    }

    private void setDataSourceFromResource(Resources resources,
            MediaPlayer player, int res) throws java.io.IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());
            afd.close();
        }
    }
	private void playStationA(Station station){
		Intent intent=new Intent(this,PlayerActivity.class);
		intent.putExtra("station", station);
		this.startActivity(intent);
	}
}
