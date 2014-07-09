package com.newer.download;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {
	
	public String download(String urlstr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			//创建一个URL对象
			URL url = new URL(urlstr);
			//创建一个http连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			//使用io流读取数据
			buffer = new BufferedReader(new InputStreamReader
					(urlConn.getInputStream()));
			while((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			buffer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
