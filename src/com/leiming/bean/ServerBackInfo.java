package com.leiming.bean;

import java.io.InputStream;


/**
 * ����ר�����ڽ��з��������ص��࣬Ҳ���ǶԷ��������ص����ݽ��а�װ����
 * @author zcs
 * */
public class ServerBackInfo {

	public final static String TYPE_STRING = "type_string"; //string���͵�����
	public final static String TYPE_INPUTSTREAM = "type_inoutstream";  //inputstream���͵�����
	/**
	 * ��¼���ʷ��������ص�״ֵ̬,�������ɹ�200�����������ɵ���500
	 * */
	public String State = "200"; //���������ص�״ֵ̬��Ĭ��200����
	public InputStream outIs = null; //���������ص���������Ĭ��null
	/**
	 * ���������ص�string��Ĭ��null
	 * ���ڷ��������ص�string��json��ʽ�����еĸ�ʽ��
	 * {state:;data:[{},{}]} ,����stateΪ��������Ӧ�ķ�����dataΪ��������Ӧ������
	 * ��ʹ�õ�ʱ��Ӧ�����ж�state�Ƿ�Ϊ��ȷ��״̬����ȥ��ȡdata���������
	 * */
	public String outStringContent = null; 
	public String ServerBackType = TYPE_STRING; // ���������ص����ݵ�����,Ĭ����string���͵ķ�������
	
	public ServerBackInfo(){};
	
	public ServerBackInfo(String state,InputStream outIs,String outStirngContent,String sbt){
		this.State = state;
		this.outIs = outIs;
		this.outStringContent = outStirngContent;
		this.ServerBackType =sbt;
	}
	
}
