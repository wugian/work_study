package com.example.listviewdemo;

import java.util.List;

public class GroupBean {
	private boolean isExpand;
	private String name;
	private List<ChildBean> childList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChildBean> getChildList() {
		return childList;
	}

	public void setChildList(List<ChildBean> childList) {
		this.childList = childList;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

}
