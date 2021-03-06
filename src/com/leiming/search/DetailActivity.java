package com.leiming.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.leiming.bean.Title;

/**
 * 题目详情界面
 * */
public class DetailActivity extends ActionBarActivity{

	TextView tv_sreach,tv_title,tv_content;
	private ActionBar actionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		initView();
		getData();
		initActionbar();
	}
	
	private void initView(){
		tv_sreach =(TextView) findViewById(R.id.select);
		tv_title =(TextView) findViewById(R.id.title);
		tv_content =(TextView) findViewById(R.id.content);
	}
	
	private void getData(){
		Intent intent =getIntent();
		if(intent!=null){
			//获取传递过来的数据
			Title title =(Title) intent.getSerializableExtra("title");
			String sreach =intent.getStringExtra("sreach");
			tv_sreach.setText(sreach!=null?sreach:"");
			//所有的查询条件
			String sreachSplit[] = sreach.split("，");
			if(title!=null){
				String t =title.title;
				String content =title.content;
				if(t!=null){
					SpannableString msp = new SpannableString(t);
					//判断当前的数据中是否有查询的数据字段，如果有更改样式
					for(String sreachs : sreachSplit){
						int start = t.indexOf(sreachs);
						if (start != -1) {
							// 设置背景色为青色
							msp.setSpan(new BackgroundColorSpan(Color.RED), start, start + sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
						}
					}
					tv_title.setText(msp);
				}else {
					tv_title.setText("");
				}
				if(content!=null){
					SpannableString msp = new SpannableString(content);
					//判断当前的数据中是否有查询的数据字段，如果有更改样式
					for(String sreachs : sreachSplit){
						int start=content.indexOf(sreachs);
						if(start!=-1){
							msp.setSpan(new BackgroundColorSpan(Color.RED), start, start+sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色 
						}
					}
					
					tv_content.setText(msp);
				}else {
					tv_content.setText("");
				}
				
			}
			
		
		}
	}

	@SuppressLint("ResourceAsColor")
	private void initActionbar(){
		actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);  
		//actionbar.setHomeButtonEnabled(true);
		//actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("搜索结果");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}
}
