package com.leiming.asynctask;

import com.leiming.utils.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * AsyncTask的一个继承类，这里将AsyncTask常用的判断逻辑进行设置，然后
 * 其他的AsyncTask类基础该类
 * */
public abstract class BaseAsyncTask extends AsyncTask<String,Integer,String>{
	
	private Context context;
	
	public BaseAsyncTask(){}
	
	public BaseAsyncTask(Context context) {
		this.context = context;
	}
	//这里基础onPostExecute，将关于网络后状态的判断设置到这里，这样
	//再继承该类的类对象就不用写了
	protected void onPostExecute(String result){//运行在主线程
		//判断是否是网络的异常，这是一个基类用于将服务器的异常情况进行对应的处理
		//至于服务器返回数据的判断就交给上层了
		switch (Integer.parseInt(result)) {
			case HttpUtil.SERVERTIMEOUT:
				Toast.makeText(context, "网络连接超时，请联系管理员！",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.REQUESTTIMEOUT:
				Toast.makeText(context, "网络请求超时！",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.NETPARSEERROR:
				Toast.makeText(context, "网络解析异常！",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.NONETWORKS:
				Toast.makeText(context, "网络未连接！",Toast.LENGTH_SHORT).show();
				break;
			case 404:
				Toast.makeText(context, "服务器断开连接！",Toast.LENGTH_SHORT).show();
				break;
			case 500:
				Toast.makeText(context, "ServerNoFoud！",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
	}	
	
}
