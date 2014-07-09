package com.newer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.newer.tool.Tools;

public class MusicService extends Service implements OnCompletionListener {

	private static final int CURRENTTIME = 1;
	private MediaPlayer mp;
	private int id = -1;
	private Intent intent;
	private Handler handler;
	private int loopMode;

	@Override
	public void onCreate() {
		mp = new MediaPlayer();
		mp.setOnCompletionListener(this);
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		int musicId = intent.getIntExtra("musicId", -1);
		if (musicId != -1) {
			if (id != musicId) {
				id = musicId;
				Uri uri = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI,
						String.valueOf(musicId));
				mp.reset();
				try {
					mp.setDataSource(this, uri);
					mp.prepare();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		setUp();
		init();
		// 获得循环模式
//		loopMode = intent.getIntExtra("loop", -1);

		int mode = intent.getIntExtra("mode", -1);
		
		switch (mode) {
		case Tools.PLAY:
			play();
			break;

		case Tools.PAUSE:
			pause();
			break;

		case Tools.STOP:
			stop();
			break;

		case Tools.PROGRESS:
			int progress = intent.getIntExtra("progress", -1);
			mp.seekTo(progress);
			break;

		default:
			break;
		}
	}

	// 发送当前播放时间消息，发送总时间的广播
	private void setUp() {
		mp.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				handler.sendEmptyMessage(CURRENTTIME);
			}
		});
		intent = new Intent(Tools.ACTION_TTIME);
		intent.putExtra("totaltime", mp.getDuration());
		sendBroadcast(intent);
	}

	private void init() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case CURRENTTIME:
					// 发送当前时间的广播
					intent = new Intent(Tools.ACTION_CTIME);
					intent.putExtra("currentTime", mp.getCurrentPosition());
					sendBroadcast(intent);
					handler.sendEmptyMessageDelayed(CURRENTTIME, 500);
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}
	
	public void removeHandler() {
		handler.removeMessages(CURRENTTIME);
	}

	private void stop() {
		if (mp != null) {
			mp.stop();
			id = -1;
			try {
				mp.prepare();
				mp.seekTo(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.removeMessages(CURRENTTIME);
		}
	}

	private void pause() {
		if (mp != null) {
			mp.pause();
		}
	}

	private void play() {
		if (mp != null) {
			mp.start();
		}
	}

	@Override
	public void onDestroy() {
		mp.release();
		super.onDestroy();
	}
	
	public class LocalBinder extends Binder {

		public MusicService getService() {
			return MusicService.this;
		}
	}

	private LocalBinder localService = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return localService;
	}
	
	public void setLoopMode(int mode) {
		this.loopMode = mode;
	}

	//播放完毕后执行的功能
	@Override
	public void onCompletion(MediaPlayer mp) {
		intent = new Intent(Tools.ACTION_COMPLETION);
		switch (loopMode) {

		case Tools.LOOP_SINGER:
			intent.putExtra("loop", Tools.LOOP_SINGER);
			break;

		case Tools.LOOP_RANDOM:
			intent.putExtra("loop", Tools.LOOP_RANDOM);
			break;

		case Tools.LOOP_ORDER:
			intent.putExtra("loop", Tools.LOOP_ORDER);
			break;

		default:
			break;
		}
		sendBroadcast(intent);
	}

}
