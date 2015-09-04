
package com.leiming.bean;

import java.io.Serializable;
/**
 * 数据实体，对应每条显示的数据，包括标题，和内容两部分
 * 具体的一道题
 * */
public class Title implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public int _id; //本地数据存储的id值
	public int serverId; //服务器上面的数据的id值
	public String title; //题目的标题
	public String content; //题目的内容也就是答案
	public String type; //题目的类型
	public String operateTime; //服务器上面该题目的操作时间
	
	public Title(){};
	public Title(int _id,int serverId,String title,String content,String type,String operateTime){
		this._id = _id;
		this.serverId = serverId;
		this.title = title;
	}
	public Title(int serverId,String title,String content,String type,String operateTime){
		this.serverId = serverId;
		this.title = title;
		this.content = content;
		this.type = type;
		this.operateTime = operateTime;
	}
	public Title(int serverId,String title,String content,String type){
		this.serverId = serverId;
		this.title = title;
		this.content = content;
		this.type = type;
		this.operateTime = operateTime;
	}

}
