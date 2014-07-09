package com.newer.mymusicplayer;

import com.newer.adapter.MusicAdapter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

public class MainActivity extends Activity {

	private ListView mListView;
	private Cursor cursor;
	private String[] projection = {
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DURATION,
			MediaStore.Audio.Media.ALBUM_ID
		};
	
	private String [] titles;//所有的歌曲名
	private String [] singers;//所有的歌手名
	private int [] albumIds;//专辑id
	private int [] ids;//歌曲id
	private int [] totalTimes;//总时间
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        ShowMusicList();
        initData();
        mListView.setOnItemClickListener(new ListItemListener());
    }
    
    //退出的時候，整個程序退出
    @Override
    protected void onNewIntent(Intent intent) {
    	// TODO Auto-generated method stub
    	super.onNewIntent(intent);
    	if (intent.getFlags() == Intent.FLAG_ACTIVITY_CLEAR_TOP) {
			finish();
		}
    }
    
    //初始化数据
    private void initData() {
    	cursor.moveToFirst();
    	titles = new String [cursor.getCount()];
    	albumIds = new int [cursor.getCount()];
    	singers = new String [cursor.getCount()];
    	ids = new int [cursor.getCount()];
    	totalTimes = new int [cursor.getCount()];
    	
    	for (int i = 0; i < cursor.getCount(); i++) {
    		ids[i] = cursor.getInt(0);
			titles[i] = cursor.getString(1);
			singers[i] = cursor.getString(2);
			albumIds[i] = cursor.getInt(6);
			totalTimes[i] = cursor.getInt(5);
			cursor.moveToNext();
		}
    	
	}

	class ListItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			cursor.moveToFirst();
			
			Intent intent = new Intent(MainActivity.this, MusicPlay.class);
			intent.putExtra("position", position);
			intent.putExtra("titles", titles);
			intent.putExtra("singers", singers);
			intent.putExtra("albumIds", albumIds);
			intent.putExtra("ids", ids);
			intent.putExtra("totalTimes", totalTimes);
			startActivity(intent);
		}
    	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemWeb:
			Intent intent = new Intent(this, WebMusicDownload.class);
			startActivity(intent);
			break;

		case R.id.itemAbout:
			
			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void ShowMusicList() {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		cursor = getContentResolver().query
				(uri , projection  , null, null, null);
		
		mListView.setAdapter(new MusicAdapter(this, cursor));
	}
}
