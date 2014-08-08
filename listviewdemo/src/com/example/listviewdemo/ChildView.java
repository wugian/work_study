package com.example.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChildView extends RelativeLayout {
	private TextView nameView;

	public ChildView(Context context, ViewGroup parent) {
		super(context);
		init(parent);
	}

	private void init(ViewGroup parent) {
		final LayoutInflater mLayoutInflater = LayoutInflater
				.from(getContext());
		View v = mLayoutInflater.inflate(R.layout.list_item_child, parent,
				false);
		addView(v);
		nameView = (TextView) v.findViewById(R.id.username);
	}

	public void setName(String name) {
		this.nameView.setText(name);
	}
}
