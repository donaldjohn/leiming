package com.leiming.search;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * �������ǲ˵��Ľ���
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
		//����actionbar�ı���ͼƬ
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		//��ȡ������actionbar�ı���
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("��������");
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	    	 //�����ͼ���ʱ���˻���һ������
		     case android.R.id.home:  
		         finish();  
		         return true;  
	     }
		return false;  
	} 



}
