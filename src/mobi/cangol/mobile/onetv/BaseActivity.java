package mobi.cangol.mobile.onetv;

import com.cangol.mobile.logging.Log;

import mobi.cangol.mobile.onetv.utils.Contants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 * @date: Jun 2, 2011
 * @time: 1:13:13 PM
 */
public abstract class BaseActivity extends Activity {
	protected String TAG = Contants.makeLogTag(BaseActivity.class);
	public OneTvApplication app;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (OneTvApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
	}

	protected abstract void findViews();

	protected abstract void initViews(Bundle savedInstanceState);

	protected abstract void initData(Bundle savedInstanceState);

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.v(TAG, "onNewIntent");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v(TAG, "onRestart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.v(TAG, "onSaveInstanceState");
	}

	protected void addActivityToManager(Activity act) {
		Log.v(TAG, "addActivityToManager");
		if (!app.activityManager.contains(act)) {
			Log.v(TAG, "addActivityToManager, name = "
					+ act.getClass().getSimpleName());
			app.activityManager.add(act);
		}
	}

	protected void closeAllActivities() {
		Log.v(TAG, "closeAllActivities");
		for (final Activity act : app.activityManager) {
			if (act != null) {
				act.finish();
			}
		}
	}

	protected void delActivityFromManager(Activity act) {
		Log.v(TAG, "delActivityFromManager");
		if (app.activityManager.contains(act)) {
			app.activityManager.remove(act);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		delActivityFromManager(this);
	}

	@Override
	public void onBackPressed() {
		Log.v(TAG, "onBackPressed");
		super.onBackPressed();
	}

	/** commons method **/

	protected void showToast(int strId) {
		Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
