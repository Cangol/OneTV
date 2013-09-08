package mobi.cangol.mobile.onetv;

import mobi.cangol.mobile.onetv.utils.Contants;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 * @date: Jun 2, 2011	
 * @time: 1:13:13 PM
 */
public abstract  class BaseActivity extends Activity {
	public static String TAG;
	public OneTvApplication app;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (OneTvApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
	}
	protected abstract void findViews();
	protected abstract void initViews();
	protected abstract void initData();
	protected  void addActivityToManager(Activity act) {
    	Log.i(TAG, "addActivityToManager");
        if (!app.activityManager.contains(act)) {
        	 Log.i(TAG , "addActivityToManager, name = " + act.getClass().getSimpleName()) ;
        	 app.activityManager.add(act);
	    }
	}
	protected  void closeAllActivities() {
    	Log.i(TAG,"closeAllActivities") ;
        for (final Activity act : app.activityManager) {
            if (act != null) {
                act.finish();
            }
        }
	}
	protected  void delActivityFromManager(Activity act) {
    	Log.i(TAG,"delActivityFromManager") ;
        if (app.activityManager.contains(act)) {
        	app.activityManager.remove(act);
        }
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		delActivityFromManager(this);
	}
	protected void showToast(int strId) {
		Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Log.e(TAG, this.getClass().getSimpleName()+" onBackPressed");
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
