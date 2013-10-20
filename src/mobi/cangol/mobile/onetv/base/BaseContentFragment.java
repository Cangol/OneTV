package mobi.cangol.mobile.onetv.base;

import android.os.Bundle;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseContentFragment extends BaseFragment{
	
	public void setTitle(String title){
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.setTitle( title);
	}
	final public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args) {
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.setContentFragment(fragmentClass, tag,args);
		bfActivity.toggle();
	}
	
}
