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
import mobi.cangol.mobile.onetv.base.BaseSlidingFragmentActivity;
import android.os.Bundle;

import com.cangol.mobile.logging.Log;

public class MainActivity extends BaseSlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		Log.setLogLevelFormat(android.util.Log.INFO, false);
		if(savedInstanceState==null){
			this.setMenuFragment(LeftMenuFragment.class, "LeftMenuFragment", null);
			//this.setContentFragment(LeftMenuFragment.class, "LeftMenuFragment", null);
		}
		
		findViews();
		initViews(savedInstanceState);
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
}
