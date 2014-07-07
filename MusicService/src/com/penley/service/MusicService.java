package com.penley.service;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("SdCardPath")
public class MusicService extends Service {
	IBinder musicBinder = new MusicBinder();
	MediaPlayer mediaPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("lovely", "service onceate");
		mediaPlayer = new MediaPlayer();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("lovely", "ondestroy release");
		mediaPlayer.release();
	}

	public class MusicBinder extends Binder {
		public MusicService getService() {
			// 返回本service的实例到客户端，于是客户端可以调用本service的公开方法
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return musicBinder;
	}

	// ----------------------other public method---------------------------//
	public void getMusicList() {
	}

	public void playMusic(String path) {
		Log.d("lovely", "playMusic");
		String musicPath = "/mnt/sdcard2/KuwoMusic/music/My Love-WestLife.mp3";
		// if (position % 2 != 1)
		// musicPath = "/mnt/sdcard2/KuwoMusic/music/Breathless-Shayne Ward.mp3";
		try {
			mediaPlayer.reset();// 把各项参数恢复到初始状态
			mediaPlayer.setDataSource(musicPath);
			mediaPlayer.prepare();
			Log.d("lovely", "in play prepare ok");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaPlayer.start();
	}

	public void pauseMusic() {
		Log.d("lovely", "pauseMusic");
		mediaPlayer.pause();
	}

	public void stopMusic() {
		Log.d("lovely", "stopMusic");
		mediaPlayer.pause();
		mediaPlayer.seekTo(0);
	}

	public void exitMusic(int position) {
		Log.d("lovely", "playMusic");

	}

}
