package mobi.cangol.mobile.onetv;

import java.util.Stack;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

public abstract class BaseFragmentActivity extends FragmentActivity {
	private static final String TAG = "BaseFragmentActivity";
	private Stack<BaseFragment> backStack = new Stack<BaseFragment>();
	abstract protected void findViews();
	abstract protected void initViews();
	@Override
	public void onBackPressed() {
		Log.d(TAG,"onBackPressed");
		if(backStack.size()<=1){
			//alert exit dialog
			super.onBackPressed();
		}else{
			if(backStack.peek().onBackPressed()){
				
				return;
			}else{
				backStack.pop();
				this.getSupportFragmentManager().popBackStackImmediate();
				return;
			}
		}
	}
	public void popBackStack() {
		backStack.pop();
	}
	public void pushBackStack(BaseFragment baseFragment) {
		backStack.push(baseFragment);
	}
	public void replaceFragment(int contId,BaseFragment baseFragment){
		FragmentManager fragmentManager=this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.replace(contId, baseFragment)
		.addToBackStack(baseFragment.getClass().getSimpleName())
		.commit();
		pushBackStack(baseFragment);
	}
	public void addFragment(int contId,BaseFragment baseFragment){
		FragmentManager fragmentManager=this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.add(contId, baseFragment)
		.addToBackStack(baseFragment.getClass().getSimpleName())
		.commit();
		pushBackStack(baseFragment);
	}
	
}
