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

import mobi.cangol.mobile.onetv.base.BaseSlidingFragmentActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cangol.mobile.logging.Log;

public class MainActivity extends BaseSlidingFragmentActivity {
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
	public void onBack() {
		finish();
	}
}
