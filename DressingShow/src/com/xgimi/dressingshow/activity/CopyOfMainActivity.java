//package com.xgimi.dressingshow.activity;
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.content.Context;
//import android.graphics.PixelFormat;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.WindowManager;
//import android.widget.FrameLayout.LayoutParams;
//import android.widget.ImageView;
//
//public class CopyOfMainActivity extends Activity {
//
//	ImageView curImg;
//	int mLeft = 0;
//	int mTop = 0;
//	int right = 0;
//	int bottom = 0;
//	LayoutParams laParams;
//	private static FloatWindowView floatWindow;// 悬浮窗View的实例
//	private static LayoutParams floatWindowParams;// 悬浮窗View的参数
//	private static WindowManager windowManager;
//	private static ActivityManager activityManager;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		curImg = (ImageView) findViewById(R.id.curImg);
//		laParams = (LayoutParams) curImg.getLayoutParams();
//		mLeft = curImg.getPaddingLeft();
//		mTop = curImg.getPaddingTop();
//		right = curImg.getPaddingRight();
//		bottom = curImg.getPaddingBottom();
//		createFloatWindow(this);
//		Log.d("TAG", "mLeft mTop  right  bottom:" + mLeft + ":" + mTop + ":"
//				+ right + ":" + bottom);
//	}
//
//	/**
//	 * 创建一个小悬浮窗初始位置为屏幕的右部中间位置
//	 * 
//	 * @param context
//	 *            必须为应用程序的Context.
//	 */
//	public static void createFloatWindow(Context context) {
//		Log.d("lovely", "create the floatwindow");
//		WindowManager windowManager = getWindowManager(context);
//		int screenWidth = windowManager.getDefaultDisplay().getWidth();
//		int screenHeight = windowManager.getDefaultDisplay().getHeight();
//		if (floatWindow == null) {
//			floatWindow = new FloatWindowView(context);
//			if (floatWindowParams == null) {
//				floatWindowParams = new LayoutParams();
//				floatWindowParams.type = LayoutParams.TYPE_PHONE;// 焦点位于应用之上，桌面之下
//				floatWindowParams.format = PixelFormat.RGBA_8888;
//				floatWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
//						| LayoutParams.FLAG_NOT_FOCUSABLE;
//				floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
//				// 添加初始化得到的大小值
//				floatWindowParams.width = FloatWindowView.floatWidth;
//				floatWindowParams.height = FloatWindowView.floatHeight;
//				// 初始化位置
//				floatWindowParams.x = screenWidth;
//				floatWindowParams.y = screenHeight / 2;
//			}
//			floatWindow.setLayoutParams(floatWindowParams);
//			windowManager.addView(floatWindow, floatWindowParams);
//		}
//	}
//
//	void updatePostion() {
//		Log.d("TAG", "laParams.height laParams.width:" + laParams.height + " "
//				+ laParams.width);
//		laParams.height = 100;
//		laParams.width = 100;
//		curImg.setLayoutParams(laParams);
//		mLeft -= 20;
//		mTop += 21;
//		Log.d("TAG", "mLeft mTop  right  bottom:" + mLeft + ":" + mTop + ":"
//				+ right + ":" + bottom);
//		// curImg.layout(mLeft, mTop, right, bottom);
//	}
//
//	/**
//	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回否则返回当前已创建的WindowManager
//	 * 
//	 * @param context
//	 *            必须为应用程序的Context.
//	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗
//	 */
//	private static WindowManager getWindowManager(Context context) {
//		if (windowManager == null) {
//			windowManager = (WindowManager) context
//					.getSystemService(Context.WINDOW_SERVICE);
//		}
//		return windowManager;
//	}
//
//	/**
//	 * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回否则返回当前已创建的ActivityManager
//	 * 
//	 * @param context
//	 *            可传入应用程序上下文
//	 * @return ActivityManager的实例，用于获取手机可用内存
//	 */
//	private static ActivityManager getActivityManager(Context context) {
//		if (activityManager == null) {
//			activityManager = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//		}
//		return activityManager;
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_DPAD_RIGHT:
//			updatePostion();
//			break;
//
//		default:
//			break;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
