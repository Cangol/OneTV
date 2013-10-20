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

import mobi.cangol.mobile.onetv.base.BaseContentFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @Description LeftMenuFragment.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class LeftMenuFragment extends BaseContentFragment {
	private TextView historyTv;
	private TextView remindTv;
	private TextView favoriteTv;
	private TextView stationTv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_leftmenu, container,false);
		findViews(v);
		initViews(savedInstanceState);
		return v;
	}

	@Override
	protected void findViews(View view) {
		historyTv=(TextView) view.findViewById(R.id.menu_history);
		remindTv=(TextView) view.findViewById(R.id.menu_remind);
		favoriteTv=(TextView) view.findViewById(R.id.menu_favorites);
		stationTv=(TextView) view.findViewById(R.id.menu_station);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		stationTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(StationListFragment.class, "StationFragment",null);
			}
		
		});
		
		historyTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(UserHistoryFragment.class, "HistoryFragment",null);
			}
		
		});
		remindTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(UserRemindFragment.class, "RemindFragment",null);
			}
		
		});
		favoriteTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(UserFavoriteFragment.class, "FavoriteFragment",null);
			}
		
		});
	
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		
	}
}
