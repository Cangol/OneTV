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
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Description PlayVideoFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class PlayVideoFragment extends BaseFragment {
	private String url="";
	private VideoView mVideoView;
	private RelativeLayout mCacheLayout;
	private TextView mBufferPercentTv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		url=this.getArguments().getString("url");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_playvideo, container,false);
		findViews(v);
		initViews(v, savedInstanceState);
		return v;
	}

	@Override
	protected void findViews(View view) {
		mVideoView = (VideoView) view.findViewById(R.id.play_videoview);
		mCacheLayout= (RelativeLayout) view.findViewById(R.id.play_layout_cache);
		mBufferPercentTv= (TextView) view.findViewById(R.id.play_buffer_percent);
	}

	@Override
	protected void initViews(View view, Bundle savedInstanceState) {
		mVideoView.setMediaController(new MediaController(this.getActivity()));
		mVideoView.setZOrderOnTop(false);
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				mediaPlayer.setPlaybackSpeed(1.0f);
			}
		});
		mVideoView.setOnErrorListener(new OnErrorListener(){

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				return false;
			}
		
		});
		mVideoView.setOnInfoListener(new OnInfoListener(){

			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
				switch(what){
					case MediaPlayer.MEDIA_INFO_BUFFERING_START:
						mCacheLayout.setVisibility(View.VISIBLE);
					case MediaPlayer.MEDIA_INFO_BUFFERING_END:
						mCacheLayout.setVisibility(View.GONE);
				}
				return false;
			}
			
		});
		mVideoView.setOnBufferingUpdateListener(new OnBufferingUpdateListener(){

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				mBufferPercentTv.setText(percent+"%");
			}
			
		});
		playVideo(url);
	}
	public void playVideo(String url){
		mVideoView.setVideoPath(url);
		mVideoView.requestFocus();
	}
	
}
