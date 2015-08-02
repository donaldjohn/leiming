package com.leiming.utils;

import com.leiming.bean.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;

/**
 * ���ڻ�ȡ��ǰ�ֻ���һЩ��Ϣ�Ĺ�����
 * @author zcs
 * */
public class AppUtil extends Activity{

	/*private static AppUtil appUtil;
	
    private AppUtil() {

    }
	
	public static AppUtil getInstance(){
		if( appUtil == null ){
			appUtil = new AppUtil();
		}
		return appUtil;
	}*/
	//��ȡmac��ַ
	public static final String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  
	
	/**
     * �ж��Ƿ���sd��
     * */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ��ȡ���ص�SharedPreferences
	 * */
	public static SharedPreferences getLocalSPF(Context context){
		//��ȡ����
		SharedPreferences preferences  = context.getSharedPreferences(Constants.LocalSharedPreferences, context.MODE_WORLD_READABLE);
		return preferences;
	}
	/**
	 * ��ȡ���ص�SharedPreferences.Editor
	 * */
	public static SharedPreferences.Editor getLocalSPFEditor(Context context){
		SharedPreferences.Editor editor  = getLocalSPF(context).edit();
		return editor;
	}
    
	/**
	 * ��logcat�д�ӡ���ܵ�ʵ��
	 * */
	public static void logInfo(String tag,String content){
		Log.i(tag, content);
	}
	
	//info�������Ϣ
	public final static void printI(String tag,String msg){
		Log.i(tag, msg);
	}	
	
	
}
