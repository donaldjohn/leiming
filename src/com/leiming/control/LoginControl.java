package com.leiming.control;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.leiming.bean.Constants;
import com.leiming.bean.ServerBackInfo;
import com.leiming.utils.AppUtil;
import com.leiming.utils.HttpUtil;

/**
 * 登录的控制层
 * */
public class LoginControl {

	//登录判断值，返回服务器返回的json中的state状态值
	public static String login(String username,String localMac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("username", username);
		userParams.put("localMac", localMac);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/login", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State == "200"){ //如果是服务成功，就解析json将服务器端返回的json中的state返回
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//将用户的权限存储到sharep中
					String permission = ob.getString("permission");
					
					Editor editor = AppUtil.getLocalSPFEditor(context);
					editor.putString(Constants.USER_PERMISSION, permission);
					editor.commit();
					
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
