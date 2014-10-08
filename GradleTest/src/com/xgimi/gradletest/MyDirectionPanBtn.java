package com.xgimi.gradletest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class MyDirectionPanBtn extends Button {

	IBtnClick iClick;

	private OnDirectionListening onDirectionListening;

	private static final int[] STATE_UP = { R.attr.state_pressup };
	private static final int[] STATE_DOWN = { R.attr.state_pressdown };
	private static final int[] STATE_LEFT = { R.attr.state_pressleft };
	private static final int[] STATE_RIGHT = { R.attr.state_pressright };
	private static final int[] STATE_CENTER = { R.attr.state_presscenter };

	private int mIsUp = 1;
	private int mIsDown = 2;
	private int mIsLeft = 3;
	private int mIsRight = 4;
	private int mIsCenter = 5;

	private int nowStatus = 0;

	public int getNowStatus() {
		return nowStatus;
	}

	public void setClick(IBtnClick iClick) {
		this.iClick = iClick;
	}

	public MyDirectionPanBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyDirectionPanBtn(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public interface OnDirectionListening {
		void onDirectionListening(int direction);
	}

	public void setOnDirectionListening(OnDirectionListening listening) {
		onDirectionListening = listening;
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {

		final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);

		switch (nowStatus) {
		case 0:
			break;
		case 1:
			if (iClick != null)
				iClick.up();
			mergeDrawableStates(drawableState, STATE_UP);
			break;
		case 2:
			if (iClick != null)
				iClick.down();
			mergeDrawableStates(drawableState, STATE_DOWN);
			break;
		case 3:
			if (iClick != null)
				iClick.left();
			mergeDrawableStates(drawableState, STATE_LEFT);
			break;
		case 4:
			if (iClick != null)
				iClick.right();
			mergeDrawableStates(drawableState, STATE_RIGHT);
			break;
		case 5:
			if (iClick != null)
				iClick.center();
			mergeDrawableStates(drawableState, STATE_CENTER);
			break;

		}

		return drawableState;
	}

	@Override
	public boolean onTouchEvent(MotionEvent paramMotionEvent) {

		switch (paramMotionEvent.getAction()) {

		case MotionEvent.ACTION_DOWN:

			float f1 = paramMotionEvent.getX();
			float f2 = paramMotionEvent.getY();
			float f3 = getWidth();
			float f4 = getHeight();

			if ((f1 > f3 / 3.0F) && (f1 < 2.0F * f3 / 3.0F) && (f2 > f4 / 3.0F)
					&& (f2 < f4 * 2.0F / 3.0F)) {
				nowStatus = mIsCenter;

			} else {
				if (f2 < (f4 / 2)) {

					if (f1 < f3 / 3.0F) {
						nowStatus = mIsLeft;
					} else if (f1 > (f3 / 3.0F) * 2.0F) {
						nowStatus = mIsRight;
					} else {
						nowStatus = mIsUp;
					}

				} else {
					if (f1 < f3 / 3.0F) {
						nowStatus = mIsLeft;
					} else if (f1 > (f3 / 3.0F) * 2.0F) {
						nowStatus = mIsRight;
					} else {
						nowStatus = mIsDown;
					}
				}

			}

			if (onDirectionListening != null) {
				onDirectionListening.onDirectionListening(nowStatus);
			}

			break;

		case MotionEvent.ACTION_UP:
			post(runnable);
		case MotionEvent.ACTION_MOVE:
			post(runnable);
			break;
		default:
			break;

		}

		return super.onTouchEvent(paramMotionEvent);
	}

	/**
	 * 轻触解决办法
	 */
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			nowStatus = 0;
		}
	};

	public interface IBtnClick {
		public void up();

		public void down();

		public void center();

		public void left();

		public void right();
	}

}
