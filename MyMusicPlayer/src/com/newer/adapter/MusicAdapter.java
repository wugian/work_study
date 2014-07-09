package com.newer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.mymusicplayer.R;
import com.newer.tool.Tools;

public class MusicAdapter extends BaseAdapter {

	private Context context;
	private Cursor cursor;
	
	public MusicAdapter(Context context,Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
	}
	
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return cursor.getString(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	static class ViewHolder {
		TextView tvTitle;
		TextView tvTime;
		TextView tvSinger;
		ImageView imageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.musicitem, null);
			holder = new ViewHolder();
			//����
			holder.tvTitle = (TextView) convertView.findViewById
					(R.id.textView_title);
			//ʱ��
			holder.tvTime = (TextView) convertView.findViewById
					(R.id.textView_time);
			//����
			holder.tvSinger = (TextView) convertView.findViewById
					(R.id.textView_singer);
			//ͼƬ
			holder.imageView = (ImageView) convertView.findViewById
					(R.id.imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		cursor.moveToPosition(position);
		
		String title = cursor.getString(0) + "." + cursor.getString(1);
		holder.tvTitle.setText(title.length() > 25 ? title.subSequence(0, 25) + "..."
				: title);
		int time = cursor.getInt(5);
		holder.tvTime.setText(Tools.format(time));
		holder.tvSinger.setText(cursor.getString(2));
		
//		Uri uri = Uri.withAppendedPath
//				(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, cursor.getString(0));
//		String [] projection = {Media.ALBUM_ART};
//		Cursor cur = context.getContentResolver().query(uri, projection, null, 
//				null, null);
//		if (cur != null && cur.moveToNext()) {
//			String path = cur.getString(0);
//			holder.imageView.setImageBitmap(BitmapFactory.decodeFile(path));
//		}
//		holder.imageView.setImageBitmap(Tools.getArtPic(context, 
//				cursor.getString(0)));
		holder.imageView.setImageBitmap(Tools.getArtPic(context, 
				cursor.getInt(0), cursor.getInt(6),
				true));
		return convertView;
	}
	
}
