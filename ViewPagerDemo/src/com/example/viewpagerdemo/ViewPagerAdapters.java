package com.example.viewpagerdemo;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerAdapters extends PagerAdapter {

	ImageView[] list;
	ArrayList<ImageView> listviews;
	int[] datas;

	public ViewPagerAdapters(ImageView[] list) {
		this.list = list;
	}

	public ViewPagerAdapters(ArrayList<ImageView> listViews, int[] pics) {
		this.listviews = listViews;
		this.datas = pics;
	}

	@Override
	public int getCount() {
		return listviews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int i) {
		int curData = datas[0];

		if (i == 0) {
			curData = datas[datas.length - 1];
			listviews.get(i).setImageResource(datas[datas.length - 1]);
		} else if (i == (listviews.size() - 1)) {
			curData = datas[0];
			listviews.get(i).setImageResource(datas[0]);
		} else {
			curData = datas[datas[i - 1]];
			listviews.get(i).setImageResource(datas[i - 1]);
		}
		// set data to view
		listviews.get(i).setImageResource(curData);

		container.addView(listviews.get(i));
		return listviews.get(i);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ImageView view = listviews.get(position);
		container.removeView(view);
		view.setImageBitmap(null);
	}
}
