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

import mobi.cangol.mobile.onetv.db.UserRemindService;
import mobi.cangol.mobile.onetv.db.model.UserRemind;
import mobi.cangol.mobile.onetv.log.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Description AlarmReceiver.java
 * @author Cangol
 * @date 2013-11-5
 */
public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		UserRemindService userRemindService = new UserRemindService(context);
		int _id = intent.getIntExtra("remind_id", -1);
		UserRemind userRemind = userRemindService.find(_id);
		if (_id != -1&&userRemind!=null) {
				Log.d("提醒时间到:" + userRemind.getProgram());
				Intent intent2=new Intent(context,RemindActivity.class);
				intent2.putExtra("remind_id",_id);
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent2);
		} else {
			Log.d("没找到提醒内容,remind_id=-1");
		}
	}
}
