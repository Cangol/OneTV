package mobi.cangol.mobile.onetv.base;

import mobi.cangol.mobile.onetv.R;
import android.os.Bundle;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseContentFragment extends BaseFragment{
	public Tracker tracker;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tracker = GoogleAnalytics.getInstance(this.getActivity()).getTracker(this.getString(R.string.ga_trackingId));
	}
	final public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args) {
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.setContentFragment(fragmentClass, tag,args);
		if(bfActivity.getSlidingMenu().isMenuShowing()){
			bfActivity.toggle();
		}
	}
	@Override
	public void onStart() {
		super.onStart();
		tracker.set(Fields.SCREEN_NAME, this.getTitle());
		tracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
	}
	@Override
	public void onStop() {
		super.onStop();
		tracker.set(Fields.SCREEN_NAME, null);
	}
	
}
