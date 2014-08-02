package com.liuz.demo;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.liuz.demo.adapter.ClassListAdapter;
import com.liuz.demo.bean.ClassItem;

public class ClassItemAct extends Activity {

	private ListView classItemList;
	private ClassListAdapter classListAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_list_screen);

		classItemList = (ListView) findViewById(R.id.classItemListView);
		classListAdapter = new ClassListAdapter(this);
		classItemList.setAdapter(classListAdapter);

		Vector<ClassItem> data = new Vector<ClassItem>();

		/**
		 * 这里测试用 如果真实数据可以用我给你的xml数据
		 */
		ClassItem item1 = new ClassItem(1, "散养土鸡蛋", 1, "已确认", "");
		ClassItem item2 = new ClassItem(1, "散养土鸡蛋", 2, "未确认", "");
		ClassItem item3 = new ClassItem(1, "散养土鸡蛋", 2, "未确认", "");
		ClassItem item4 = new ClassItem(1, "散养土鸡蛋", 3, "已取消", "");
		ClassItem item5 = new ClassItem(1, "散养土鸡蛋", 3, "已取消", "");
		ClassItem item6 = new ClassItem(1, "散养土鸡蛋", 3, "已取消", "");
		ClassItem item7 = new ClassItem(1, "散养土鸡蛋", 3, "已取消", "");

		data.addElement(item1);
		data.addElement(item2);
		data.addElement(item3);
		data.addElement(item4);
		data.addElement(item5);
		data.addElement(item6);
		data.addElement(item7);
		addAdapterItem(data);
	}

	private void addAdapterItem(Vector<ClassItem> data) {
		Vector<ClassItem> classItem = new Vector<ClassItem>();
		classItem.removeAllElements();

		ClassItem temp = null;
		Set<Integer> set = new HashSet<Integer>();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				temp = data.get(i);// 获取数据
				if (set.contains(temp.partId)) {// 判断是否存在这个partid 如果存在
												// 说明此条数据是在同意个栏目下
					classItem.add(temp);
				} else {
					temp.ifTop = true;// 设置置顶 也就是显示栏目
					set.add(temp.partId);// 将此条partid 添加到set 以便后面判断
					classItem.add(temp);
				}
			}
			classListAdapter.removeAll();
			for (ClassItem item : classItem) {
				classListAdapter.addItem(item);
			}
		}
	}
}