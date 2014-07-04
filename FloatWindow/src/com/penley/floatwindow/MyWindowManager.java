package com.penley.floatwindow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class MyWindowManager {

	private static FloatWindowView floatWindow;// 悬浮窗View的实例
	private static FunctionWindowView functionWindow;// 功能悬浮窗View的实例

	private static LayoutParams floatWindowParams;// 悬浮窗View的参数
	private static LayoutParams functionWindowParams;// 功能悬浮窗View的参数

	private static WindowManager windowManager;// 用于控制在屏幕上添加或移除悬浮窗
	private static ActivityManager activityManager;// 用于获取手机可用内存

	/**
	 * 创建一个小悬浮窗初始位置为屏幕的右部中间位置
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createFloatWindow(Context context) {
		Log.d("lovely", "create the floatwindow");
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (floatWindow == null) {
			floatWindow = new FloatWindowView(context);
			if (floatWindowParams == null) {
				floatWindowParams = new LayoutParams();
				floatWindowParams.type = LayoutParams.TYPE_PHONE;// 焦点位于应用之上，桌面之下
				floatWindowParams.format = PixelFormat.RGBA_8888;
				floatWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				// 添加初始化得到的大小值
				floatWindowParams.width = FloatWindowView.floatWidth;
				floatWindowParams.height = FloatWindowView.floatHeight;
				// 初始化位置
				floatWindowParams.x = screenWidth;
				floatWindowParams.y = screenHeight / 2;
			}
			floatWindow.setLayoutParams(floatWindowParams);
			windowManager.addView(floatWindow, floatWindowParams);
		}
	}

	/**
	 * 将悬浮窗从屏幕上移除
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeFloatWindow(Context context) {
		if (floatWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(floatWindow);
			floatWindow = null;
		}
	}

	/**
	 * 创建一个大悬浮窗位置为屏幕正中间
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createFunctionWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (functionWindow == null) {
			functionWindow = new FunctionWindowView(context);
			if (functionWindowParams == null) {
				functionWindowParams = new LayoutParams();
				functionWindowParams.x = screenWidth / 2
						- FunctionWindowView.viewWidth / 2;
				functionWindowParams.y = screenHeight / 2
						- FunctionWindowView.viewHeight / 2;
				functionWindowParams.type = LayoutParams.TYPE_PHONE;
				functionWindowParams.format = PixelFormat.RGBA_8888;
				functionWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				functionWindowParams.width = FunctionWindowView.viewWidth;
				functionWindowParams.height = FunctionWindowView.viewHeight;
			}
			windowManager.addView(functionWindow, functionWindowParams);
		}
	}

	/**
	 * 将功能悬浮窗从屏幕上移除
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeFunctionWindow(Context context) {
		if (functionWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(functionWindow);
			functionWindow = null;
		}
	}

	/**
	 * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比
	 * 
	 * @param context
	 *            可传入应用程序上下文
	 */
	public static void updateUsedPercent(Context context) {
		if (floatWindow != null) {
			TextView percentView = (TextView) floatWindow
					.findViewById(R.id.button1);
			percentView.setText(getUsedPercentValue(context));
		}
	}

	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false
	 */
	public static boolean isWindowShowing() {
		return floatWindow != null || functionWindow != null;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回否则返回当前已创建的WindowManager
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager getWindowManager(Context context) {
		if (windowManager == null) {
			windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager;
	}

	/**
	 * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回否则返回当前已创建的ActivityManager
	 * 
	 * @param context
	 *            可传入应用程序上下文
	 * @return ActivityManager的实例，用于获取手机可用内存
	 */
	private static ActivityManager getActivityManager(Context context) {
		if (activityManager == null) {
			activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return activityManager;
	}

	/**
	 * 计算已使用内存的百分比，并返回
	 * 
	 * @param context
	 *            可传入应用程序上下文
	 * @return 已使用内存的百分比，以字符串形式返回
	 */
	public static String getUsedPercentValue(Context context) {
		String dir = "/proc/meminfo";
		try {
			FileReader fr = new FileReader(dir);
			BufferedReader br = new BufferedReader(fr, 2048);
			String memoryLine = br.readLine();
			String subMemoryLine = memoryLine.substring(memoryLine
					.indexOf("MemTotal:"));
			br.close();
			long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll(
					"\\D+", ""));
			long availableSize = getAvailableMemory(context) / 1024;
			int percent = (int) ((totalMemorySize - availableSize)
					/ (float) totalMemorySize * 100);
			return percent + "%";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "悬浮窗";
	}

	/**
	 * 获取当前可用内存，返回数据以字节为单位
	 * 
	 * @param context
	 *            可传入应用程序上下文
	 * @return 当前可用内存
	 */
	private static long getAvailableMemory(Context context) {
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		getActivityManager(context).getMemoryInfo(mi);
		return mi.availMem;
	}

}
