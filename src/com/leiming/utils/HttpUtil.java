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
 * ���з�������Ĺ�����
 * ����post��get�ύ���ַ�ʽ
 * @author zcs
 * */
public class HttpUtil {

	/**
	 * ���ӳ�ʱ
	 * */
	public static final int SERVERTIMEOUT = -1000;
	/**
	 * ����ʱ
	 * */
	public static final int REQUESTTIMEOUT = -1001;
	/**
	 * δ����
	 * */
	public static final int NONETWORKS = -1002;
	/**
	 * ��������쳣���Է��������ص����ݽ��д���
	 * */
	public static final int NETPARSEERROR = -1004;
	//���ӳ�ʱ����ʱ��
	public static final int TIME_OUT_DELAY = 5000;
	
	//��ȡhttpclient����
	public static HttpClient httpClient = new DefaultHttpClient();
	//home�ĵ�ַ
	//public static final String BASE_URL = "http://10.18.1.103:8080/leimingService";
	//�������ĵ�ַ
	public static final String BASE_URL = "http://123.57.81.43:8090/leimingService";
	//��˾�ĵ�ַ
	//public static final String BASE_URL = "http://192.168.15.117:8090/leimingService";
	//����httpClient�Ĳ���
	public static void setHtppClientParmas(){
		//����httpClient������ʱ
		httpClient.getParams().setIntParameter(  
                HttpConnectionParams.SO_TIMEOUT, TIME_OUT_DELAY); // ��ʱ����  ,����ʱ
		httpClient.getParams().setIntParameter(  
                HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT_DELAY);// ���ӳ�ʱ ����ȡ��ʱ
	}
	
	/**
	 * ��ȡ����������Ӧ������
	 * @param param �������
	 * @param type Ҫ�����������������
	 * @return ���������ص����ݰ�װ��
	 * */
	public static ServerBackInfo getRequst(final String param,final String type,Context context) throws Exception{
		httpClient = new DefaultHttpClient();
		final ServerBackInfo sbt = new ServerBackInfo();
		if(isOnline(context)){
			//ʹ��futuretask����ʵ�ַ�������
			FutureTask<ServerBackInfo> futureTask = new FutureTask<ServerBackInfo>(new Callable<ServerBackInfo>() {
				
				//���౻���õ�ʱ���ִ�еķ������ڸ÷����н���ʵ�ֻ�ȡ���������ص�����
				public ServerBackInfo call(){
					HttpGet httpGet = new HttpGet(param);
					//����httpClient������ʱ
					setHtppClientParmas();
					HttpResponse httpResponse;
					try {
						httpResponse = httpClient.execute(httpGet);
						//��ȡ����ķ��ز���
						sbt.State = httpResponse.getStatusLine().getStatusCode()+"";
						sbt.ServerBackType = type;
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							//�������ɹ�����ô����Ҫ���صĲ������ͽ����������÷��ز���
							if( type.endsWith(ServerBackInfo.TYPE_INPUTSTREAM) ){ //Ҫ�������inputstrem
								sbt.outIs = httpResponse.getEntity().getContent();
							}else if( type.endsWith(ServerBackInfo.TYPE_STRING) ){ //Ҫ�������string
								String result = EntityUtils.toString(httpResponse.getEntity()); //��ȡ���������ص��ַ���
								result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
								sbt.outStringContent = result;
							}
						}
					}catch(ConnectTimeoutException e){  
						e.printStackTrace();
						Log.i("HTTP","ConnectTimeoutException,���ӳ�ʱ");
						sbt.State = HttpUtil.SERVERTIMEOUT+"";
						//����ǰ�Ĵ��������з���
	                } catch (ClientProtocolException e) {
						e.printStackTrace();
						Log.i("HTTP","ClientProtocolException,����ʱ,���������ɵ���");
						sbt.State = HttpUtil.REQUESTTIMEOUT+"";
					} catch (IOException e) {
						e.printStackTrace();
						Log.i("HTTP","IOException�������쳣");
						sbt.State = HttpUtil.NETPARSEERROR+"";
					}
					//���������������ݵİ�װ���н��з���
					return sbt;
				}
			});
			//�½�һ���̣߳�ִ�дӷ�������������ķ���
			new Thread(futureTask).start();
			return futureTask.get(); //��ȡfutrueTaskִ�к󷵻ص�������Ϣ
		}else{
			sbt.State = HttpUtil.NONETWORKS+"";
			return sbt;
		}
	}
	
	/**
	 * �������post�ύ��ͬʱ���������������ȡ��������Ӧ������
	 * @param url ����ĵ�ַ
	 * @param type Ҫ���ص���������
	 * @param rawsParams post�ύ�Ĳ���
	 * */
	public static ServerBackInfo postRequst(final String url,final String type,final Map<String,String> rawsParams,Context context) throws Exception{
		final ServerBackInfo sbt = new ServerBackInfo();
		//�Ƚ����жϵ�ǰ�����绷���Ƿ����������û����������ֱ�ӷŻؾ�����
		if(isOnline(context)){
			//ʹ��futuretask����ʵ�ַ�������
			FutureTask<ServerBackInfo> futureTask = new FutureTask<ServerBackInfo>(new Callable<ServerBackInfo>() {
				
				//���౻���õ�ʱ���ִ�еķ������ڸ÷����н���ʵ�ֻ�ȡ���������ص�����
				public ServerBackInfo call() {
					HttpPost httpPost = new HttpPost(url);
					//������ݵĲ����Ƚ϶࣬���ԶԴ��ݵĲ������з�װ
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					//������������з�װ��params��
					for(String key : rawsParams.keySet()){
						params.add(new BasicNameValuePair(key, rawsParams.get(key)));
					}
					//������������з���post�����ʵ����
					try {
						httpPost.setEntity(new UrlEncodedFormEntity(params,"gbk"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					//����httpClient������ʱ
					setHtppClientParmas();
					//����post����
					HttpResponse httpResponse = null;
					try {
						httpResponse = httpClient.execute(httpPost);
						//��ȡ����ķ��ز���
						sbt.State = httpResponse.getStatusLine().getStatusCode()+"";
						sbt.ServerBackType = type;
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							if( type.endsWith(ServerBackInfo.TYPE_INPUTSTREAM) ){ //Ҫ�������inputstrem
								sbt.outIs = httpResponse.getEntity().getContent();
							}else if( type.endsWith(ServerBackInfo.TYPE_STRING) ){ //Ҫ�������string
								String result = EntityUtils.toString(httpResponse.getEntity()); //��ȡ���������ص��ַ���
								result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
								sbt.outStringContent = result;
							}
							return sbt;
						}
					}catch(ConnectTimeoutException e){  
						e.printStackTrace();
						Log.i("HTTP","ConnectTimeoutException,���ӳ�ʱ");
						sbt.State = HttpUtil.SERVERTIMEOUT+"";
						//����ǰ�Ĵ��������з���
	                } catch (ClientProtocolException e) {
						e.printStackTrace();
						Log.i("HTTP","ClientProtocolException,����ʱ,���������ɵ���");
						sbt.State = HttpUtil.REQUESTTIMEOUT+"";
					} catch (IOException e) {
						e.printStackTrace();
						Log.i("HTTP","IOException�������쳣");
						sbt.State = HttpUtil.NETPARSEERROR+"";
					}
					return sbt;
				}
			});
			//�½�һ���̣߳�ִ�дӷ�������������ķ���
			new Thread(futureTask).start();
			return futureTask.get(); //��ȡfutrueTaskִ�к󷵻ص�������Ϣ
		}else{
			//���û����ֱ�ӷ��ض�Ӧ��״ֵ̬
			sbt.State = HttpUtil.NONETWORKS+"";
			return sbt;
		}
		
	}
	
	//�жϵ�ǰ�������Ƿ�����
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
