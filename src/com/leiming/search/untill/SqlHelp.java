package com.leiming.search.untill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelp extends SQLiteOpenHelper{

	public SqlHelp(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public SqlHelp(Context context, String name,
			int version) {
		super(context, name,null, version);
		// TODO Auto-generated constructor stub
	}
	public SqlHelp(Context context, String name) {
		super(context, name,null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table user(id int,value varchar(200))");
		db.execSQL("create table anwser (id int ,value varchar(3000) ,type varchar(30))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
