package com.leiming.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
     * 所有题目数据表
     * servierId : 服务器上面的条目的id
     * title: 题目的标题
     * content: 题目的内容（答案）
     * type: 题目的类型
     * operateTime: 服务上该题目的操作时间
     * */
    private String userInfo = "create table if not exists title ("
    		+ "_id integer primary key autoincrement,"
    		+ "servierId,title,content,type,operateTime"
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
