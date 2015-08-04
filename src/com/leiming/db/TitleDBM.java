package com.leiming.db;

import java.util.ArrayList;
import java.util.List;

import com.leiming.bean.Title;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 管理各个类型下的题目的数据库操作类
 * */
public class TitleDBM {
	private DBHelper helper;  
    private SQLiteDatabase db;  
      
    public TitleDBM(Context context) {  
        helper = new DBHelper(context);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        db = helper.getWritableDatabase();  
    }  
      
    /** 
     * add Title 
     */  
    public void add(List<Title> userinfos) {  
        db.beginTransaction();  //开始事务  
        try {  
            for (Title userinfo : userinfos) {  
                db.execSQL("insert into Title values(null, ?, ?)", new Object[]{userinfo.title,userinfo.content});  
            }  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
            db.endTransaction();    //结束事务  
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
     * 根据学号/工号获取用户数据
     * */
    public Title getInfoByUserNum(String user_num){
    	Cursor c = db.rawQuery("select * from Title where user_num = ? ", new String[]{user_num});  
    	Title lu = new Title();
    	if(c.getCount() > 0){
    		//要先将Cursor移动到第一个才能进行获取数据
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
    	//使用SQLiteDatabase执行查询语句
        Cursor c = db.rawQuery("select * from Title", null);  
        return c;  
    }  
      
    /** 
     * close database 
     */  
    public void closeDB() {  
        db.close();  
    }
    //根据类型进行获取对应的所有的题目的数据信息
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
