package mobi.cangol.mobile.onetv.base;

import mobi.cangol.mobile.onetv.MobileApplication;
import mobi.cangol.mobile.onetv.R;
import mobi.cangol.mobile.onetv.navigation.SlidingFragmentActivity;
import mobi.cangol.mobile.onetv.navigation.SlidingMenu;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.WindowManager;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseSlidingFragmentActivity extends SlidingFragmentActivity {
	protected String TAG = Contants.makeLogTag(BaseSlidingFragmentActivity.class);
	private static final boolean LIFECYCLE=Contants.LIFECYCLE;
	protected MobileApplication app;
	private BaseMenuFragment menuFragment;
	private FragmentStackManager stack;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (MobileApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		// customize the SlidingMenu
		final SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindWidthRes(R.dimen.behindWidth);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		this.setSlidingActionBarEnabled(false);
		stack = FragmentStackManager.forContainer(this, R.id.content_frame,this.getSupportFragmentManager());
		if(savedInstanceState==null){
		}else{
			stack.restoreState(savedInstanceState);
		}
		
	}

	final public void setMenuFragment(Class<? extends BaseMenuFragment> fragmentClass,String tag,Bundle args) {
		menuFragment = (BaseMenuFragment) Fragment.instantiate(this,fragmentClass.getName(), args);
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		t.replace(R.id.menu_frame, menuFragment);
		t.commit();
	}
	final public void setContentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int moduleId) {
		this.setContentFragment(fragmentClass,tag,args);
		menuFragment.onContentChange(moduleId);
	}
	final public void setContentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		stack.replace(fragmentClass, tag,args);
		stack.commit();
	}
	abstract protected void findViews();

	abstract protected void initViews(Bundle savedInstanceState);

	abstract protected void initData(Bundle savedInstanceState);

	@Override
	protected void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(LIFECYCLE)Log.v(TAG, "onRestart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		delActivityFromManager(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		stack.saveState(outState);
		super.onSaveInstanceState(outState);
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
	}

	@Override
	public Object getLastCustomNonConfigurationInstance() {
		if(LIFECYCLE)Log.v(TAG, "getLastCustomNonConfigurationInstance");
		return super.getLastCustomNonConfigurationInstance();
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		if(LIFECYCLE)Log.v(TAG, "onRetainCustomNonConfigurationInstance");
		return super.onRetainCustomNonConfigurationInstance();
	}
	@Override
	final public void onBackPressed() {
		//if(LIFECYCLE)
		Log.v(TAG, "onBackPressed ");
		if (stack.size() <= 1) {
			onBack();
		} else {
			if (stack.peek().onBackPressed()) {
				return;
			} else {
				stack.pop();
				return;
			}
		}
	}
	public void onBack(){
		if(LIFECYCLE)Log.v(TAG, "onBack");
		super.onBackPressed();
	}
	protected void addActivityToManager(Activity act) {
		if(LIFECYCLE)Log.v(TAG, "addActivityToManager");
		if (!app.activityManager.contains(act)) {
			if(LIFECYCLE)Log.v(TAG, "addActivityToManager, name = "
					+ act.getClass().getSimpleName());
			app.activityManager.add(act);
		}
	}

	protected void closeAllActivities() {
		if(LIFECYCLE)Log.v(TAG, "closeAllActivities");
		for (final Activity act : app.activityManager) {
			if (act != null) {
				act.finish();
			}
		}
	}

	protected void delActivityFromManager(Activity act) {
		if(LIFECYCLE)Log.v(TAG, "delActivityFromManager");
		if (app.activityManager.contains(act)) {
			app.activityManager.remove(act);
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}
	
}