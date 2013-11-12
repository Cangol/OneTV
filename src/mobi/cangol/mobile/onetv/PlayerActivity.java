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

import java.util.List;

import io.vov.vitamio.LibsChecker;
import mobi.cangol.mobile.onetv.base.BaseFragment;
import mobi.cangol.mobile.onetv.base.BaseSlidingFragmentActivity;
import mobi.cangol.mobile.onetv.db.model.Station;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class PlayerActivity extends BaseSlidingFragmentActivity {
	private Station station;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
	              WindowManager.LayoutParams. FLAG_FULLSCREEN);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.activity_play);
		if(this.getIntent()!=null){
			station=(Station) this.getIntent().getSerializableExtra("station");
		}
		if(savedInstanceState==null){
			Bundle bundle=new Bundle();
			bundle.putSerializable("position", "left");
			this.setMenuFragment(StationListFragment.class, "StationListFragment", bundle);
			
			Bundle bundle2=new Bundle();
			bundle2.putSerializable("station", station);
			this.setContentFragment(PlayVideoFragment.class, "PlayVideoFragment", bundle2);
		}
		this.getSupportActionBar().hide();
		findViews();
		initViews(savedInstanceState);
		this.setSlidingActionBarEnabled(true);
	}

	@Override
	protected void findViews() {
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
	}


	@Override
	protected void initData(Bundle savedInstanceState) {
	}
	@Override
   public void setContentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		this.getFragmentStackManager().clear();
		super.setContentFragment(fragmentClass, tag, args);
	}

	@Override
	public void onBack() {
		List<Activity> activitys=this.app.activityManager;
		boolean isHave=false;
		for(Activity activity :activitys){
			if(activity instanceof MainActivity){
				isHave=true;
				break;
			}
		}
		if(!isHave){
			Intent intent=new Intent(this,MainActivity.class);
			this.startActivity(intent);
		}
		finish();
	}
	
}
