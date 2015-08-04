package com.leiming.utils;

import java.util.HashMap;

import com.leiming.bean.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;

/**
 * ���ڻ�ȡ��ǰ�ֻ���һЩ��Ϣ�Ĺ�����
 * @author zcs
 * */
public class AppUtil extends Activity{

	/*private static AppUtil appUtil;
	
    private AppUtil() {
    }
	
	public static AppUtil getInstance(){
		if( appUtil == null ){
			appUtil = new AppUtil();
		}
		return appUtil;
	}*/
	//��ȡmac��ַ
	public static final String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  
	
	/**
     * �ж��Ƿ���sd��
     * */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ��ȡ���ص�SharedPreferences
	 * */
	public static SharedPreferences getLocalSPF(Context context){
		//��ȡ����
		SharedPreferences preferences  = context.getSharedPreferences(Constants.LocalSharedPreferences, context.MODE_WORLD_READABLE);
		return preferences;
	}
	/**
	 * ��ȡ���ص�SharedPreferences.Editor
	 * */
	public static SharedPreferences.Editor getLocalSPFEditor(Context context){
		SharedPreferences.Editor editor  = getLocalSPF(context).edit();
		return editor;
	}
    
	/**
	 * ��logcat�д�ӡ���ܵ�ʵ��
	 * */
	public static void logInfo(String tag,String content){
		Log.i(tag, content);
	}
	
	//info�������Ϣ
	public final static void printI(String tag,String msg){
		Log.i(tag, msg);
	}	
	
	 /**
     * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
     * 
     * @param pxValue
     * @param scale
     *            ��DisplayMetrics��������density��
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
     * 
     * @param dipValue
     * @param scale
     *            ��DisplayMetrics��������density��
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     * 
     * @param pxValue
     * @param fontScale
     *            ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     * 
     * @param spValue
     * @param fontScale
     *            ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
    
    //������תΪ��Ӧ��ƴ��������һ��ƴ����map���ϣ�ƴ�����������ڵ�index��
    /*public static HashMap<String,String> getPingYin(String src) {  
  	  
        char[] srcArray = null;  
        //��ȡ�ַ���תΪ����ı���
        srcArray = src.toCharArray();  
        //���浱�ں���תΪ��ƴ��
        StringBuilder spellString = new StringBuilder();
        //����ת��Ϊƴ�����ֱ���
        String[] temp = new String[srcArray.length];  
        //ʹ�ÿ��תƴ�����õĲ���
        HanyuPinyinOutputFormat params = new HanyuPinyinOutputFormat();  
        params.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        params.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        params.setVCharType(HanyuPinyinVCharType.WITH_V);  
        //����һ��ת��Ϊƴ���ĺ��ӵ�ƴ�����Լ����ں������ڵ�λ��
        HashMap<String,String> spellMap = new HashMap<String,String>();
        //��ȡ�ַ��ĳ���
        int srcLength = srcArray.length;  
        try {  
            for (int i = 0; i < srcLength; i++) {  
                // �ж��Ƿ�Ϊ�����ַ�  
                if (java.lang.Character.toString(srcArray[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                	temp = PinyinHelper.toHanyuPinyinStringArray(srcArray[i], params);  
                	spellMap.put(temp[0], i+"");
                	spellString.append(temp[0]+" ");
                } else{
                	spellMap.put(java.lang.Character.toString(srcArray[i]), i+"");
                	spellString.append(java.lang.Character.toString(srcArray[i])+" ");
                }
            }  
            //����ǰ�ַ�ת��Ϊ��ƴ������
            spellMap.put("spellString",spellString.toString() );
            //System.out.println(t4);  
            return spellMap;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return spellMap;  
    } */
	
	
}
