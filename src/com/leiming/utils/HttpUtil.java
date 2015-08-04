package com.leiming.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.leiming.bean.ServerBackInfo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * 进行发送请求的工具类
 * 包括post，get提交两种方式
 * @author zcs
 * */
public class HttpUtil {

	/**
	 * 连接超时
	 * */
	public static final int SERVERTIMEOUT = -1000;
	/**
	 * 请求超时
	 * */
	public static final int REQUESTTIMEOUT = -1001;
	/**
	 * 未联网
	 * */
	public static final int NONETWORKS = -1002;
	/**
	 * 网络解析异常（对服务器返回的数据进行错误）
	 * */
	public static final int NETPARSEERROR = -1004;
	//连接超时设置时间
	public static final int TIME_OUT_DELAY = 5000;
	
	//获取httpclient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	//home的地址
	//public static final String BASE_URL = "http://10.18.1.103:8080/leimingService";
	//服务器的地址
	public static final String BASE_URL = "http://123.57.81.43:8090/leimingService";
	//公司的地址
	//public static final String BASE_URL = "http://192.168.15.117:8090/leimingService";
	//设置httpClient的参数
	public static void setHtppClientParmas(){
		//设置httpClient的请求超时
		httpClient.getParams().setIntParameter(  
                HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // 超时设置  ,请求超时
		httpClient.getParams().setIntParameter(  
                HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// 连接超时 ，读取超时
	}
	
	/**
	 * 获取服务器中相应的请求
	 * @param param 请求参数
	 * @param type 要进行请求的数据类型
	 * @return 服务器返回的数据包装类
	 * */
	public static ServerBackInfo getRequst(final String param,final String type,Context context) throws Exception{
		httpClient = new DefaultHttpClient();
		final ServerBackInfo sbt = new ServerBackInfo();
		if(isOnline(context)){
			//使用futuretask进行实现发送请求
			FutureTask<ServerBackInfo> futureTask = new FutureTask<ServerBackInfo>(new Callable<ServerBackInfo>() {
				
				//该类被调用的时候回执行的方法，在该方法中进行实现获取服务器返回的数据
				public ServerBackInfo call(){
					HttpGet httpGet = new HttpGet(param);
					//设置httpClient的请求超时
					setHtppClientParmas();
					HttpResponse httpResponse;
					try {
						httpResponse = httpClient.execute(httpGet);
						//获取请求的返回参数
						sbt.State = httpResponse.getStatusLine().getStatusCode()+"";
						sbt.ServerBackType = type;
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							//如果请求成功，那么根据要返回的参数类型进行生成设置返回参数
							if( type.endsWith(ServerBackInfo.TYPE_INPUTSTREAM) ){ //要请求的是inputstrem
								sbt.outIs = httpResponse.getEntity().getContent();
							}else if( type.endsWith(ServerBackInfo.TYPE_STRING) ){ //要请求的是string
								String result = EntityUtils.toString(httpResponse.getEntity()); //获取服务器返回的字符串
								result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
								sbt.outStringContent = result;
							}
						}
					}catch(ConnectTimeoutException e){  
						e.printStackTrace();
						Log.i("HTTP","ConnectTimeoutException,连接超时");
						sbt.State = HttpUtil.SERVERTIMEOUT+"";
						//将当前的错误代码进行返回
	                } catch (ClientProtocolException e) {
						e.printStackTrace();
						Log.i("HTTP","ClientProtocolException,请求超时,服务器不可到达");
						sbt.State = HttpUtil.REQUESTTIMEOUT+"";
					} catch (IOException e) {
						e.printStackTrace();
						Log.i("HTTP","IOException，解析异常");
						sbt.State = HttpUtil.NETPARSEERROR+"";
					}
					//将服务器返回数据的包装进行进行返回
					return sbt;
				}
			});
			//新建一个线程，执行从服务器发送请求的方法
			new Thread(futureTask).start();
			return futureTask.get(); //获取futrueTask执行后返回的数据信息
		}else{
			sbt.State = HttpUtil.NONETWORKS+"";
			return sbt;
		}
	}
	
	/**
	 * 向服务器post提交，同时发送请求参数，获取服务器相应的请求
	 * @param url 请求的地址
	 * @param type 要返回的数据类型
	 * @param rawsParams post提交的参数
	 * */
	public static ServerBackInfo postRequst(final String url,final String type,final Map<String,String> rawsParams,Context context) throws Exception{
		final ServerBackInfo sbt = new ServerBackInfo();
		//先进行判断当前的网络环境是否正常，如果没有网络连接直接放回就行了
		if(isOnline(context)){
			//使用futuretask进行实现发送请求
			FutureTask<ServerBackInfo> futureTask = new FutureTask<ServerBackInfo>(new Callable<ServerBackInfo>() {
				
				//该类被调用的时候回执行的方法，在该方法中进行实现获取服务器返回的数据
				public ServerBackInfo call() {
					HttpPost httpPost = new HttpPost(url);
					//如果传递的参数比较多，可以对传递的参数进行封装
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					//将请求参数进行封装到params中
					for(String key : rawsParams.keySet()){
						params.add(new BasicNameValuePair(key, rawsParams.get(key)));
					}
					//将请求参数进行放入post请求的实体中
					try {
						httpPost.setEntity(new UrlEncodedFormEntity(params,"gbk"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					//设置httpClient的请求超时
					setHtppClientParmas();
					//发送post请求
					HttpResponse httpResponse = null;
					try {
						httpResponse = httpClient.execute(httpPost);
						//获取请求的返回参数
						sbt.State = httpResponse.getStatusLine().getStatusCode()+"";
						sbt.ServerBackType = type;
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							if( type.endsWith(ServerBackInfo.TYPE_INPUTSTREAM) ){ //要请求的是inputstrem
								sbt.outIs = httpResponse.getEntity().getContent();
							}else if( type.endsWith(ServerBackInfo.TYPE_STRING) ){ //要请求的是string
								String result = EntityUtils.toString(httpResponse.getEntity()); //获取服务器返回的字符串
								result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
								sbt.outStringContent = result;
							}
							return sbt;
						}
					}catch(ConnectTimeoutException e){  
						e.printStackTrace();
						Log.i("HTTP","ConnectTimeoutException,连接超时");
						sbt.State = HttpUtil.SERVERTIMEOUT+"";
						//将当前的错误代码进行返回
	                } catch (ClientProtocolException e) {
						e.printStackTrace();
						Log.i("HTTP","ClientProtocolException,请求超时,服务器不可到达");
						sbt.State = HttpUtil.REQUESTTIMEOUT+"";
					} catch (IOException e) {
						e.printStackTrace();
						Log.i("HTTP","IOException，解析异常");
						sbt.State = HttpUtil.NETPARSEERROR+"";
					}
					return sbt;
				}
			});
			//新建一个线程，执行从服务器发送请求的方法
			new Thread(futureTask).start();
			return futureTask.get(); //获取futrueTask执行后返回的数据信息
		}else{
			//如果没有网直接返回对应的状态值
			sbt.State = HttpUtil.NONETWORKS+"";
			return sbt;
		}
		
	}
	
	//判断当前的网络是否连接
	public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }
	

}
