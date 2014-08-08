package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListViewAdapter listViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 数据
		List<GroupBean> itemList = getListData();
		// view和adapter
		ListView listView = (ListView) findViewById(R.id.listview);
		listViewAdapter = new ListViewAdapter(this, itemList);
		// 给adapter设置监听
		listViewAdapter.setGroupListener(new OnGroupClickListener() {
			@Override
			public void onGroupClicked(GroupView view, GroupBean group,
					int position) {
				Toast.makeText(MainActivity.this, "I am " + group.getName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		listViewAdapter.setChildListener(new OnChildClickListener() {
			@Override
			public void onChildClicked(ChildView view, ChildBean child,
					int position) {
				Toast.makeText(MainActivity.this, "I am " + child.getName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(listViewAdapter);
	}

	@Override
	protected void onDestroy() {
		if (listViewAdapter != null) {
			listViewAdapter.destroy();
			listViewAdapter = null;
		}
		super.onDestroy();
	}

	private static interface OnGroupClickListener {
		public void onGroupClicked(GroupView view, GroupBean group, int position);
	}

	private static interface OnChildClickListener {
		public void onChildClicked(ChildView view, ChildBean child, int position);
	}

	private static class ListViewAdapter extends BaseAdapter {

		private static final int VIEW_TYPE_GROUP = 0;
		private static final int VIEW_TYPE_CHILD = 1;

		private Context context;
		private List<GroupBean> groupList;
		private OnGroupClickListener groupListener;
		private OnChildClickListener childListener;

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
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

	private static class GroupBean {
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

	private static class ChildBean {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private static class GroupView extends RelativeLayout {
		private TextView nameView;
		private ImageView arrowView;

		public GroupView(Context context, ViewGroup parent) {
			super(context);
			init(parent);
		}

		private void init(ViewGroup parent) {
			final LayoutInflater mLayoutInflater = LayoutInflater
					.from(getContext());
			View v = mLayoutInflater.inflate(R.layout.list_item_group, parent,
					false);
			addView(v);
			nameView = (TextView) v.findViewById(R.id.username);
			arrowView = (ImageView) v.findViewById(R.id.group_arrow);
		}

		public void setName(String name) {
			this.nameView.setText(name);
		}

		public void setExpand(boolean expand) {
			if (expand) {
				arrowView.setBackgroundResource(R.drawable.arrow_down);
			} else {
				arrowView.setBackgroundResource(R.drawable.arrow_right);
			}
		}
	}

	private static class ChildView extends RelativeLayout {
		private TextView nameView;

		public ChildView(Context context, ViewGroup parent) {
			super(context);
			init(parent);
		}

		private void init(ViewGroup parent) {
			final LayoutInflater mLayoutInflater = LayoutInflater
					.from(getContext());
			View v = mLayoutInflater.inflate(R.layout.list_item_child, parent,
					false);
			addView(v);
			nameView = (TextView) v.findViewById(R.id.username);
		}

		public void setName(String name) {
			this.nameView.setText(name);
		}
	}
}
