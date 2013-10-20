package mobi.cangol.mobile.onetv.base;

import android.os.Bundle;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseContentFragment extends BaseFragment{
	
	final public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args) {
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.setContentFragment(fragmentClass, tag,args);
		if(bfActivity.getSlidingMenu().isMenuShowing()){
			bfActivity.toggle();
		}
	}
	
}
