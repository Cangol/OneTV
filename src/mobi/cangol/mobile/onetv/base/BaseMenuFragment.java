package mobi.cangol.mobile.onetv.base;

import mobi.cangol.mobile.onetv.utils.Contants;
import android.os.Bundle;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseMenuFragment extends BaseFragment{
	protected String TAG = Contants.makeLogTag(BaseMenuFragment.class);

	public void toggleMenu(){
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.toggle();
	}
	public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args,int moduleId) {
		BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
				.getActivity();
		bfActivity.setContentFragment(fragmentClass, tag,args,moduleId);
		bfActivity.toggle();
	}

	abstract protected void onContentChange(int moduleId);

}
