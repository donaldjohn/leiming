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
 * 用于获取当前手机的一些信息的工具类
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
	//获取mac地址
	public static final String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  
	
	/**
     * 判断是否有sd卡
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
	 * 获取本地的SharedPreferences
	 * */
	public static SharedPreferences getLocalSPF(Context context){
		//读取对象
		SharedPreferences preferences  = context.getSharedPreferences(Constants.LocalSharedPreferences, context.MODE_WORLD_READABLE);
		return preferences;
	}
	/**
	 * 获取本地的SharedPreferences.Editor
	 * */
	public static SharedPreferences.Editor getLocalSPFEditor(Context context){
		SharedPreferences.Editor editor  = getLocalSPF(context).edit();
		return editor;
	}
    
	/**
	 * 在logcat中打印功能的实现
	 * */
	public static void logInfo(String tag,String content){
		Log.i(tag, content);
	}
	
	//info级别的信息
	public final static void printI(String tag,String msg){
		Log.i(tag, msg);
	}	
	
	
}
