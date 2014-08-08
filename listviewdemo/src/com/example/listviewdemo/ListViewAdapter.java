package com.example.listviewdemo;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListViewAdapter extends BaseAdapter {

	private static final int VIEW_TYPE_GROUP = 0x00;
	private static final int VIEW_TYPE_CHILD = 0x01;

	private Context context;
	private List<GroupBean> groupList;
	private OnGroupClickListener groupListener;
	private OnChildClickListener childListener;

	private int expandGroupPosition = -1;

	public ListViewAdapter(Context context, List<GroupBean> groupList) {
		this.context = context;
		this.groupList = groupList;
	}

	@Override
	public int getCount() {
		if (groupList == null || groupList.size() <= 0) {
			return 0;
		}
		int count = 0;
		for (GroupBean group : groupList) {
			count++;
			List<ChildBean> childList = group.getChildList();
			if (childList == null) {
				continue;
			}
			if (group.isExpand()) {
				count += childList.size();
			}
		}
		return count;
	}

	@Override
	public Object getItem(int index) {
		if (groupList == null || groupList.size() <= 0) {
			return null;
		}
		int count = 0;
		for (int i = 0; i < groupList.size(); i++) {
			GroupBean group = groupList.get(i);
			if (index == count) {
				return group;
			}
			count++;
			if (!group.isExpand()) {
				continue;
			}
			List<ChildBean> childList = group.getChildList();
			if (childList == null) {
				continue;
			}
			for (int j = 0; j < childList.size(); j++) {
				ChildBean child = childList.get(j);
				if (index == count) {
					return child;
				}
				count++;
			}
		}
		return null;
	}

	@Override
	public long getItemId(int itemid) {
		return itemid;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			int type = getItemViewType(position);
			if (type == VIEW_TYPE_GROUP) {
				convertView = new GroupView(context, parent);
				holder.groupView = (GroupView) convertView;
			} else {
				convertView = new ChildView(context, parent);
				holder.childView = (ChildView) convertView;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int type = getItemViewType(position);
		if (type == VIEW_TYPE_GROUP) {
			final GroupView groupView = holder.groupView;
			final GroupBean groupBean = (GroupBean) getItem(position);
			groupView.setName(groupBean.getName());
			groupView.setExpand(groupBean.isExpand());
			groupView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					groupBean.setExpand(!groupBean.isExpand());
					notifyDataSetChanged();
					if (groupListener != null) {
						groupListener.onGroupClicked(groupView, groupBean,
								position);
					}
				}
			});
			return groupView;
		} else {
			final ChildView childView = holder.childView;
			final ChildBean childBean = (ChildBean) getItem(position);
			childView.setName(childBean.getName());
			childView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (childListener != null) {
						childListener.onChildClicked(childView, childBean,
								position);
					}
				}
			});
			return childView;
		}
	}

	@Override
	public int getItemViewType(int position) {
		Object item = getItem(position);
		if (item instanceof GroupBean) {
			return VIEW_TYPE_GROUP;
		} else {
			return VIEW_TYPE_CHILD;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public void setGroupListener(OnGroupClickListener groupListener) {
		this.groupListener = groupListener;
	}

	public void setChildListener(OnChildClickListener childListener) {
		this.childListener = childListener;
	}

	public int getExpandGroupPosition() {
		return expandGroupPosition;
	}

	public void setExpandGroupPosition(int expandGroupPosition) {
		this.expandGroupPosition = expandGroupPosition;
	}

	public void destroy() {
		this.context = null;
		this.groupList = null;
		this.groupListener = null;
		this.childListener = null;
	}

	private class ViewHolder {
		GroupView groupView;
		ChildView childView;
	}
}
