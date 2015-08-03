
package com.leiming.bean;

import java.io.Serializable;
/**
 * 数据实体，对应每条显示的数据，包括标题，和内容两部分
 * 具体的一道题
 * */
public class Title implements Serializable{
	private static final long serialVersionUID = 1L;
	private  String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
