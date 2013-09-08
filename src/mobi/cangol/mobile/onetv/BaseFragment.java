package mobi.cangol.mobile.onetv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public abstract class BaseFragment extends Fragment {
	protected static String TAG ="" ;
	abstract protected void findViews(View view);
	abstract protected void initViews(View view,Bundle savedInstanceState);
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG=this.getClass().getSimpleName();
		Log.d(TAG, "onCreate");
	}
	public boolean onBackPressed() {
		return false;
	}
	public void addFragment(int contId,BaseFragment baseFragment) {
		BaseFragmentActivity bfActivity=(BaseFragmentActivity) this.getActivity();
		bfActivity.addFragment(contId, baseFragment);
	}
	public void replaceFragment(int contId,BaseFragment baseFragment) {
		BaseFragmentActivity bfActivity=(BaseFragmentActivity) this.getActivity();
		bfActivity.replaceFragment(contId, baseFragment);
	}
	
	public void networkStatus() {
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
}
