/**
 * 包换一些书
 * */
package com.leiming.utils;

import java.text.SimpleDateFormat;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Container {

	public static String[] username;
	public static String[] pwd;
	public static String[] limit;
	public static int current_user;
	//保存当前点击的是那个类型
	public static unit current_unit;

	/**  
	 * 	权限定义在limit&& 之后
	 * 电脑，会计，教师资格 ，统考    computer , accounting , teacher , exam 
	"三级心理咨询师", "三级人力资源管理师", "三级秘书资格证", "高级育婴师",
	"公共营养师", "物流师", "企业培训师", "理财规划师"
	对应 exama~examh
	 **/
	public enum unit {
		COMPUTER("computer"), ACCOUNTING("accounting"), TEACHER("teacher"), EXAM(
				"exam"), EXAMA("exama"), EXAMB("examb"), EXAMC("examc"), EXAMD(
				"examd"), EXAME("exame"), EXAMF("examf"), EXAMG("examg"), EXAMH(
				"examh");

		private String unit;

		private unit(String unit) {
			this.unit = unit;
		}

		public String getValue() {
			return unit;
		}
		
		public String getEXAMlimit(int postions){
			switch (postions) {
			case 0:
				return EXAMA.getValue();
			case 1:
				return EXAMB.getValue();
			case 2:
				return EXAMC.getValue();
			case 3:
				return EXAMD.getValue();
			case 4:
				return EXAME.getValue();
			case 5:
				return EXAMF.getValue();
			case 6:
				return EXAMG.getValue();
			default:
				return EXAMH.getValue();
			}
		}
		
		public unit postionToEnum(int postions){
			switch (postions) {
			case 0:
				return EXAMA;
			case 1:
				return EXAMB;
			case 2:
				return EXAMC;
			case 3:
				return EXAMD;
			case 4:
				return EXAME;
			case 5:
				return EXAMF;
			case 6:
				return EXAMG;
			default:
				return EXAMH;
			}
		}
		
	}
	//universalimageloader进行加载图片设置的配置信息
	final public static DisplayImageOptions adUrl_options = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.displayer(new RoundedBitmapDisplayer(20))
			//是否设置为圆角，弧度为多少
			.cacheInMemory().cacheOnDisc().build();

	final public static DisplayImageOptions adUrl = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.cacheInMemory().cacheOnDisc().build();
	//所有的证书图pain数据
	public static String[] url = new String[] { "assets://1.jpg",
			"assets://2.jpg", "assets://3.jpg", "assets://4.jpg",
			"assets://5.jpg", "assets://6.jpg", "assets://7.jpg",
			"assets://8.jpg", "assets://9.jpg", "assets://10.jpg",
			"assets://11.jpg", "assets://12.jpg", "assets://13.jpg",
			"assets://14.jpg", "assets://15.jpg", "assets://16.jpg" };
	

	static boolean getStatus;
	public static  boolean getstatus(Context context){
		SharedPreferences share = getSharedPreferences(context);
		int time=share.getInt("time", 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
		String date=sdf.format(new java.util.Date());  

		int currentTime =Integer.valueOf(date);
		if(currentTime<time){
			return true;
		}
		return false;
	}
	
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("currentTime", Context.MODE_PRIVATE);
	}
	
	public static void saveDate(Context context){
		SharedPreferences share = getSharedPreferences(context);
		Editor editor =share.edit();
		editor.putInt("time", 20140922);
		editor.commit();
	}

}
