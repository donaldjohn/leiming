
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
 * ��ʾ֤��Ľ���
 * */
public class Zhengs extends ActionBarActivity implements OnItemClickListener{

	GridView gridview;
	String[] data;
	private ActionBar actionbar;
	ArrayList<View> views;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	//��ʾ֤���pop
	final PopupWindow window =new PopupWindow();
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 100:
					//�ر�pop��ʾ
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
		actionbar.setTitle("���֤��");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postions, long arg3) {
		//��gridView�����ʱ����ʾ��Ӧ�����ͼƬ����
		createPopu(postions);
	}
	
	private void createPopu(int postions){
		
		View pop =LayoutInflater.from(this).inflate(R.layout.pop, null);
		window.setContentView(pop);
		views = new ArrayList<View>();
		// ��ʼ������ͼƬ�б�
		addView(Container.url, postions);
		// ��ʼ������ͼƬ�б�
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(views,this,postions,handler);
		
		ViewPager vp = (ViewPager) pop.findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		//���õ�ǰ��ʾ����
		vp.setCurrentItem(postions);
		
		window.setWidth(LayoutParams.MATCH_PARENT);
		window.setHeight(LayoutParams.MATCH_PARENT);
		window.setBackgroundDrawable(new BitmapDrawable());
		//����pop��ʾ��λ�ã�����������ж�λ��
		window.showAtLocation(gridview, Gravity.CENTER, 0, 0);
		
	}
	//��ʼ��viewPaper��adapter������
	public void addView(String[] url,int postions){
		/**
		 * �����е�֤�����ݶ���ʾ��viewpaper����
		 * */
		for(String urlValue : url){
			views.add(getView(urlValue));
		}
		//views.add(getView(url[postions]));
    }
    //����url���л�ȡ��Ӧ��ͼƬ
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
	
	//���÷��ؼ��Ĺ���
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		//��������Ƿ��ؼ����ж�pop�Ƿ���ʾ����ǣ���pop�رվ����ˣ� ��������������ǰ��activity
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
