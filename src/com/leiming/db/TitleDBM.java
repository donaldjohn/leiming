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
      
    public void add(List<Title> titleValues) {  
        db.beginTransaction();  //开始事务  
        try {  
            for (Title titleValue : titleValues) {  
                db.execSQL("insert into title(null,?,?,?,?,?)", 
            		new Object[]{titleValue.serverId,titleValue.title,titleValue.content,titleValue.type,titleValue.operateTime});  
            }  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
            db.endTransaction();//结束事务  
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
    //根据title删除
    public void delete(Title titleValue) {  
        db.execSQL("delete from title where _id = ?", new Object[]{titleValue._id});  
    }  
    //根据对应的类型将数据全部删除
    public void delete(String type){
    	db.execSQL("delete from title where type = ?", new Object[]{type}); 
    }
      
    /** 
     * 获取所有的题目数据
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
     * 根据类型进行获取对应的所有的题目的数据信息
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
     * 根据_id进行查询对应的数据
     * */
    public Title getInfoById(String _id){
    	Cursor c = db.rawQuery("select * from title where _id = ? ", new String[]{_id});  
    	Title title = new Title();
    	if(c.getCount() > 0){
    		//要先将Cursor移动到第一个才能进行获取数据
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
