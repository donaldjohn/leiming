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
 * ��¼�Ŀ��Ʋ�
 * */
public class LoginControl {

	//��¼�ж�ֵ�����ط��������ص�json�е�state״ֵ̬
	public static String login(String username,String localMac,Context context){
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("username", username);
		userParams.put("localMac", localMac);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/login", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State == "200"){ //����Ƿ���ɹ����ͽ���json���������˷��ص�json�е�state����
				try {
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//���û���Ȩ�޴洢��sharep��
					String permission = ob.getString("permission");
					
					Editor editor = AppUtil.getLocalSPFEditor(context);
					editor.putString(Constants.USER_PERMISSION, permission);
					editor.commit();
					
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
