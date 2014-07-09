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
			//����һ��URL����
			URL url = new URL(urlstr);
			//����һ��http����
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			//ʹ��io����ȡ����
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
