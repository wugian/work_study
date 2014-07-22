package com.example.viewpagerdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ViewPagerActivity extends Activity {
	// ViewPager
	private ViewPager viewPager;

	// 装点点的ImageView数组
	private ImageView[] dots;

	// 装ImageView数组
	private ImageView[] mImageViews;

	ArrayList<ImageView> listViews = new ArrayList<ImageView>();

	// 图片资源id
	private int[] imgIdArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		// 载入图片资源ID
		imgIdArray = new int[] { R.drawable.item001, R.drawable.item002,
				R.drawable.item003, R.drawable.item004 };
		// 将点点加入到ViewGroup中
		dots = new ImageView[imgIdArray.length];

		for (int i = 0; i < dots.length; i++) {
			ImageView imageView = new ImageView(this);
			// dot size
			imageView.setLayoutParams(new LayoutParams(10, 10));
			// left margin
			// imageView.setLeft(3);

			dots[i] = imageView;
			if (i == 0) {
				dots[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				dots[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			group.addView(imageView);
		}

		// 将图片装载到数组中
		int length = imgIdArray.length + 2;
		for (int i = 0; i < length; i++) {
			ImageView imageView = new ImageView(this);
			ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(viewPagerImageViewParams);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			listViews.add(imageView);
		}
		// 设置Adapter
		viewPager.setAdapter(new ViewPagerAdapters(listViews, imgIdArray));
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new PageChangeListeners(dots,
				viewPager));
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem(1);
	}
}
