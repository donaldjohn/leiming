package com.leiming.untill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * sqllite数据库建立类
 * */
public class SqlHelp extends SQLiteOpenHelper{

	public SqlHelp(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public SqlHelp(Context context, String name,
			int version) {
		super(context, name,null, version);
	}
	public SqlHelp(Context context, String name) {
		super(context, name,null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table user(id int,value varchar(200))");
		db.execSQL("create table anwser (id int ,value varchar(3000) ,type varchar(30))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
