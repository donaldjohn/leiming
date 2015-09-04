package com.leiming.control;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.leiming.bean.Constants;
import com.leiming.bean.ServerBackInfo;
import com.leiming.utils.AppUtil;
import com.leiming.utils.HttpUtil;

/**
 * 关于User用户的处理控制层
 * */
public class UserControl {

	//登录判断值，返回服务器返回的json中的state状态值
	public static String login(String username,String user_mac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("userName", username);
		userParams.put("userMac", user_mac);
		//用于验证的字段
		StringBuilder proofRule = new StringBuilder(username.substring(0, 5) + user_mac.substring(5, 15));
		userParams.put("userProofRule", proofRule.toString());
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/lmuser/login", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //如果是服务成功，就解析json将服务器端返回的json中的state返回
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//根据返回的state的值进行判断处理操作，1层功能，2没有权限
					if( "1".equals(ob.get("state")) ){
						//将用户的权限存储到sharep中
						String permission = ob.getString("userPermission");
						if( !TextUtils.isEmpty(permission) ){
							AppUtil.logInfo("userpermission", permission);
							Editor editor = AppUtil.getLocalSPFEditor(context);
							editor.putString(Constants.USER_PERMISSION, permission);
							editor.commit();
						}
					}
					
					return ob.getString("state");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				return sbi.State; //直接返回服务器相应的状态值
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//注册
	public static String register(String username,String user_mac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("userName", username);
		userParams.put("userMac", user_mac);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/lmuser/signup", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //如果是服务成功，就解析json将服务器端返回的json中的state返回
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					return ob.getString("state");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				return sbi.State; //直接返回服务器相应的状态值
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
}
