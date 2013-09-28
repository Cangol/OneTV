package mobi.cangol.mobile.onetv.base;

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public class FragmentStackManager {
	private static final String STATE_TAG = "FragmentStackManager";
	private Stack<BaseFragment> stack = new Stack<BaseFragment>();

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private int containerId;
	private FragmentActivity fActivity;
	
	public static FragmentStackManager forContainer(FragmentActivity activity, int containerId,
			  FragmentManager fragmentManager) {
	    return new FragmentStackManager(activity, containerId, fragmentManager);
	}
	
	private FragmentStackManager(FragmentActivity fActivity, int containerId,FragmentManager fragmentManager) {
		this.fActivity = fActivity;
		this.fragmentManager =fragmentManager;
		this.containerId = containerId;
	}

	public void saveState(Bundle outState) {

		final int stackSize = stack.size();
		String[] stackTags = new String[stackSize];

		int i = 0;
		for (Fragment f : stack) {
			stackTags[i++] = f.getTag();
		}

		outState.putStringArray(STATE_TAG, stackTags);
	}

	public void restoreState(Bundle state) {
		String[] stackTags = state.getStringArray(STATE_TAG);
		for (String tag : stackTags) {
			BaseFragment f = (BaseFragment) fragmentManager.findFragmentByTag(tag);
			stack.add(f);
		}
	}
	private FragmentTransaction beginTransaction(){
		if (fragmentTransaction == null) fragmentTransaction = fragmentManager.beginTransaction();
		return fragmentTransaction;
	}
	public void replace(Class<? extends BaseFragment> fragmentClass, String tag) {
	    replace(fragmentClass, tag, null);
	}
	public void replace(Class<? extends BaseFragment> fragmentClass, String tag, Bundle args) {
		if(stack.size()>0){
			Fragment first = stack.firstElement();
			if (first != null && tag.equals(first.getTag())) {
				if (stack.size() > 1) {
					while (stack.size() > 1) {
						stack.pop();
						fragmentManager.popBackStackImmediate();
					}
					attachFragment(stack.peek(), tag);
				}
				return;
			}
			Fragment last =stack.peek();
			if(last!=null&&tag.equals(last.getTag())){
				return;
			}
		}
		
		BaseFragment fragment =null;
		if (fragment == null) {
			fragment = (BaseFragment) Fragment.instantiate(fActivity,
					fragmentClass.getName(), args);
		}
		if (fragment.isCleanStack()) {
			while (stack.size() > 1) {
				stack.pop();
				fragmentManager.popBackStackImmediate();
			}
		}
		attachFragment(fragment, tag);
		stack.add(fragment);
	}

	private void attachFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			if (fragment.isDetached()) {
				beginTransaction()
				.attach(fragment);
				if(stack.size()>0){
					beginTransaction().addToBackStack(tag);
				}
			}else if (!fragment.isAdded()){
				beginTransaction()
				.replace(containerId, fragment, tag);
				if(stack.size()>0){
					beginTransaction().addToBackStack(tag);
				}
			}
		}
	}
	
	public BaseFragment peek() {
		return stack.peek();
	}
	
	public int size() {
		return stack.size();
	}
	
	public boolean pop() {
		if (stack.size() > 1) {
			stack.pop();
			fragmentManager.popBackStackImmediate();
			return true;
		}
		return false;
	}

	public boolean commit() {
		if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();
			fragmentTransaction=null;
			return true;
		}
		return false;
	}
}
