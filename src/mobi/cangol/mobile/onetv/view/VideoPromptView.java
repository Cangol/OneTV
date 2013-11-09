package mobi.cangol.mobile.onetv.view;

import mobi.cangol.mobile.onetv.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 */
public class VideoPromptView extends LinearLayout {
	private View errorLayout;
	private View cachingLayout;
	private View loadingLayout;
	private TextView errorText;
	private TextView loadingText;
	private TextView cachingText;
	public VideoPromptView(Context context) {
		super(context);
		init(context);
	}
	public VideoPromptView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setGravity(Gravity.CENTER);
		this.setVisibility(View.GONE);
		this.setOrientation(LinearLayout.VERTICAL);
		errorLayout=inflater.inflate(R.layout.video_view_error, null);
		cachingLayout=inflater.inflate(R.layout.video_view_caching, null);
		loadingLayout=inflater.inflate(R.layout.video_view_loading, null);
		errorText=(TextView) errorLayout.findViewById(R.id.video_error_text);
		cachingText=(TextView) cachingLayout.findViewById(R.id.video_caching_text);
		loadingText=(TextView) loadingLayout.findViewById(R.id.video_loading_text);
		this.addView(errorLayout);
		this.addView(cachingLayout);
		this.addView(loadingLayout);
	}
	public void hide(){
		errorLayout.setVisibility(View.GONE);
		cachingLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.GONE);
	}
	public void showLoading(){
		errorLayout.setVisibility(View.GONE);
		cachingLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.VISIBLE);
		this.setVisibility(View.VISIBLE);
	}
	
	public void showCaching(int percent){
		cachingText.setText(percent+"% "+this.getContext().getString(R.string.video_caching));
		cachingLayout.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.VISIBLE);
	}
	
	public void showError(){
		errorLayout.setVisibility(View.VISIBLE);
		cachingLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		this.setVisibility(View.VISIBLE);
	}
	public void setRetryListener(OnClickListener onClickListener){
		errorText.setOnClickListener(onClickListener);
	}
}
