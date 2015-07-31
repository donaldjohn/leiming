/**
 * 显示证书的界面
 * */
package com.leiming.search;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leiming.search.adapter.GridAdapter;
import com.leiming.search.adapter.ViewPagerAdapter;
import com.leiming.search.adapter.ZhengsGridAdapter;
import com.leiming.search.untill.Container;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Zhengs extends ActionBarActivity implements OnItemClickListener{

	GridView gridview;
	String[] data;
	private ActionBar actionbar;
	ArrayList<View> views;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menus);
		initView();
		initActionbar();
	}

	private void initView() {
		gridview = (GridView) findViewById(R.id.gridView_zs);
		ZhengsGridAdapter adapter  =new  ZhengsGridAdapter(getApplicationContext());
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}

	
	@SuppressLint("ResourceAsColor")
	private void initActionbar(){
		actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title","id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("相关证书");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postions, long arg3) {
		//当gridView点击的时候显示对应点击的图片数据
		createPopu(postions);
	}
	
	private void createPopu(int postions){
		
		PopupWindow window =new PopupWindow();
		View pop =LayoutInflater.from(this).inflate(R.layout.pop, null);
		window.setContentView(pop);
		views = new ArrayList<View>();
		// 初始化引导图片列表
		addView(Container.url, postions);
		// 初始化引导图片列表
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(views,this,postions);
		
		ViewPager vp = (ViewPager) pop.findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		//设置当前显示界面
		vp.setCurrentItem(postions);
		
		window.setOutsideTouchable(true);
		window.setWidth(LayoutParams.MATCH_PARENT);
		window.setHeight(LayoutParams.MATCH_PARENT);
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.update();
		//设置pop显示的位置，根据坐标进行定位的
		window.showAtLocation(gridview, Gravity.CENTER, 0, 0);
	}
	//初始化viewPaper的adapter的数据
	public void addView(String[] url,int postions){
		views.add(getView(url[postions]));
    }
    //根据url进行获取对应的图片
	public View getView(String url){
		View view = LayoutInflater.from(this).inflate(R.layout.pic_new_one, null);
		ImageView image = (ImageView)view.findViewById(R.id.image_res);
		imageLoader.displayImage(url, image, Container.adUrl);
		return view;
	}
}
