package com.fuck.activity;

import static android.widget.Toast.LENGTH_SHORT;

import java.util.ArrayList;
import java.util.Arrays;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.fuck.activity.SideBar.OnTouchingLetterChangedListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnTouchingLetterChangedListener {

	private Context context = this;

	private TestBaseAdapter mAdapter;
	private StickyListHeadersListView mStickyList;
	private String[] data = new String[] { "阿富汗", "孟加拉国", "文莱", "柬埔寨", "朝鲜",
			"印度", "伊朗", "以色列", "约旦", "老挝", "中国澳门", "马尔代夫", "尼泊尔", "巴基斯坦",
			"菲律宾", "沙特阿拉伯", "韩国", "叙利亚", "土耳其", "也门共和国", "中国", "东帝汶", "吉尔吉斯斯坦",
			"土库曼斯坦", "阿尔及利亚" };
	private ArrayList<String> dataList;
	private TextView show;
	private SideBar sideBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findById();
		setListener();
		Arrays.sort(data,new PinyinComparator());
		dataList = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			dataList.add(data[i]);
		}
		mAdapter = new TestBaseAdapter(this, dataList);
		mStickyList.setAdapter(mAdapter);
	}

	private void findById() {
		show = (TextView) findViewById(R.id.show);
		sideBar = (SideBar) findViewById(R.id.sideBar);
		mStickyList = (StickyListHeadersListView) findViewById(R.id.list);
	}

	private void setListener() {
		sideBar.setOnTouchingLetterChangedListener(this);
		mStickyList.setOnItemClickListener(this);
	}

	public void clear(View v) {
		mAdapter.clear();
	}

	public void restore(View v) {
		mAdapter.restore(dataList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(context, "Item " + position + " clicked!", LENGTH_SHORT)
				.show();
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		if (!"".equals(s) && s != null) {
			show.setVisibility(View.VISIBLE);
			show.setText(s);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					show.setVisibility(View.GONE);
				}
			}, 1500);
			int position = alphaIndexer(s);
			if (position > 0) {
				mStickyList.setSelection(position);
			}
		}
	}

	public int alphaIndexer(String s) {
		int position = 0;
		String st = "";
		if (dataList != null) {
			for (int i = 0; i < dataList.size(); i++) {
				st = dataList.get(i).toString().trim();
				if (!"".equals(st) && st != null) {
					if (PinyinUtils.getInstance().getAlpha(st).startsWith(s)) {
						position = i;
						break;
					}
				}
			}
		}
		return position;
	}
}
