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

import mobi.cangol.mobile.onetv.base.BaseSlidingFragmentActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends BaseSlidingFragmentActivity {
	private boolean isExit=false;
	private final static int exit_time=2000;
	public Handler handler=new Handler(){
		@Override
		 public void handleMessage(Message msg) {
		 }
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState==null){
			this.setMenuFragment(LeftMenuFragment.class, "LeftMenuFragment", null);
			Bundle bundle=new Bundle();
			bundle.putSerializable("position", "right");
			this.setContentFragment(StationListFragment.class, "StationFragment", bundle);
		}
		findViews();
		initViews(savedInstanceState);
		this.setSlidingActionBarEnabled(false);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
	        case R.id.menu_about:
	        	this.setContentFragment(AboutusFragment.class, "AboutusFragment", null);
	            return true;
	        case R.id.menu_rating:
	        	toRating();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private void toRating(){
		Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		try{
			startActivity(intent);
		}catch(ActivityNotFoundException e){
			Toast.makeText(this, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onBack() {
		if(isExit){
			finish();
			app.exit();
		}else{
			Toast.makeText(this, R.string.exit_tips,exit_time).show();
			isExit=true;
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					synchronized (app) {
						isExit=false;
					}
				}
			},exit_time);
		}
	}
}
