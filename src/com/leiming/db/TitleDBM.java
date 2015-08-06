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
      
    public void add(List<Title> titleValues) {  
        db.beginTransaction();  //��ʼ����  
        try {  
            for (Title titleValue : titleValues) {  
                db.execSQL("insert into title(null,?,?,?,?,?)", 
            		new Object[]{titleValue.serverId,titleValue.title,titleValue.content,titleValue.type,titleValue.operateTime});  
            }  
            db.setTransactionSuccessful();  //��������ɹ����  
        } finally {  
            db.endTransaction();//��������  
        }  
    }  
    
    public void add(Title titleValue) {  
		db.execSQL("insert into title values(null,?,?,?,?,?)", 
				new Object[]{titleValue.serverId,titleValue.title,titleValue.content,titleValue.type,titleValue.operateTime});  
    }  
      
    public void update(Title titleValue) {  
    	//servierId,title,content,type,operateTime
    	db.execSQL("update title set servierId=?,title=?,content=?,type=?,operateTime=? where _id = ?",
		new Object[]{titleValue.serverId,titleValue.title,titleValue.content,titleValue.type,titleValue.operateTime,titleValue._id});
    }  
    //����titleɾ��
    public void delete(Title titleValue) {  
        db.execSQL("delete from title where _id = ?", new Object[]{titleValue._id});  
    }  
    //���ݶ�Ӧ�����ͽ�����ȫ��ɾ��
    public void delete(String type){
    	db.execSQL("delete from title where type = ?", new Object[]{type}); 
    }
      
    /** 
     * ��ȡ���е���Ŀ����
     * @return List<Title> 
     */  
    public List<Title> query() {  
        ArrayList<Title> titleValues = new ArrayList<Title>();  
        Cursor c = db.rawQuery("select * from title", null);   
        while (c.moveToNext()) {  
        	Title titleValue = new Title();  
        	titleValue._id = c.getInt(c.getColumnIndex("_id"));  
        	titleValue.serverId = c.getInt(c.getColumnIndex("servierId"));  
        	titleValue.title = c.getString(c.getColumnIndex("title"));  
        	titleValue.content = c.getString(c.getColumnIndex("content"));  
        	titleValue.type = c.getString(c.getColumnIndex("type"));  
        	titleValue.operateTime = c.getString(c.getColumnIndex("operateTime"));  
        	titleValues.add(titleValue);  
        }  
        c.close();  
        return titleValues;  
    }  
    
    /**
     * �������ͽ��л�ȡ��Ӧ�����е���Ŀ��������Ϣ
     * */
	public ArrayList<Title> getAllTitlesForType(String type) {
		ArrayList<Title> titles = new ArrayList<Title>();  
		Cursor c = db.rawQuery("select * from title where type = ?",new String[]{type});   
        while (c.moveToNext()) {  
        	Title title = new Title();  
        	title._id = c.getInt(c.getColumnIndex("_id"));  
        	title.serverId = c.getInt(c.getColumnIndex("servierId"));  
        	title.title = c.getString(c.getColumnIndex("title"));  
        	title.content = c.getString(c.getColumnIndex("content"));  
        	title.type = c.getString(c.getColumnIndex("type"));  
        	title.operateTime = c.getString(c.getColumnIndex("operateTime"));  
        	titles.add(title);  
        }  
        c.close();  
        return titles;  
	}  
      
    /**
     * ����_id���в�ѯ��Ӧ������
     * */
    public Title getInfoById(String _id){
    	Cursor c = db.rawQuery("select * from title where _id = ? ", new String[]{_id});  
    	Title title = new Title();
    	if(c.getCount() > 0){
    		//Ҫ�Ƚ�Cursor�ƶ�����һ�����ܽ��л�ȡ����
    		c.moveToFirst(); 
    		title._id = c.getInt(c.getColumnIndex("_id"));  
        	title.serverId = c.getInt(c.getColumnIndex("servierId"));  
        	title.title = c.getString(c.getColumnIndex("title"));  
        	title.content = c.getString(c.getColumnIndex("content"));  
        	title.type = c.getString(c.getColumnIndex("type"));  
        	title.operateTime = c.getString(c.getColumnIndex("operateTime"));  
    	}
    	return title;
    }
    
	/** 
     * close database 
     */  
    public void closeDB() {  
        db.close();  
    }
}
