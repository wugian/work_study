package com.newer.mymusicplayer;

import com.newer.download.HttpDownloader;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class WebMusicDownload extends ListActivity {
	
	private ArrayAdapter<String> adapter;
	private String[] objects = {"a","b","c"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1, objects );
		getListView().setAdapter(adapter);
		HttpDownloader httpDownloader = new HttpDownloader();
		String xml = httpDownloader.download("http://192.168.1.108:9001/mp3/resources.xml");
		Log.v("aaa", xml);  
	}
}
