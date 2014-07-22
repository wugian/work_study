package com.example.viewpagerdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class TestViewPagerActivity extends Activity {

	ArrayList<View> listviews = new ArrayList<View>();
	ArrayList<Modless> datass = new ArrayList<Modless>();

	// ViewPager
	private ViewPager viewPager;

	// 装点点的ImageView数组
	private ImageView[] dots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		datass = new ArrayList<Modless>();

		View v = LayoutInflater.from(this).inflate(R.layout.i_test, null);
		for (int i = 0; i < 5; i++) {
			Modless m = new Modless();
			m.title = "title " + i;
			m.url = "i am url " + i;
			datass.add(m);
			listviews.add(v);
		}

		listviews.add(v);
		listviews.add(v);

		dots = new ImageView[datass.size()];
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

		// 设置Adapter
		viewPager.setAdapter(new UViewPagerAdapter(listviews, datass));
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new PageChangeListeners(dots,
				viewPager));
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem(1);
	}
}
