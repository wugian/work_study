package com.penley.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private boolean debug = true;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
	}

	public void toast(String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public void log(String tag, String msg) {
		if (debug)
			Log.d(tag, msg);
	}

	public void log(String msg) {
		if (debug)
			Log.d("BaseActivity", msg);
	}
}
