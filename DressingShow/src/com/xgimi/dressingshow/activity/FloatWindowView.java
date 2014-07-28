package com.xgimi.dressingshow.activity;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 用于悬浮窗口的布局管理，包括基本操作及移动
 * 
 * @author Penley
 *
 */
public class FloatWindowView extends LinearLayout {
	// zoom
	private int mode = 0;
	float oldDist;
	LayoutParams laParams;
	ImageView imageView = null;
	/**
	 * 记录悬浮窗的宽度高度
	 */
	public static int floatWidth;
	public static int floatHeight;

	/**
	 * 记录系统状态栏的高度
	 */
	private static int statusBarHeight;

	/**
	 * 用于更新悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 * 悬浮窗的参数
	 */
	private WindowManager.LayoutParams layoutParams;

	/**
	 * 记录当前手指位置在屏幕上的横纵坐标值
	 */
	private float xFingerPos;
	private float yFingerPos;

	/**
	 * 记录手指按下时在屏幕上的横纵坐标的值
	 */
	private float xFingerDownPos;
	private float yFingerDownPos;

	/**
	 * 悬浮窗的横纵坐标的值
	 */
	private float xPos;
	private float yPos;

	public FloatWindowView(final Context context) {
		super(context);
		// init params
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_window, this);
		View view = findViewById(R.id.float_lyt);
		floatWidth = view.getLayoutParams().width;
		floatHeight = view.getLayoutParams().height;

		Log.d("lovely", "viewHeight,viewWidth:" + floatHeight + ","
				+ floatWidth);

		imageView = (ImageView) findViewById(R.id.coatImg);
		// percentView.setText(MyWindowManager.getUsedPercentValue(context));
		imageView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.coa));
		// 监听按键的长按拖动及点击事件
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return touchEvent(v, event);
			}
		});
	}

	/**
	 * 实现托动功能
	 * 
	 * @param event
	 * @return
	 */
	public boolean touchEvent(View v, MotionEvent event) {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
		// xPos = event.getX();
		// yPos = event.getY();
		// xFingerDownPos = event.getRawX();
		// yFingerDownPos = event.getRawY() - getStatusBarHeight();
		// xFingerPos = event.getRawX();
		// yFingerPos = event.getRawY() - getStatusBarHeight();
		// break;
		// case MotionEvent.ACTION_MOVE:
		// xFingerPos = event.getRawX();
		// yFingerPos = event.getRawY() - getStatusBarHeight();
		// // 手指移动的时候更新悬浮窗的位置
		// updateViewPosition();
		// break;
		// case MotionEvent.ACTION_UP:
		// //
		// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件
		// if (xFingerDownPos == xFingerPos && yFingerDownPos == yFingerPos) {
		// }
		// break;
		// default:
		// break;
		// }
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = 1;
			break;
		case MotionEvent.ACTION_UP:
			mode = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode -= 1;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			mode += 1;
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode >= 2) {
				float newDist = spacing(event);
				if (newDist > oldDist + 1) {
					zoom(newDist / oldDist);
					oldDist = newDist;
				}
				if (newDist < oldDist - 1) {
					zoom(newDist / oldDist);
					oldDist = newDist;
				}
			}
			break;
		}
		return true;
	}

	/**
	 * 将悬浮窗的参数传入，用于更新悬浮窗的位置
	 * 
	 * @param params
	 *            悬浮窗的参数
	 */
	public void setLayoutParams(WindowManager.LayoutParams params) {
		layoutParams = params;
	}

	/**
	 * 更新悬浮窗在屏幕中的位置
	 */
	private void updateViewPosition() {
		layoutParams.x = (int) (xFingerPos - xPos);
		layoutParams.y = (int) (yFingerPos - yPos);
		windowManager.updateViewLayout(this, layoutParams);
	}

	/**
	 * 用于获取状态栏的高度
	 * 
	 * @return 返回状态栏高度的像素值
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
				Log.d("lovely", "statusBarHeight:" + statusBarHeight);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

	private void zoom(float f) {
		Log.e("TAG", "ontouch f is:" + (f + 0.1));
		laParams = (LayoutParams) imageView.getLayoutParams();
		laParams.height = (int) (laParams.height * f + 0.1);
		laParams.width = (int) (laParams.width * f + 0.1);
		imageView.setLayoutParams(laParams);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
}
