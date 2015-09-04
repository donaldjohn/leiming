
package com.leiming.bean;

import java.io.Serializable;
/**
 * ����ʵ�壬��Ӧÿ����ʾ�����ݣ��������⣬������������
 * �����һ����
 * */
public class Title implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public int _id; //�������ݴ洢��idֵ
	public int serverId; //��������������ݵ�idֵ
	public String title; //��Ŀ�ı���
	public String content; //��Ŀ������Ҳ���Ǵ�
	public String type; //��Ŀ������
	public String operateTime; //�������������Ŀ�Ĳ���ʱ��
	
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
