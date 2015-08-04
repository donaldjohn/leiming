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

	//根据类型去服务器进行加载对应类型的题目的数据，将这些数据写入到sqllite数据库中
	public static String loadDataForType(String type,Context context) {
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("type", type);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/getDataForType", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //如果是服务成功，就解析json将服务器端返回的json中的state返回
				try {
					//获取返回的字符串
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//将获取对应所有的题目数据
					String titles = ob.getString("titles");
					if( !TextUtils.isEmpty(titles) ){
						//如果不为空则将获取的所有的题目数据存放到本地数据库中
						JSONArray titlesJson = new JSONArray(titles);
						for( int i=0 ;i<titlesJson.length();i++ ){
							JSONObject title =  titlesJson.getJSONObject(i);
							
						}
					}
					//返回服务器返回的state状态
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
