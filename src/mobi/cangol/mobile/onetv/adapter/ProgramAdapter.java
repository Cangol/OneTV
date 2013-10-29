/** 
 * Copyright (c) 2013 Cangol.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mobi.cangol.mobile.onetv.adapter;

import mobi.cangol.mobile.onetv.R;
import mobi.cangol.mobile.onetv.db.model.Program;
import mobi.cangol.mobile.onetv.view.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description ProgramAdapter.java 
 * @author Cangol
 * @date 2013-9-8
 */
public class ProgramAdapter extends ArrayAdapter<Program>{
	private LayoutInflater mInflater;
	private OnActionClickListener mOnActionClickListener;
	public ProgramAdapter(Context context) {
		super(context);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		Program item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = mInflater.inflate(R.layout.list_view_item_program, parent, false);
			holder=new ViewHolder();
			holder.name=(TextView) convertView.findViewById(R.id.program_name);
			holder.time=(TextView) convertView.findViewById(R.id.program_time);
			holder.action=(ImageView) convertView.findViewById(R.id.program_action);
			convertView.setTag(holder);  
		}
		
		holder.time.setText(item.getPlayTime());
		holder.name.setText(item.getTvProgram());
		
		holder.action.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mOnActionClickListener.onClick(v, position);
			}
			
		});
		return convertView;
	}
	class ViewHolder{
		TextView name;
		TextView time;
		ImageView action;
	}
	public OnActionClickListener getOnActionClickListener() {
		return mOnActionClickListener;
	}

	public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
		this.mOnActionClickListener = mOnActionClickListener;
	}
	public interface OnActionClickListener{
		void onClick(View v,int position);
	}
}
