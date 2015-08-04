
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
	public  String title; //题目的标题
	public String content; //题目的内容也就是答案
	public String type; //题目的类型

}
