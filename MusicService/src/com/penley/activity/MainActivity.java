package com.penley.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.penley.musicservice.R;
import com.penley.service.MusicService;
import com.penley.service.MusicService.MusicBinder;

public class MainActivity extends BaseActivity implements OnClickListener {
	MusicService musicService;

	Button start;
	Button pause;
	Button stop;
	Button exit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_test_main);
		// MediaPlayer mediaPlayer;

		start = (Button) findViewById(R.id.start);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);
		exit = (Button) findViewById(R.id.exit);

		Intent intent = new Intent(this, MusicService.class);
		getApplicationContext().bindService(intent, serviceConnection,
				BIND_AUTO_CREATE);

		start.setOnClickListener(listener);
		pause.setOnClickListener(listener);
		stop.setOnClickListener(listener);
		exit.setOnClickListener(listener);
	}

	int i = 0;
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.start:
				musicService.playMusic("");
				i++;
				break;
			case R.id.pause:
				musicService.pauseMusic();
				break;
			case R.id.stop:
				musicService.stopMusic();
				break;
			case R.id.exit:
				getApplication().unbindService(serviceConnection);
				finish();
				break;

			default:
				break;
			}
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MusicBinder binder = (MusicBinder) service;
			musicService = binder.getService();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
