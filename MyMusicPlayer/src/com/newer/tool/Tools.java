package com.newer.tool;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.newer.mymusicplayer.R;

public class Tools {
	
	public static final int PLAY = 0;
	public static final int NEXT = 1;
	public static final int PREV = 2;
	public static final int STOP = 3;
	public static final int PAUSE = 4;
	public static final int PROGRESS = 5;
	public static final int LOOP_ORDER = 6;//顺序播放
	public static final int LOOP_RANDOM = 7;//随机播放
	public static final int LOOP_SINGER = 8;//单曲循环
	
	//当前时间
	public static final String  ACTION_CTIME = "com.newer.music_currenttime";
	//总时间
	public static final String ACTION_TTIME = "com.newer.music_totaltime";
	//歌曲播放完成
	public static final String ACTION_COMPLETION = "com.newer.music_completion";
	
	public static String format(int time) {
		int min = time / 1000 / 60;
		int sec = time /1000 % 60;
		return String.format("%d:%02d", min,sec);
	}
	
	
	public static Bitmap getArtPic(Context context, String song_id) {
		Bitmap bitmap = null;
		Uri uri = Uri.withAppendedPath
				(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, song_id);
		String [] projection = {Media.ALBUM_ART};
		Cursor cur = context.getContentResolver().query(uri, projection, null, 
				null, null);
		if (cur != null && cur.moveToNext()) {
			String path = cur.getString(0);
			bitmap = BitmapFactory.decodeFile(path);
		} else {
			bitmap = BitmapFactory.decodeResource
					(context.getResources(), R.drawable.ic_launcher);
		}
		return bitmap;
	}
	
	// 获取专辑照片
	public static Bitmap getArtPic(Context context, long song_id, long album_id,
			boolean allowdefault) {
		if (album_id < 0) {

			if (song_id >= 0) {
				Bitmap bm = getArtworkFromFile(context, song_id, -1);
				if (bm != null) {
					return bm;
				}
			}
			if (allowdefault) {
				return getDefaultArtwork(context);
			}
			return null;
		}
		ContentResolver res = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
		if (uri != null) {
			InputStream in = null;
			try {
				in = res.openInputStream(uri);
				BitmapFactory.Options options = new BitmapFactory.Options();

				options.inSampleSize = 1;

				options.inJustDecodeBounds = true;

				BitmapFactory.decodeStream(in, null, options);

				options.inSampleSize = computeSampleSize(options, 200);

				options.inJustDecodeBounds = false;
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				in = res.openInputStream(uri);
				return BitmapFactory.decodeStream(in, null, sBitmapOptions);
			} catch (FileNotFoundException ex) {

				Bitmap bm = getArtworkFromFile(context, song_id, album_id);
				if (bm != null) {
					if (bm.getConfig() == null) {
						bm = bm.copy(Bitmap.Config.RGB_565, false);
						if (bm == null && allowdefault) {
							return getDefaultArtwork(context);
						}
					}
				} else if (allowdefault) {
					bm = getDefaultArtwork(context);
				}
				return bm;
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
				}
			}
		}

		return null;
	}

	/**
	 * 对图片大小进行判断，并得到合适缩放比例
	 * 
	 * @param options
	 * @param target
	 * @return
	 */
	static int computeSampleSize(BitmapFactory.Options options, int target) {

		int w = options.outWidth;
		int h = options.outHeight;
		int candidateW = w / target;
		int candidateH = h / target;
		int candidate = Math.max(candidateW, candidateH);
		if (candidate == 0)
			return 1;
		if (candidate > 1) {
			if ((w > target) && (w / candidate) < target)
				candidate -= 1;
		}
		if (candidate > 1) {
			if ((h > target) && (h / candidate) < target)
				candidate -= 1;
		}
		Log.v("ADW", "candidate:" + candidate);
		return candidate;
	}

	private static Bitmap getArtworkFromFile(Context context, long songid,
			long albumid) {
		Bitmap bm = null;

		if (albumid < 0 && songid < 0) {
			throw new IllegalArgumentException(
					"Must specify an album or a song id");
		}
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			FileDescriptor fd = null;
			if (albumid < 0) {
				Uri uri = Uri.parse("content://media/external/audio/media/"
						+ songid + "/albumart");
				ParcelFileDescriptor pfd = context.getContentResolver()
						.openFileDescriptor(uri, "r");
				if (pfd != null) {
					fd = pfd.getFileDescriptor();

				}
			} else {
				Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
				ParcelFileDescriptor pfd = context.getContentResolver()
						.openFileDescriptor(uri, "r");
				if (pfd != null) {
					fd = pfd.getFileDescriptor();

				}
			}
			options.inSampleSize = 1;
			// 只进行大小判断
			options.inJustDecodeBounds = true;
			// 调用此方法得到options得到图片的大小
			BitmapFactory.decodeFileDescriptor(fd, null, options);
			// 我们的目标是在800pixel的画面上显示。
			// 所以需要调用computeSampleSize得到图片缩放的比例
			options.inSampleSize = 200;
			// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// 根据options参数，减少所需要的内存
			bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
		} catch (FileNotFoundException ex) {

		}
		if (bm != null) {

		}
		return bm;
	}

	private static Bitmap getDefaultArtwork(Context context) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;

		return BitmapFactory.decodeStream(context.getResources()
				.openRawResource(R.drawable.ic_launcher), null, opts);
	}

	private static final Uri sArtworkUri = Uri
			.parse("content://media/external/audio/albumart");
	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
}
