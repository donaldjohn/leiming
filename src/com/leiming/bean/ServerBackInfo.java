package com.leiming.bean;

import java.io.InputStream;


/**
 * 该类专门用于进行服务器返回的类，也就是对服务器返回的数据进行包装的类
 * @author zcs
 * */
public class ServerBackInfo {

	public final static String TYPE_STRING = "type_string"; //string类型的数据
	public final static String TYPE_INPUTSTREAM = "type_inoutstream";  //inputstream类型的数据
	/**
	 * 记录访问服务器返回的状态值,如果请求成功200，服务器不可到达500
	 * */
	public String State = "200"; //服务器返回的状态值，默认200正常
	public InputStream outIs = null; //服务器返回的输入流，默认null
	/**
	 * 服务器返回的string，默认null
	 * 对于服务器返回的string是json格式的其中的格式：
	 * {state:;data:[{},{}]} ,其中state为服务器相应的返回吗，data为服务器相应的数据
	 * 在使用的时候应该先判断state是否为正确的状态，在去获取data请求的数据
	 * */
	public String outStringContent = null; 
	public String ServerBackType = TYPE_STRING; // 服务器返回的数据的类型,默认是string类型的返回数据
	
	public ServerBackInfo(){};
	
	public ServerBackInfo(String state,InputStream outIs,String outStirngContent,String sbt){
		this.State = state;
		this.outIs = outIs;
		this.outStringContent = outStirngContent;
		this.ServerBackType =sbt;
	}
	
}
