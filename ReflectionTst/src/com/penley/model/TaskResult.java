package com.penley.model;

import java.util.ArrayList;

/**
 * Copyright (C) 2014 极米科技有限公司, Inc
 * 
 * 存放任务结果列表及相关信息
 * 
 * @author PanLi <478760490@qq.com>
 * @Maintainer PanLi <478760490@qq.com>
 */
public class TaskResult {
	// [result, page_count, [TASK], [TASK], …]
	private int tag;// 标志位是否成功
	private int pageCount;// 总工的页数
	private ArrayList<TaskItem> tasks;// 任务列表

	public TaskResult() {
		tasks = new ArrayList<TaskItem>();
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public ArrayList<TaskItem> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<TaskItem> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "TaskResult [tag=" + tag + ", pageCount=" + pageCount
				+ ", tasks=" + tasks + "]";
	}

}
