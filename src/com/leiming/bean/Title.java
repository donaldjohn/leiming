
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
	public  String title; //��Ŀ�ı���
	public String content; //��Ŀ������Ҳ���Ǵ�
	public String type; //��Ŀ������

}
