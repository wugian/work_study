package com.example.filpperdeletelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class FilpperListvew extends ListView {
	// curX 当前滑动到的点 apartX 最新的点和上一个点的差
	private float orginX, orginY, apartX, apartY, curX, curY;
	private boolean delete = false;
	private int itemHeight = 50;// ListView 中视图项的高度 默认50

	// 自定义的滑动删除监听
	private FilpperDeleteListener filpperDeleterListener;

	public FilpperListvew(Context context) {

		super(context);
	}

	public FilpperListvew(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获得第一个点的x坐标
			int temp = getChildCount();
			itemHeight = (temp == 0) ? itemHeight : getChildAt(0).getHeight();
			orginX = ev.getX(0);
			orginY = ev.getY(0);
			curX = orginX;
			curY = orginY;
			break;

		case MotionEvent.ACTION_MOVE:
			// 得到最后一个点的坐标
			float deltaX = ev.getX(ev.getPointerCount() - 1) - orginX;
			float deltaY = Math.abs(ev.getY(ev.getPointerCount() - 1) - orginY);
			apartX = ev.getX() - curX;
			apartY = ev.getY() - curY;
			curX = ev.getX();
			curY = ev.getY();

			if (itemHeight > apartY)
				filpperDeleterListener.onFlipping(orginX, orginY, apartX,
						apartY);

			// 可以滑动删除的条件：横向滑动大于本身视图宽度的一半
			if (deltaX > this.getWidth() / 2) {
				delete = true;
			} else {
				delete = false;
			}
			break;

		case MotionEvent.ACTION_UP:
			if (delete && filpperDeleterListener != null) {
				filpperDeleterListener.filpperDelete(orginX, orginY);
			}
			if (!delete) {
				filpperDeleterListener.restoreView(orginX, orginY);
			}

			reset();
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void reset() {
		delete = false;
		orginX = -1;
		orginY = -1;
	}

	public void setFilpperDeleteListener(FilpperDeleteListener f) {
		filpperDeleterListener = f;
	}

	// 自定义的接口
	public interface FilpperDeleteListener {
		public void filpperDelete(float xPosition, float yPosition);

		// 滑动过程中的处理
		public void onFlipping(float xPosition, float yPosition, float apartX,
				float apartY);

		// 如果没有达成滑动删除项的条件 那么就还原项
		public void restoreView(float x, float y);

	}

	public int getItemHeight() {
		return itemHeight;
	}

}
