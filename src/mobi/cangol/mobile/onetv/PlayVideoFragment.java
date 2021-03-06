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

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.VideoView;
import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import mobi.cangol.mobile.onetv.db.UserHistoryService;
import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.log.Log;
import mobi.cangol.mobile.onetv.view.VideoPromptView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cangol.mobile.utils.TimeUtils;

/**
 * @Description PlayVideoFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class PlayVideoFragment extends BaseContentFragment {
	private Station station;
	private VideoView mVideoView;
	private VideoPromptView mVideoPromptView;
	private UserHistoryService userHistoryService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		station=(Station) this.getArguments().getSerializable("station");
		userHistoryService=new UserHistoryService(this.getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_playvideo, container,false);
		findViews(v);
		initViews(savedInstanceState);
		return v;
	}

	@Override
	protected void findViews(View view) {
		mVideoView = (VideoView) view.findViewById(R.id.play_videoview);
		mVideoPromptView= (VideoPromptView) view.findViewById(R.id.videoPromptView);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		//mVideoView.setMediaController(new MediaController(this.getActivity()));
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				mediaPlayer.setPlaybackSpeed(1.0f);
				mVideoPromptView.hide();
			}
		});
		mVideoView.setOnErrorListener(new OnErrorListener(){

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				switch(what){
					case MediaPlayer.MEDIA_ERROR_UNKNOWN:
						mp.reset();
						mVideoPromptView.showError();
						return true;
				}
				return false;
			}
		
		});
		mVideoView.setOnInfoListener(new OnInfoListener(){

			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
				switch(what){
					case MediaPlayer.MEDIA_INFO_BUFFERING_START:
						if(!mp.isPlaying())
						mVideoPromptView.showCaching(0);
					case MediaPlayer.MEDIA_INFO_BUFFERING_END:
						mVideoPromptView.hide();
				}
				return false;
			}
			
		});
		mVideoView.setOnBufferingUpdateListener(new OnBufferingUpdateListener(){

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				if(!mp.isPlaying())
				mVideoPromptView.showCaching(percent);
			}
			
		});
		mVideoPromptView.setRetryListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				playVideo(station.getUrl());
			}
			
		});
		playVideo(station.getUrl());
	}
	public void playVideo(String url){
		url=url.replace("ipad", "iphone");
		Log.d("play url="+url);
		mVideoView.setVideoPath(url);
		mVideoPromptView.showLoading();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(station!=null){
			UserHistory userHistory=userHistoryService.findByStationId(station.getId());
			if(userHistory==null){
				userHistory=new UserHistory();
				userHistory.setStationId(station.getId());
				userHistory.setStationName(station.getName());
				userHistory.setStationUrl(station.getUrl());
			}
			userHistory.setLastPlayTime(TimeUtils.getCurrentTime());
			userHistoryService.save(userHistory);
			Log.d("save history");
		}
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
	}
}
