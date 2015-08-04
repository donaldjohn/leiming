package com.leiming.control;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.leiming.bean.Constants;
import com.leiming.bean.ServerBackInfo;
import com.leiming.utils.AppUtil;
import com.leiming.utils.HttpUtil;

public class LoadTitlesDataControl {

	//��������ȥ���������м��ض�Ӧ���͵���Ŀ�����ݣ�����Щ����д�뵽sqllite���ݿ���
	public static String loadDataForType(String type,Context context) {
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("type", type);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/getDataForType", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //����Ƿ���ɹ����ͽ���json���������˷��ص�json�е�state����
				try {
					//��ȡ���ص��ַ���
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//����ȡ��Ӧ���е���Ŀ����
					String titles = ob.getString("titles");
					if( !TextUtils.isEmpty(titles) ){
						//�����Ϊ���򽫻�ȡ�����е���Ŀ���ݴ�ŵ��������ݿ���
						JSONArray titlesJson = new JSONArray(titles);
						for( int i=0 ;i<titlesJson.length();i++ ){
							JSONObject title =  titlesJson.getJSONObject(i);
							
						}
					}
					//���ط��������ص�state״̬
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
