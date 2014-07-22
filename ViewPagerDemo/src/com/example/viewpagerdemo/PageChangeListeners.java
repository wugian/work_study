package com.example.viewpagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

public class PageChangeListeners implements OnPageChangeListener {
	// private boolean autoRun = false;
	ImageView[] dots;
	ViewPager viewPager;

	public PageChangeListeners(ImageView[] dots, ViewPager viewPager) {
		this.dots = dots;
		this.viewPager = viewPager;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int position) {
		//position 从１开始的
		int pageIndex = position;
		setImageBackground((position + dots.length - 1) % dots.length);
		if (position == 0) {
			// 当视图在第一个时，将页面号设置为图片的最后一张。
			pageIndex = dots.length;
		} else if (position == dots.length + 1) {
			// 当视图在最后一个是,将页面号设置为图片的第一张。
			pageIndex = 1;
		}
		if (position != pageIndex) {
			viewPager.setCurrentItem(pageIndex, false);
			return;
		}

	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < dots.length; i++) {
			if (i == selectItems) {
				dots[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				dots[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}
}
