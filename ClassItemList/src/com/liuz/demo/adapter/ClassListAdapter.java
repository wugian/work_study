package com.liuz.demo.adapter;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuz.demo.R;
import com.liuz.demo.bean.ClassItem;

public class ClassListAdapter extends BaseAdapter {

	private Vector<ClassItem> items;
	private Context context;
	private LayoutInflater mInflater;
	
	public ClassListAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
	 	items = new Vector<ClassItem>();
	}
	
	public ClassItem getMessageByIndex(int index) {
		return items.elementAt(index);
	}

	public void addItem(ClassItem item) {
		items.add(item);
		this.notifyDataSetChanged();
	}
	
	/**
	 * 将Item内的对象复制到指定的Vector中
	 * @param messages
	 */
	public void copyItems(Vector<ClassItem> messages){
		if(messages==null){
			messages = new Vector<ClassItem>();
		}
		if(items != null && items.size() > 0){
			for(int i = 0;i < items.size();i++){
				messages.add(items.get(i));
			}
		}
	}

	public void removeAll() {
		items.clear();
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.class_part_lay, null);
			holder = new ViewHolder();
			// 栏目名称布局
			holder.partLayout = (LinearLayout)convertView.findViewById(R.id.classGroupLayout);
			holder.partId = (TextView) convertView.findViewById(R.id.class_part_id);
			holder.partName = (TextView) convertView.findViewById(R.id.class_part_name);
			holder.className = (TextView) convertView.findViewById(R.id.class_item_name);
			holder.mIcon = (ImageView)convertView.findViewById(R.id.class_item_img);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ClassItem classItem = items.get(position);
		if(classItem != null){
			if(classItem.ifTop){
				holder.partLayout.setVisibility(View.VISIBLE);
				holder.partName.setText(classItem.partName);
				holder.className.setText(classItem.className);
			}else{
				holder.partLayout.setVisibility(View.GONE);
				holder.partName.setText(classItem.partName);
				holder.className.setText(classItem.className);
			}
			
			holder.mIcon.setBackgroundResource(R.drawable.default_class_icon);
		}
		return convertView;
	}

	public class ViewHolder {
		LinearLayout partLayout;
		TextView partId;
		TextView partName;
		TextView className;
		ImageView mIcon;
	}
}
