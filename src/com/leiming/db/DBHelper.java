package com.leiming.db;

import com.leiming.bean.Title;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * ���ڴ�����ǰapp��Ŀ�ı���sqllit���ݿ�
 * */
public class DBHelper extends SQLiteOpenHelper{

	//������
	private static final String DATABASE_NAME = "leiming.db";  
	//���ݿ�汾
    private static final int DATABASE_VERSION = 1;  
    //�û���Ϣ��
    /**
     * �û���Ϣ���������Ϣ
     * user_num : �û�ѧ��/����
     * user_head: �û�ͷ������
     * */
    private String userInfo = "create table if not exists localUserInfo ("
    		+ "_id integer primary key autoincrement,"
    		+ "user_num,"
    		+ "user_head"
    		+ ")";
	
	public DBHelper(Context context){  
		//���ﴴ�������ݿ�����data\dataӦ�ó���İ�װĿ¼�µģ�ͬʱʹ��SQLiteOpenHelperֻ�ܴ����ڸ�Ŀ¼�µ����ݿ�
        super(context, context.getFilesDir().toString()+DATABASE_NAME, null, DATABASE_VERSION);  
        
    }  
  
	//���ݿ��һ�α�����ʱonCreate�ᱻ����  
	@Override
	public void onCreate(SQLiteDatabase db) {
		//����ִ�����ݿ�ĳ�ʼ���������
		//�����û���Ϣ�������
		db.execSQL(userInfo);
	}
	
	//���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade  
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
