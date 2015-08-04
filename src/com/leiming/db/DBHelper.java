package com.leiming.db;

import com.leiming.bean.Title;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * 用于创建当前app项目的本地sqllit数据库
 * */
public class DBHelper extends SQLiteOpenHelper{

	//数据名
	private static final String DATABASE_NAME = "leiming.db";  
	//数据库版本
    private static final int DATABASE_VERSION = 1;  
    //用户信息表
    /**
     * 用户信息表的数据信息
     * user_num : 用户学号/工号
     * user_head: 用户头像数据
     * */
    private String userInfo = "create table if not exists localUserInfo ("
    		+ "_id integer primary key autoincrement,"
    		+ "user_num,"
    		+ "user_head"
    		+ ")";
	
	public DBHelper(Context context){  
		//这里创建的数据库是在data\data应用程序的安装目录下的，同时使用SQLiteOpenHelper只能创建在该目录下的数据库
        super(context, context.getFilesDir().toString()+DATABASE_NAME, null, DATABASE_VERSION);  
        
    }  
  
	//数据库第一次被创建时onCreate会被调用  
	@Override
	public void onCreate(SQLiteDatabase db) {
		//这里执行数据库的初始化建表操作
		//创建用户信息表的数据
		db.execSQL(userInfo);
	}
	
	//如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade  
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
