package com.seandroid.googlepullrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.seandroid.googlepullrefresh.widget.MySwipeRefreshLayout;
import com.seandroid.googlepullrefresh.widget.MySwipeRefreshLayout.OnRefreshListener;
import com.seandroid.googlepullrefresh.widget.MySwipeRefreshLayout.OnRefreshUpListener;

import java.util.ArrayList;

public class MainActivity extends Activity {

	private MySwipeRefreshLayout swipeLayout;
	private ListView listView;
	private ListViewAdapter adapter;
	private ArrayList<SoftwareClassificationInfo> list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		swipeLayout = (MySwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						swipeLayout.setRefreshing(false);
						list.add(0,new SoftwareClassificationInfo(2, "下拉刷新"+"+"+list.size()));
						adapter.notifyDataSetChanged();
					}
				}, 1000);
				
			}
		});
		swipeLayout.setOnRefreshUpListener(new OnRefreshUpListener() {
			
			@Override
			public void onRefreshUp() {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						swipeLayout.setRefreshing(false);
						list.add(new SoftwareClassificationInfo(2, "上拉加载更多" + "-" + list.size()));
                        //<editor-fold desc="Description">
                        list.add(new SoftwareClassificationInfo(2, "上拉加载更多"+"-"+(list.size())+1));
                        //</editor-fold>
						list.add(new SoftwareClassificationInfo(2, "上拉加载更多" + "-" + (list.size()) + 2));
						list.add(new SoftwareClassificationInfo(2, "上拉加载更多"+"-"+(list.size())+3));
						adapter.notifyDataSetChanged();
					}
				}, 1000);
				
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

		list = new ArrayList<SoftwareClassificationInfo>();
		list.add(new SoftwareClassificationInfo(1, "javaapk.com提供测试"));
		listView = (ListView) findViewById(R.id.list);
		adapter = new ListViewAdapter(this, list);
		listView.setAdapter(adapter);
	}

//	public void onRefresh() {
//		new Handler().postDelayed(new Runnable() {
//			public void run() {
//				swipeLayout.setRefreshing(false);
//				list.add(new SoftwareClassificationInfo(2, "ass"));
//				adapter.notifyDataSetChanged();
//			}
//		}, 1000);
//	}
}
