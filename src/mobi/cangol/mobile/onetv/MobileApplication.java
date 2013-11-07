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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mobi.cangol.mobile.onetv.utils.Contants;

import android.app.Activity;
import android.app.Application;

/**
 * @Description OneTvApplication.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class MobileApplication  extends Application {
		public HashMap<String,Object> session;
		public final String TAG=Contants.makeLogTag(MobileApplication.class);
		public List<Activity> activityManager;
		public File root;
		@Override
		public void onCreate() {
			super.onCreate();
			init();
		}
		public void init(){
			activityManager=new ArrayList<Activity>();
			session=new  HashMap<String,Object>();
		}
		public void exit() {
			session.clear();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
}
