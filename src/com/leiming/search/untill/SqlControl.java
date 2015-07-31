package com.leiming.search.untill;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leiming.search.bean.Title;

/**
 * 数据库操作类
 * */
public class SqlControl {
	SQLiteDatabase data;
	SqlHelp help;
	public SqlControl(Context context){
		help =new SqlHelp(context, "leimeng");
	}
	
	public void write(){
	}
	
	public void read(){
		data=help.getReadableDatabase();
	}
	
	public void insert(List<Title> list,String type){
		data=help.getWritableDatabase();
		data.beginTransaction();        //手动设置开始事务
		//数据插入操作循环
		for(Title title :list){
		}
		data.setTransactionSuccessful();        //设置事务处理成功，不设置会自动回滚不提交

		data.endTransaction();   
	}
}
