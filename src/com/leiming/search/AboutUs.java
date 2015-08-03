package com.leiming.search;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 关于我们菜单的界面
 * */
public class AboutUs extends ActionBarActivity{

	private android.support.v7.app.ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		initActionbar();
	}

	@SuppressLint("ResourceAsColor")
	private void initActionbar(){
		actionbar = getSupportActionBar();
		//actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);  
		//actionbar.setIcon(R.drawable.back);
		//设置actionbar的背景图片
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		//获取并设置actionbar的标题
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("关于雷鸣");
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	    	 //当点击图标的时候退回上一个界面
		     case android.R.id.home:  
		         finish();  
		         return true;  
	     }
		return false;  
	} 



}
