package com.leiming.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.leiming.bean.ServerBackInfo;
import com.leiming.bean.Title;
import com.leiming.db.TitleDBM;
import com.leiming.utils.HttpUtil;

public class LoadTitlesDataControl {

	//��������ȥ���������м��ض�Ӧ���͵���Ŀ�����ݣ�����Щ����д�뵽sqllite���ݿ���
	public static String loadDataForType(String type,Context context) {
		Map<String,String> userParams = new HashMap<String,String>();
		userParams.put("type", type);
		ServerBackInfo sbi = null;
		try {
			sbi = HttpUtil.postRequst(HttpUtil.BASE_URL+"/service/question/list", ServerBackInfo.TYPE_STRING, userParams, context);
			if(sbi.State.equals("200")){ //����Ƿ���ɹ����ͽ���json���������˷��ص�json�е�state����
				//��ȡ���ص��ַ���
				JSONObject ob = new JSONObject(sbi.outStringContent);
				//����ȡ��Ӧ���е���Ŀ����
				String state = ob.getString("state"); //��ȡ���������ص�״̬
				if(state.equals("1")){ //ֻ�з������ݵ�ʱ��Ż���д��뵽���ص����ݿ���
					TitleDBM tdbm = new TitleDBM(context);
					tdbm.delete(type);
					//��ȡ���е���Ŀ����
					String titles = ob.getString("titles");
					if( !TextUtils.isEmpty(titles) ){
						
						Title title = null;
						//�����Ϊ���򽫻�ȡ�����е���Ŀ���ݴ�ŵ��������ݿ���
						JSONArray titlesJson = new JSONArray(titles);
						if(titlesJson.length() > 0){
							//�Ƚ���ǰ���ݿ��е����и����͵����ݲ���
							//�ٸ��ݻ�ȡ�����ݣ����뵽���ص����ݿ���   , titleJson.getString("operateTime")
							for( int i=0 ;i<titlesJson.length();i++ ){
								JSONObject titleJson =  titlesJson.getJSONObject(i);
								int id = -1;
								String titleValue = "";
								String contentValue = "";
								String typeValue = "";
								try {
									id = titleJson.getInt("id");
									typeValue = titleJson.getString("type");
									titleValue = titleJson.getString("title");
									contentValue = titleJson.getString("content");
								} catch (Exception e) {
									
								}
								title = new Title(id,titleValue,contentValue,typeValue);
								//��ӵ��������ݿ���
								tdbm.add(title);
							}
						}
					}
				}
				//���ط��������ص�state״̬
				return state;
			}else{
				return sbi.State; //ֱ�ӷ��ط�������Ӧ��״ֵ̬
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "4";
	}
	
	//���ݶ�Ӧ��������data_temp�в��ҷ���Ҫ�������
	//��������������ѯ��Ӧ�������Ƿ��ж�Ӧ�����ݣ����ص��Ƿ��ϲ�ѯ����������
	public static List<Title> getTitlesForCondition(String condition, List<Title> data) {
		//��ȡ����Ҫ��ѯ������
		String conditions[] = condition.split("��"); 
		List<Title> queryLsit = new ArrayList<Title>();
		boolean stateYesNo;
		for (Title title : data) {
			stateYesNo = true;
			/*
			 *�����е���Ŀ���ݺ�ÿ���������бȶ� 
			 * �Ƚ��бȶԱ���ʹ����Ƿ��е�ǰ�����ݣ����û����ô�ٽ��бȶ�ƴ���Ƿ����
			 * ֻ�����е������������ʱ��Ż������ʾ
			 * */
			for(String cod : conditions){
				//��������������������ƥ�����������ӽ���data��
				if ( !( (title.title != null && title.title.contains(cod)) || 
						(title.content != null && title.content.contains(cod)) )
						) {
					//�������������ʹ𰸶������������false��ͬʱbreak
					stateYesNo =false;
					break;
				} 
			}
			if(stateYesNo){
				//˵������Ŀ���е����������㣬��ô��Ҫ������ʾ
				queryLsit.add(title);
			}
			
		}
		return queryLsit;
	} 

}
