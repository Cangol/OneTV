package mobi.cangol.mobile.onetv;

import com.cangol.mobile.logging.Log;

import mobi.cangol.mobile.onetv.utils.Contants;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected String TAG = Contants.makeLogTag(BaseFragment.class);

	abstract protected void initData(Bundle savedInstanceState);
	
	abstract protected void findViews(View view);

	abstract protected void initViews(Bundle savedInstanceState);

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, "onAttach");
	}

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = Contants.makeLogTag(this.getClass());
		Log.v(TAG, "onCreate");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.v(TAG, "onViewCreated");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.v(TAG, "onActivityCreated");
	}
	

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.v(TAG, "onDetach");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	public boolean onBackPressed() {
		Log.v(TAG, "onBackPressed");
		return false;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.v(TAG, "onSaveInstanceState");
	}
	
	public void addFragment(int contId, BaseFragment baseFragment, String tag) {
		BaseFragmentActivity bfActivity = (BaseFragmentActivity) this
				.getActivity();
		bfActivity.addFragment(contId, baseFragment, tag);
	}

	public void replaceFragment(int contId, BaseFragment baseFragment,
			String tag) {
		BaseFragmentActivity bfActivity = (BaseFragmentActivity) this
				.getActivity();
		bfActivity.replaceFragment(contId, baseFragment, tag);
	}

	public void replaceChildFragment(int contId, BaseFragment baseFragment,
			String tag) {
		FragmentManager fragmentManager = this.getChildFragmentManager();
		fragmentManager.beginTransaction().replace(contId, baseFragment, tag)
				.addToBackStack(baseFragment.getClass().getSimpleName())
				.commit();
	}

	public void addChildFragment(int contId, BaseFragment baseFragment,
			String tag) {
		FragmentManager fragmentManager = this.getChildFragmentManager();
		fragmentManager.beginTransaction().add(contId, baseFragment, tag)
				.addToBackStack(baseFragment.getClass().getSimpleName())
				.commit();
	}

}
