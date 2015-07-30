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

import com.leiming.search.bean.Title;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends ActionBarActivity{

	TextView tv_sreach,tv_title,tv_content;
	private ActionBar actionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
			Title title =(Title) intent.getSerializableExtra("title");
			String sreach =intent.getStringExtra("sreach");
			tv_sreach.setText(sreach!=null?sreach:"");
			if(title!=null){
				String t =title.getTitle();
				String content =title.getContent();
				if(t!=null){
					SpannableString msp = new SpannableString(t);
					int start=t.indexOf(sreach);
					if(start !=-1){
						msp.setSpan(new BackgroundColorSpan(Color.RED), start, start+sreach.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色 
					}
					tv_title.setText(msp);
				}else {
					tv_title.setText("");
				}
				if(content!=null){
					SpannableString msp = new SpannableString(content);
					int start=content.indexOf(sreach);
					if(start!=-1){
						msp.setSpan(new BackgroundColorSpan(Color.RED), start, start+sreach.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色 
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
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
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
		// TODO Auto-generated method stub
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
