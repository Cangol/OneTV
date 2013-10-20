package mobi.cangol.mobile.onetv.base;

import mobi.cangol.mobile.onetv.MobileApplication;
import mobi.cangol.mobile.onetv.log.Log;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public abstract class BaseFragment extends Fragment{
	protected String TAG = Contants.makeLogTag(BaseFragment.class);
	private static final boolean LIFECYCLE=true;
	private String title;
	private FragmentStackManager stack;
	protected MobileApplication app;
	
	abstract protected void initData(Bundle savedInstanceState);
	
	abstract protected void findViews(View view);

	abstract protected void initViews(Bundle savedInstanceState);
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(LIFECYCLE)if(LIFECYCLE)Log.v(TAG, "onAttach");
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onCreate");
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		app = (MobileApplication) this.getActivity().getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		if(savedInstanceState==null){
		}else{
			title=savedInstanceState.getString("title");
			if(null!=stack)stack.restoreState(savedInstanceState);
		}
	}
	protected void initFragmentStack(int containerId){
		if(null==stack)
		stack = FragmentStackManager.forContainer(this.getActivity(), containerId,this.getChildFragmentManager());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(LIFECYCLE)Log.v(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onViewCreated");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onActivityCreated");
	}
	

	@Override
	public void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if(LIFECYCLE)Log.v(TAG, "onDetach");
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(LIFECYCLE)Log.v(TAG, "onDestroyView");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(LIFECYCLE)Log.v(TAG, "onDestroy");
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
		outState.putString("title", title);
		if(null!=stack)stack.saveState(outState);
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}
	
	public boolean onBackPressed() {
		if(LIFECYCLE)Log.v(TAG, "onBackPressed");
		if(null==stack)return false;
		if (stack.size() <= 1) {
			return false;
		} else {
			if (stack.peek().onBackPressed()) {
				return true;
			} else {
				stack.pop();
				return true;
			}
		}
	}
	public boolean isCleanStack(){
		return false;
	}
	public boolean onSupportNavigateUp() {
		return false;
	}
	public ActionBar getSupportActionBar() {
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			return abActivity.getSupportActionBar();
		}
	}
	public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		stack.replace(fragmentClass, tag,args);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
		if(getSupportActionBar()!=null)
		getSupportActionBar().setTitle(title);
	}
	public void setTitle(int title) {
		this.title=getString(title);
		if(getSupportActionBar()!=null)
		getSupportActionBar().setTitle(title);
	}
}
