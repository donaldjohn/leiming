package com.leiming.utils;

import android.util.Log;

/**
 * 用于打印日志的工具类，使用该工具类是为了方便统一对于打印的日志进行管理
 * @author zcs
 * */
public class LogUser {

	//info级别的信息
	public final static void printI(String tag,String msg){
		Log.i(tag, msg);
	}	
	
}
