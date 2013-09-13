package mobi.cangol.mobile.onetv;

import java.util.Stack;

import com.cangol.mobile.logging.Log;

import mobi.cangol.mobile.onetv.utils.Contants;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseFragmentActivity extends FragmentActivity {
	protected String TAG = Contants
			.makeLogTag(BaseFragmentActivity.class);
	protected OneTvApplication app;
	private Stack<BaseFragment> backStack = new Stack<BaseFragment>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (OneTvApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
	}

	abstract protected void findViews();

	abstract protected void initViews(Bundle savedInstanceState);

	abstract protected void initData(Bundle savedInstanceState);

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
	protected void onDestroy() {
		super.onDestroy();
		delActivityFromManager(this);
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

	@Override
	public Object getLastCustomNonConfigurationInstance() {
		Log.v(TAG, "getLastCustomNonConfigurationInstance");
		return super.getLastCustomNonConfigurationInstance();
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		Log.v(TAG, "onRetainCustomNonConfigurationInstance");
		return super.onRetainCustomNonConfigurationInstance();
	}

	@Override
	public void onBackPressed() {
		Log.v(TAG, "onBackPressed");
		if (backStack.size() <= 1) {
			// alert exit dialog
			super.onBackPressed();
		} else {
			if (backStack.peek().onBackPressed()) {
				return;
			} else {
				backStack.pop();
				this.getSupportFragmentManager().popBackStackImmediate();
				return;
			}
		}
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

	public void popBackStack() {
		backStack.pop();
	}

	public void pushBackStack(BaseFragment baseFragment) {
		backStack.push(baseFragment);
	}

	public void replaceFragment(int contId, BaseFragment baseFragment,
			String tag) {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(contId, baseFragment, tag)
				.addToBackStack(baseFragment.getClass().getSimpleName())
				.commit();
		pushBackStack(baseFragment);
	}

	public void addFragment(int contId, BaseFragment baseFragment, String tag) {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction().add(contId, baseFragment, tag)
				.addToBackStack(baseFragment.getClass().getSimpleName())
				.commit();
		pushBackStack(baseFragment);
	}

	/** commons method **/

	protected void showToast(int strId) {
		Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
