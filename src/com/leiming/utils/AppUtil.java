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
 * 用于获取当前手机的一些信息的工具类
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
	//获取mac地址
	public static final String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  
	
	/**
     * 判断是否有sd卡
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
	 * 获取本地的SharedPreferences
	 * */
	public static SharedPreferences getLocalSPF(Context context){
		//读取对象
		SharedPreferences preferences  = context.getSharedPreferences(Constants.LocalSharedPreferences, context.MODE_WORLD_READABLE);
		return preferences;
	}
	/**
	 * 获取本地的SharedPreferences.Editor
	 * */
	public static SharedPreferences.Editor getLocalSPFEditor(Context context){
		SharedPreferences.Editor editor  = getLocalSPF(context).edit();
		return editor;
	}
    
	/**
	 * 在logcat中打印功能的实现
	 * */
	public static void logInfo(String tag,String content){
		Log.i(tag, content);
	}
	
	//info级别的信息
	public final static void printI(String tag,String msg){
		Log.i(tag, msg);
	}	
	
	 /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
    
    //将汉字转为对应的拼音，返回一个拼音的map集合（拼音，汉字所在的index）
    /*public static HashMap<String,String> getPingYin(String src) {  
  	  
        char[] srcArray = null;  
        //获取字符串转为数组的变量
        srcArray = src.toCharArray();  
        //保存当期汉子转为的拼音
        StringBuilder spellString = new StringBuilder();
        //保存转换为拼音的字变量
        String[] temp = new String[srcArray.length];  
        //使用框架转拼音设置的参数
        HanyuPinyinOutputFormat params = new HanyuPinyinOutputFormat();  
        params.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        params.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        params.setVCharType(HanyuPinyinVCharType.WITH_V);  
        //保存一个转换为拼音的汉子的拼音，以及当期汉子所在的位置
        HashMap<String,String> spellMap = new HashMap<String,String>();
        //获取字符的长度
        int srcLength = srcArray.length;  
        try {  
            for (int i = 0; i < srcLength; i++) {  
                // 判断是否为汉字字符  
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
            //将当前字符转化为的拼音保存
            spellMap.put("spellString",spellString.toString() );
            //System.out.println(t4);  
            return spellMap;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return spellMap;  
    } */
	
	
}
