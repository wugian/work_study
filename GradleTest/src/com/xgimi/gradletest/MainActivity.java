package com.xgimi.gradletest;

import com.xgimi.gradletest.MyDirectionPanBtn.IBtnClick;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	MyDirectionPanBtn btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(getApplicationContext(), "gradle eclipse test",
				Toast.LENGTH_LONG).show();
		btn = (MyDirectionPanBtn) findViewById(R.id.myDirectionPanBtn);
		btn.setClick(new IBtnClick() {

			@Override
			public void up() {
				toast("up");
			}

			@Override
			public void down() {
				toast("down");
			}

			@Override
			public void right() {
				// TODO Auto-generated method stub
				toast("right");
			}

			@Override
			public void left() {
				// TODO Auto-generated method stub
				toast("left");
			}

			@Override
			public void center() {
				// TODO Auto-generated method stub
				toast("center");
			}
		});

	}

	void toast(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}

	void d(String s) {
		Log.d("TAG", s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
