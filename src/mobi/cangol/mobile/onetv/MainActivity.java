/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mobi.cangol.mobile.onetv;

import io.vov.vitamio.LibsChecker;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.FrameLayout;

import com.cangol.mobile.logging.Log;

public class MainActivity extends BaseFragmentActivity {
	private DrawerLayout mDrawerLayout;
	private FrameLayout leftFrame;
	private LeftMenuFragment leftMenuFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		Log.setLogLevelFormat(android.util.Log.INFO, false);
		findViews();
		initViews(savedInstanceState);
	}

	@Override
	protected void findViews() {
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		leftFrame = (FrameLayout) this.findViewById(R.id.left_drawer);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		leftMenuFragment = (LeftMenuFragment) this.getSupportFragmentManager()
				.findFragmentById(R.id.left_fragment);
		leftMenuFragment.setOnChangeListener(new PlayVideoChangeListener() {

			@Override
			public void OnChangeListener(int id, String url) {
				playVideoFragment(url);
				mDrawerLayout.closeDrawer(leftFrame);
			}

		});
	}
	
	@Override
	public void onBackPressed() {
		if(mDrawerLayout.isDrawerOpen(leftFrame)){
			mDrawerLayout.closeDrawer(leftFrame);
			return;
		}else{
			PlayVideoFragment playVideoFragment=(PlayVideoFragment) this.getSupportFragmentManager().findFragmentByTag("PlayVideoFragment");
			if(playVideoFragment.onBackPressed()){
				return;
			}
		}
		super.onBackPressed();
	}

	private void playVideoFragment(String url) {
		Log.d("playVideo url=" + url);
		PlayVideoFragment playVideoFragment = new PlayVideoFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		playVideoFragment.setArguments(bundle);
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, playVideoFragment,"PlayVideoFragment").commit();
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
}