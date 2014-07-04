package com.penley.floatwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 功能窗口
 * @author pc
 *
 */
public class FunctionWindowView extends LinearLayout {
	/**
	 * 记录大悬浮窗的宽度高度
	 */
	public static int viewWidth;
	public static int viewHeight;

	WindowManager windowManager;
	Context context;

	public FunctionWindowView(final Context context) {
		super(context);
		this.context = context;
		LayoutInflater.from(context.getApplicationContext()).inflate(
				R.layout.a_funciton, this);
		View view = findViewById(R.id.function_lyt);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

		Button btn = (Button) view.findViewById(R.id.mainBtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "are you happy", Toast.LENGTH_SHORT)
						.show();
				MyWindowManager.removeFunctionWindow(context);
				MyWindowManager.createFloatWindow(context);
			}

		});
		// 类似于popwindow点击其他地方消失
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyWindowManager.removeFunctionWindow(context);
				MyWindowManager.createFloatWindow(context);
			}
		});
	}
}
