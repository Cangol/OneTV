package mobi.cangol.mobile.onetv.view;

import mobi.cangol.mobile.onetv.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewTips extends LinearLayout {
	private LayoutInflater mInflater;
	private View emptyLayout;
	private View errorLayout;
	private View loadingLayout;
	public ListViewTips(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setGravity(Gravity.CENTER);
		this.setVisibility(View.GONE);
		init(mInflater);
	}

	private void init(LayoutInflater inflater) {
		emptyLayout=inflater.inflate(R.layout.commons_list_view_empty, null);
		errorLayout=inflater.inflate(R.layout.commons_list_view_error, null);
		loadingLayout=inflater.inflate(R.layout.commons_list_view_loading, null);
		this.addView(emptyLayout);
		this.addView(errorLayout);
		this.addView(loadingLayout);
	}
	public void showEmpty(){
		emptyLayout.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.VISIBLE);
	}
	public void showEmpty(String str){
		showEmpty();
		TextView text=(TextView) emptyLayout.findViewById(R.id.empty_text);
		text.setText(str);
	}
	public void showError(){
		emptyLayout.setVisibility(View.GONE);
		errorLayout.setVisibility(View.VISIBLE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.VISIBLE);
	}
	public void showError(String str){
		showError();
		TextView text=(TextView) errorLayout.findViewById(R.id.error_text);
		text.setText(str);
	}
	public void showLoading(){
		emptyLayout.setVisibility(View.GONE);
		errorLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.VISIBLE);
		this.setVisibility(View.VISIBLE);
		
	}
	public void showLoading(String str){
		showLoading();
		TextView text=(TextView) loadingLayout.findViewById(R.id.loading_text);
		text.setText(str);
	}
	public void showContent(){
		emptyLayout.setVisibility(View.GONE);
		errorLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.GONE);
	}
	
}
