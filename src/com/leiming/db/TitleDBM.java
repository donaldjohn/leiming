package com.leiming.db;

import java.util.ArrayList;
import java.util.List;

import com.leiming.bean.Title;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ������������µ���Ŀ�����ݿ������
 * */
public class TitleDBM {
	private DBHelper helper;  
    private SQLiteDatabase db;  
      
    public TitleDBM(Context context) {  
        helper = new DBHelper(context);  
        //��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��  
        db = helper.getWritableDatabase();  
    }  
      
    /** 
     * add Title 
     */  
    public void add(List<Title> userinfos) {  
        db.beginTransaction();  //��ʼ����  
        try {  
            for (Title userinfo : userinfos) {  
                db.execSQL("insert into Title values(null, ?, ?)", new Object[]{userinfo.title,userinfo.content});  
            }  
            db.setTransactionSuccessful();  //��������ɹ����  
        } finally {  
            db.endTransaction();    //��������  
        }  
    }  
      
    /** 
     * update Title
     * @param userinfo 
     */  
    public void updateAge(Title userinfo) {  
    	db.execSQL("update Title set user_head=? where user_num = ?",new Object[]{userinfo.title,userinfo.content});
    }  
      
    /** 
     * delete old Title 
     * @param userinfo 
     */  
    public void deleteOldPerson(Title userinfo) {  
        db.execSQL("delete from Title where user_num = ?", new Object[]{userinfo.title});  
    }  
      
    /** 
     * query all Title, return list 
     * @return List<Title> 
     */  
    public List<Title> query() {  
        ArrayList<Title> userinfos = new ArrayList<Title>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
        	Title userinfo = new Title();  
        	userinfo._id = c.getInt(c.getColumnIndex("_id"));  
        	userinfo.title = c.getString(c.getColumnIndex("title"));  
        	userinfo.content = c.getString(c.getColumnIndex("content"));  
        	userinfos.add(userinfo);  
        }  
        c.close();  
        return userinfos;  
    }  
      
    /**
     * ����ѧ��/���Ż�ȡ�û�����
     * */
    public Title getInfoByUserNum(String user_num){
    	Cursor c = db.rawQuery("select * from Title where user_num = ? ", new String[]{user_num});  
    	Title lu = new Title();
    	if(c.getCount() > 0){
    		//Ҫ�Ƚ�Cursor�ƶ�����һ�����ܽ��л�ȡ����
    		c.moveToFirst(); 
    		lu.title = c.getString(c.getColumnIndex("title")); 
        	lu.content = c.getString(c.getColumnIndex("content")); 
    	}
    	return lu;
    }
    
    /** 
     * query all Title, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
    	//ʹ��SQLiteDatabaseִ�в�ѯ���
        Cursor c = db.rawQuery("select * from Title", null);  
        return c;  
    }  
      
    /** 
     * close database 
     */  
    public void closeDB() {  
        db.close();  
    }
    //�������ͽ��л�ȡ��Ӧ�����е���Ŀ��������Ϣ
	public void getAllTitlesForType(String type) {
		ArrayList<Title> titles = new ArrayList<Title>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
        	Title title = new Title();  
        	title._id = c.getInt(c.getColumnIndex("_id"));  
        	title.title = c.getString(c.getColumnIndex("title"));  
        	title.content = c.getString(c.getColumnIndex("content"));  
        	titles.add(title);  
        }  
        c.close();  
        return titles;  
	}  
}
