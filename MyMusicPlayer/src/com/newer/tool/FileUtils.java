package com.newer.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	
	private String sdPath;
	
	public FileUtils() {
		sdPath = Environment.getExternalStorageDirectory() + "/";
	}
	
	//�����ļ�
	public File createSDFile(String fileName) throws IOException {
		File file = new File(sdPath + fileName);
		file.createNewFile();
		return file;
	}
	
	//����Ŀ¼
	public File createSDDir(String dirName) {
		File file = new File(sdPath + dirName); 
		file.mkdir();
		return file;
	}
	
	//�ж�SD�����ļ����Ƿ����
	public boolean isFileExist(String fileName) {
		File file = new File(sdPath + fileName);
		return file.exists();
	}
	
	public File writeToSD(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		createSDDir(path);
		try {
			file = createSDFile(fileName);
			output = new FileOutputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
