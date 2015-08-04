package com.leiming.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.leiming.bean.Title;
import com.leiming.utils.Container.unit;
/**
 * �ӱ��ػ�ȡ�ļ�������Ϣ�Ĳ�����
 * */
public class DBHelp {

	public static Map<String, String[]> getUser(Context context) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			@SuppressWarnings("resource")
			BufferedReader read = new BufferedReader(new InputStreamReader(
					context.getResources().getAssets().open("leiming/login.txt")));
			String result;
			while ((result = read.readLine()) != null) {
				String[] split = result.split("&&");
				String[] temp = split[1].split(";");
				map.put(split[0], temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;

	}
	//��assestĿ¼�»�ȡ��Ӧ����Ŀ��Ϣ
	public static List<Title> searchFromSD(unit un,Context context) {
		List<Title> list = new ArrayList<Title>();
		String path ="class/"+un.getValue()+".txt";
		try {
			@SuppressWarnings("resource")
			BufferedReader read = new BufferedReader(new InputStreamReader(
					context.getResources().getAssets().open(path), "gbk"));
			String result = null;
			int i = 0;
			while ((result = read.readLine()) != null) {
				//System.out.println(i);
				i++;
				String[] split = result.split("&&");
				Title title = new Title();
				title.title = split[0];
				if (split.length > 1) {
					title.content = split[1];
				}
				list.add(title);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static void intFile(String p) {
		String path = readPath();
		if (path == null) {
		}
		path = path + "/" + p;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

	}
	//�ڻ�ȡsd���ϵ�·��
	public static String readPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) // 
		{
			sdDir = Environment.getExternalStorageDirectory();//
			return sdDir.getPath();
		} else {
			return null;
		}
	}

	public static String getPath2() {
		String sdcard_path = null;
		String sd_default = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (sd_default.endsWith("/")) {
			sd_default = sd_default.substring(0, sd_default.length() - 1);
		}
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				} else if (line.contains("fuse") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdcard_path;
	}
	//��������������ѯ��Ӧ�������Ƿ��ж�Ӧ�����ݣ����ص��Ƿ��ϲ�ѯ����������
	public static List<Title> sreachHelper(String condition, List<Title> data) {
		//��ȡ����Ҫ��ѯ������
		String conditions[] = condition.split("��"); 
		List<Title> queryLsit = new ArrayList<Title>();
		HashMap<String,String> namePinMap = null;
		String spellString = null;
		for (Title title : data) {
			
			/*
			 *�����е���Ŀ���ݺ�ÿ���������бȶ� 
			 * �Ƚ��бȶԱ���ʹ����Ƿ��е�ǰ�����ݣ����û����ô�ٽ��бȶ�ƴ���Ƿ����
			 * */
			for(String cod : conditions){
				//��������������������ƥ�����������ӽ���data��
				if ( (title.title != null && title.title.contains(cod)) || 
						(title.content != null && title.content.contains(cod))
						) {
					queryLsit.add(title);
					break;
				} 
			}
			
		}
		return queryLsit;
	}
}
