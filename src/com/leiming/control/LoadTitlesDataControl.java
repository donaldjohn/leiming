package com.leiming.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.leiming.bean.ServerBackInfo;
import com.leiming.bean.Title;
import com.leiming.db.TitleDBM;
import com.leiming.utils.HttpUtil;

public class LoadTitlesDataControl {

	//根据类型去服务器进行加载对应类型的题目的数据，将这些数据写入到sqllite数据库中
	public static String loadDataForType(String type,Context context) {
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("type", type);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/question/list", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //如果是服务成功，就解析json将服务器端返回的json中的state返回
				try {
					//获取返回的字符串
					JSONObject ob = new JSONObject(sbi.outStringContent);
					//将获取对应所有的题目数据
					String state = ob.getString("state"); //获取服务器返回的状态
					if(state.equals("1")){ //只有返回数据的时候才会进行存入到本地的数据库中
						TitleDBM tdbm = new TitleDBM(context);
						tdbm.delete(type);
						//获取所有的题目数据
						String titles = ob.getString("titles");
						if( !TextUtils.isEmpty(titles) ){
							
							Title title;
							//如果不为空则将获取的所有的题目数据存放到本地数据库中
							JSONArray titlesJson = new JSONArray(titles);
							if(titlesJson.length() > 0){
								//先将当前数据库中的所有该类型的数据参数
								//再根据获取的数据，插入到本地的数据库中   , titleJson.getString("operateTime")
								for( int i=0 ;i<titlesJson.length();i++ ){
									JSONObject titleJson =  titlesJson.getJSONObject(i);
									title = new Title(titleJson.getInt("id"),titleJson.getString("title"),
											titleJson.getString("content"),titleJson.getString("type")
											);
									//添加到本地数据库中
									tdbm.add(title);
								}
							}
						}
					}
					//返回服务器返回的state状态
					return state;
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
	
	//根据对应的条件在data_temp中查找符合要求的数据
	//根据条件继续查询对应集合中是否有对应的数据，返回的是符合查询条件的数据
	public static List<Title> getTitlesForCondition(String condition, List<Title> data) {
		//获取所有要查询的条件
		String conditions[] = condition.split("，"); 
		List<Title> queryLsit = new ArrayList<Title>();
		boolean stateYesNo;
		for (Title title : data) {
			stateYesNo = true;
			/*
			 *将所有的题目数据和每个条件进行比对 
			 * 先进行比对标题和答案中是否有当前的数据，如果没有那么再进行比对拼音是否包含
			 * 只有所有的条件都满足的时候才会进行显示
			 * */
			for(String cod : conditions){
				//如果标题或者是内容中有匹配的内容则添加进入data中
				if ( !( (title.title != null && title.title.contains(cod)) || 
						(title.content != null && title.content.contains(cod)) )
						) {
					//如果条件，标题和答案都不满足就设置false，同时break
					stateYesNo =false;
					break;
				} 
			}
			if(stateYesNo){
				//说明该题目所有的条件都满足，那么就要进行显示
				queryLsit.add(title);
			}
			
		}
		return queryLsit;
	} 

}
