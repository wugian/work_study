package com.penley.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * useless,just for launcher the service
 * 
 * @author Penley
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startService(new Intent(this, FloatWindowService.class));
		finish();
	}
}
