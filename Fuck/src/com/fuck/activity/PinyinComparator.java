package com.fuck.activity;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class PinyinComparator implements Comparator {

	@Override
	public int compare(Object lhs, Object rhs) {
		String str1 = PinyinUtils.getInstance().getPingYin((String) lhs);
		String str2 = PinyinUtils.getInstance().getPingYin((String) rhs);
		return str1.compareTo(str2);
	}
}
