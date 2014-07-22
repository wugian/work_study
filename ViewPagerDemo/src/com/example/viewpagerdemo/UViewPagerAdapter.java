package com.example.viewpagerdemo;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UViewPagerAdapter extends PagerAdapter {

	ArrayList<View> listviews;
	ArrayList<Modless> datass;

	private int dataSize = 0;

	public UViewPagerAdapter(ArrayList<View> listviews,
			ArrayList<Modless> datass) {
		this.listviews = listviews;
		this.datass = datass;
		dataSize = datass.size();
	}

	@Override
	public int getCount() {
		return datass.size() + 2;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// position　从１　开始
		Modless curDatas = datass.get(0);
		if (position == 0) {
			curDatas = datass.get(dataSize - 1);
		} else if (position == (dataSize + 1)) {
			curDatas = datass.get(0);
		} else {
			curDatas = datass.get(position - 1);
		}
		// set data to view
		// listviews.get(i).setImageResource(curData);
		initUi(listviews.get(position), curDatas);
		container.addView(listviews.get(position));
		return listviews.get(position);
	}

	View initUi(View view, Modless mm) {
		// find view
		// fill the data
		TextView nameTv = (TextView) view.findViewById(R.id.name_tv);
		TextView urlTv = (TextView) view.findViewById(R.id.url_tv);
		nameTv.setText(mm.title);
		urlTv.setText(mm.url);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = listviews.get(position);
		container.removeView(view);
		view = (null);
	}
}
