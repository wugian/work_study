package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class TfMainActivity extends Activity {

	private ListViewAdapter listViewAdapter;
	List<GroupBean> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 数据
		itemList = getListData();
		// view和adapter
		ListView listView = (ListView) findViewById(R.id.listview);
		listViewAdapter = new ListViewAdapter(this, itemList);
		// 给adapter设置监听
		listViewAdapter.setGroupListener(new OnGroupClickListener() {
			@Override
			public void onGroupClicked(GroupView view, GroupBean group,
					int position) {
				int oldPosition = listViewAdapter.getExpandGroupPosition();
				if (oldPosition != position) {
					if (oldPosition != -1)
						itemList.get(oldPosition).setExpand(false);
					itemList.get(position).setExpand(true);
					listViewAdapter.setExpandGroupPosition(position);
					listViewAdapter.notifyDataSetChanged();
					for (int i = 0; i < itemList.size(); i++) {
						System.out.println(itemList.get(i).isExpand());
					}
				}
				Toast.makeText(TfMainActivity.this, "I am " + group.getName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		listViewAdapter.setChildListener(new OnChildClickListener() {
			@Override
			public void onChildClicked(ChildView view, ChildBean child,
					int position) {
				Toast.makeText(TfMainActivity.this, "I am " + child.getName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(listViewAdapter);
	}

	private List<GroupBean> getListData() {
		List<GroupBean> itemList = new ArrayList<GroupBean>();
		for (int i = 0; i < 10; i++) {
			GroupBean group = new GroupBean();
			group.setName("group_" + (i + 1));
			List<ChildBean> childList = new ArrayList<ChildBean>();
			for (int j = 0; j < 3; j++) {
				ChildBean child = new ChildBean();
				child.setName("child" + j);
				childList.add(child);
			}
			group.setChildList(childList);
			itemList.add(group);
		}
		return itemList;
	}

	@Override
	protected void onDestroy() {
		if (listViewAdapter != null) {
			listViewAdapter.destroy();
			listViewAdapter = null;
		}
		super.onDestroy();
	}
}
