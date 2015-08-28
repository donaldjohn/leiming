
package com.leiming.search;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leiming.adapter.GridAdapter;
import com.leiming.adapter.ViewPagerAdapter;
import com.leiming.adapter.ZhengsGridAdapter;
import com.leiming.utils.Container;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 显示证书的界面
 * */
public class Zhengs extends ActionBarActivity implements OnItemClickListener{

	GridView gridview;
	String[] data;
	private ActionBar actionbar;
	ArrayList<View> views;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	//显示证书的pop
	final PopupWindow window =new PopupWindow();
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 100:
					//关闭pop显示
					window.dismiss();
					break;
				default:
					break;
			}
		}
		
	};

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
		actionbar.setDisplayHomeAsUpEnabled(true);  
		//actionbar.setHomeButtonEnabled(true);
		//actionbar.setIcon(R.drawable.back);
		actionbar.setDisplayHomeAsUpEnabled(true);  
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
		
		View pop =LayoutInflater.from(this).inflate(R.layout.pop, null);
		window.setContentView(pop);
		views = new ArrayList<View>();
		// 初始化引导图片列表
		addView(Container.url, postions);
		// 初始化引导图片列表
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(views,this,postions,handler);
		
		ViewPager vp = (ViewPager) pop.findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		//设置当前显示界面
		vp.setCurrentItem(postions);
		
		window.setWidth(LayoutParams.MATCH_PARENT);
		window.setHeight(LayoutParams.MATCH_PARENT);
		window.setBackgroundDrawable(new BitmapDrawable());
		//设置pop显示的位置，根据坐标进行定位的
		window.showAtLocation(gridview, Gravity.CENTER, 0, 0);
		
	}
	//初始化viewPaper的adapter的数据
	public void addView(String[] url,int postions){
		/**
		 * 将所有的证书数据都显示在viewpaper上面
		 * */
		for(String urlValue : url){
			views.add(getView(urlValue));
		}
		//views.add(getView(url[postions]));
    }
    //根据url进行获取对应的图片
	public View getView(String url){
		View view = LayoutInflater.from(this).inflate(R.layout.pic_new_one, null);
		ImageView image = (ImageView)view.findViewById(R.id.image_res);
		imageLoader.displayImage(url, image, Container.adUrl);
		return view;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()){  
		     case android.R.id.home:  
		         finish();  
		         return true;  
	     }
		return false;  
	} 
	
	//设置返回键的功能
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		//如果按的是返回键，判断pop是否显示如果是，则将pop关闭就行了， 如果不是则结束当前的activity
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	if( window.isShowing() ){
        		window.dismiss();
        	}else{
        		finish();
        	}
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	
}
