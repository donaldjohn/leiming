package com.leiming.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
     * ������Ŀ���ݱ�
     * servierId : �������������Ŀ��id
     * title: ��Ŀ�ı���
     * content: ��Ŀ�����ݣ��𰸣�
     * type: ��Ŀ������
     * operateTime: �����ϸ���Ŀ�Ĳ���ʱ��
     * */
    private String userInfo = "create table if not exists title ("
    		+ "_id integer primary key autoincrement,"
    		+ "servierId,title,content,type,operateTime"
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
