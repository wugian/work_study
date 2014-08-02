package com.fuck.activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

public class TestBaseAdapter extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	private LayoutInflater inflater;
	ArrayList<String> dataList;
	/** 选中目录 */
	private int[] sectionIndices;
	/** 选中字母 */
	private Character[] sectionsLetters;

	public TestBaseAdapter(Context context, ArrayList<String> outDataList) {
		inflater = LayoutInflater.from(context);
		this.dataList = outDataList;
		sectionIndices = getSectionIndices();
		sectionsLetters = getStartingLetters();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.test_list_item_layout,
					parent, false);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.text);
		txt.setText(dataList.get(position));
		return convertView;
	}

	private int[] getSectionIndices() {
		int[] sections = new int[dataList.size()];
		for (int i = 0; i < dataList.size(); i++) {
			sections[i] = i;
		}
		return sections;
	}

	private Character[] getStartingLetters() {
		Character[] letters = new Character[sectionIndices.length];
		for (int i = 0; i < sectionIndices.length; i++) {
			letters[i] = dataList.get(sectionIndices[i]).charAt(0);
		}
		return letters;
	}

	@Override
	public Object[] getSections() {
		return sectionsLetters;
	}

	public int getSectionStart(int itemPosition) {
		return getPositionForSection(getSectionForPosition(itemPosition));
	} // remember that these have to be static, postion=1 should walys return
		// the

	// same Id that is.
	@Override
	public long getHeaderId(int position) {
		// return the first character of the country as ID because this is what
		// headers are based upon
		return dataList.get(position).subSequence(0, 1).charAt(0);
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < sectionIndices.length; i++) {
			if (position < sectionIndices[i]) {
				return i - 1;
			}
		}
		return sectionIndices.length - 1;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= sectionIndices.length) {
			section = sectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return sectionIndices[section];
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.header, parent, false);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.text1);
		// set header text as first char in name
		char headerChar = PinyinUtils.getInstance()
				.getAlpha(dataList.get(position)).subSequence(0, 1).charAt(0);
		txt.setText(String.valueOf(headerChar));
		return convertView;
	}

	public void clear() {
		dataList = new ArrayList<String>();
		sectionIndices = new int[0];
		sectionsLetters = new Character[0];
		notifyDataSetChanged();
	}

	public void restore(ArrayList<String> outDataList) {
		this.dataList = outDataList;
		sectionIndices = getSectionIndices();
		sectionsLetters = getStartingLetters();
		notifyDataSetChanged();
	}
}
