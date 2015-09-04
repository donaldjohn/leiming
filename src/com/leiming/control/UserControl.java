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
 * ����User�û��Ĵ�����Ʋ�
 * */
public class UserControl {

	//��¼�ж�ֵ�����ط��������ص�json�е�state״ֵ̬
	public static String login(String username,String user_mac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("userName", username);
		userParams.put("userMac", user_mac);
		//������֤���ֶ�
		StringBuilder proofRule = new StringBuilder(username.substring(0, 5) + user_mac.substring(5, 15));
		userParams.put("userProofRule", proofRule.toString());
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/lmuser/login", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //����Ƿ���ɹ����ͽ���json���������˷��ص�json�е�state����
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//���ݷ��ص�state��ֵ�����жϴ��������1�㹦�ܣ�2û��Ȩ��
					if( "1".equals(ob.get("state")) ){
						//���û���Ȩ�޴洢��sharep��
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
				return sbi.State; //ֱ�ӷ��ط�������Ӧ��״ֵ̬
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	//ע��
	public static String register(String username,String user_mac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("userName", username);
		userParams.put("userMac", user_mac);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/lmuser/signup", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //����Ƿ���ɹ����ͽ���json���������˷��ص�json�е�state����
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					return ob.getString("state");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				return sbi.State; //ֱ�ӷ��ط�������Ӧ��״ֵ̬
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
}
