package com.leiming.search.untill;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leiming.search.bean.Title;

/**
 * ���ݿ������
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
		data.beginTransaction();        //�ֶ����ÿ�ʼ����
		//���ݲ������ѭ��
		for(Title title :list){
		}
		data.setTransactionSuccessful();        //����������ɹ��������û��Զ��ع����ύ

		data.endTransaction();   
	}
}
