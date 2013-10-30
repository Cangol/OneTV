package mobi.cangol.mobile.onetv.base;

import java.util.Stack;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public class FragmentStackManager {
	private static final String STATE_TAG = "FragmentStackManager";
	private Stack<BaseFragment> stack = new Stack<BaseFragment>();
	private Stack<String> tagStack = new Stack<String>();
	private Object lock = new Object();
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private int containerId;
	private FragmentActivity fActivity;
	private Handler handler;
	
	private final Runnable execPendingTransactions = new Runnable() {
		    @Override
		    public void run() {
		      if (fragmentTransaction != null) {
		        fragmentTransaction.commit();
		        fragmentManager.executePendingTransactions();
		        fragmentTransaction = null;
		      }
		    }
	};
	public static FragmentStackManager forContainer(FragmentActivity activity, int containerId,
			  FragmentManager fragmentManager) {
	    return new FragmentStackManager(activity, containerId, fragmentManager);
	}
	
	private FragmentStackManager(FragmentActivity fActivity, int containerId,FragmentManager fragmentManager) {
		this.fActivity = fActivity;
		this.fragmentManager =fragmentManager;
		this.containerId = containerId;
		handler = new Handler();
	}

	public void saveState(Bundle outState) {
		executePendingTransactions();
		final int stackSize = stack.size();
		String[] stackTags = new String[stackSize];

		int i = 0;
		for (String tag : tagStack) {
			Log.i(STATE_TAG, "tag ="+tag);
			stackTags[i++] = tag;
		}

		outState.putStringArray(STATE_TAG, stackTags);
	}

	public void restoreState(Bundle state) {
		String[] stackTags = state.getStringArray(STATE_TAG);
		for (String tag : stackTags) {
			BaseFragment f = (BaseFragment) fragmentManager.findFragmentByTag(tag);
			stack.add(f);
			tagStack.add(tag);
		}
	}
	private FragmentTransaction beginTransaction(){
		if (fragmentTransaction == null) fragmentTransaction = fragmentManager.beginTransaction();
		handler.removeCallbacks(execPendingTransactions);
		return fragmentTransaction;
	}
	public void replace(Class<? extends BaseFragment> clazz, String tag, Bundle args){
		if(stack.size()>0){
			BaseFragment first = stack.firstElement();
			if (first != null && tag.equals(tagStack.firstElement())) {
				if (stack.size() > 1) {
					while (stack.size() > 1) {
						removeFragment(stack.peek());
						synchronized(lock){
							stack.pop();
							tagStack.pop();
						}
						//fragmentManager.popBackStackImmediate();
					}
					stack.peek().setArguments(args);
					attachFragment(stack.peek(),tag);
				}
				return;
			}
			BaseFragment last =stack.peek();
			if(last!=null&&tag.equals(tagStack.peek())){
				if (last.isCleanStack())
				return;//导致 fragmentTransaction 为null
			}
		}
		BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null||!fragment.isSingleton()) {
			fragment = (BaseFragment) Fragment.instantiate(fActivity,clazz.getName(), args);
		}
		if (fragment.isCleanStack()) {
			while (stack.size() > 1) {
				removeFragment(stack.peek());
				synchronized(lock){
					stack.pop();
					tagStack.pop();
				}
			}
		}
		//clear();
		attachFragment(fragment,tag);
		synchronized(lock){
			stack.add(fragment);
			tagStack.add(tag);
		}
	}
	private void removeFragment(Fragment fragment) {
		if (fragment != null) {
			beginTransaction().remove(fragment);
		}
	}
	private void attachFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			if (fragment.isDetached()) {
				beginTransaction()
				.attach(fragment);
//				if(stack.size()>0){
//					beginTransaction().addToBackStack(tag);
//				}
			}else if (!fragment.isAdded()){
				beginTransaction()
				.replace(containerId, fragment, tag);
				
//				if(stack.size()>0){
//					beginTransaction().addToBackStack(tag);
//				}
			}else{
				Log.i(STATE_TAG, "fragment state illegal "+fragment);
			}
		}else{
			Log.i(STATE_TAG, "fragment is null");
		}
	}
	private void detachFragment(Fragment fragment) {
	    if (fragment != null && !fragment.isDetached()) {
	    	beginTransaction();
	      fragmentTransaction.detach(fragment);
	    }
	 }
	
	private void clear() {
		if(stack.size()>0){
			Fragment first = stack.firstElement();
			for (Fragment fragment : stack) {
				if (fragment == first) {
					detachFragment(fragment);
				} else {
					removeFragment(fragment);
				}
			}
		}
		stack.clear();
	}
	public BaseFragment peek() {
		return stack.peek();
	}
	
	public int size() {
		return stack.size();
	}
	
	public boolean pop() {
		if (stack.size() > 1) {
			removeFragment(stack.peek());
			synchronized(lock){
				stack.pop();
				tagStack.pop();
			}
			Log.i(STATE_TAG, "tag="+tagStack.peek());
			attachFragment(stack.peek(),tagStack.peek());
			commit();
			//fragmentManager.popBackStackImmediate();
			return true;
		}
		return false;
	}	
	public void commit() {
		if(fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
		      handler.removeCallbacks(execPendingTransactions);
		      handler.post(execPendingTransactions);
		}else{
			Log.i(STATE_TAG, "fragmentTransaction is null or empty");
		}
	}
	public boolean executePendingTransactions() {
	    if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
	      handler.removeCallbacks(execPendingTransactions);
	      fragmentTransaction.commitAllowingStateLoss();
	      fragmentTransaction = null;
	      return fragmentManager.executePendingTransactions();
	    }
	    return false;
	}
	
}