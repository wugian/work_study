package com.example.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupView extends RelativeLayout {
	private TextView nameView;
	private ImageView arrowView;

	public GroupView(Context context, ViewGroup parent) {
		super(context);
		init(parent);
	}

	private void init(ViewGroup parent) {
		final LayoutInflater mLayoutInflater = LayoutInflater
				.from(getContext());
		View v = mLayoutInflater.inflate(R.layout.list_item_group, parent,
				false);
		addView(v);
		nameView = (TextView) v.findViewById(R.id.username);
		arrowView = (ImageView) v.findViewById(R.id.group_arrow);
	}

	public void setName(String name) {
		this.nameView.setText(name);
	}

	public void setExpand(boolean expand) {
		if (expand) {
			arrowView.setBackgroundResource(R.drawable.arrow_down);
		} else {
			arrowView.setBackgroundResource(R.drawable.arrow_right);
		}
	}
}
