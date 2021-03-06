
package com.leiming.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.leiming.adapter.GridAdapter;
import com.leiming.bean.Constants;
import com.leiming.utils.AppUtil;
import com.leiming.utils.Container;
import com.leiming.utils.Container.unit;
/**
 * 主菜单界面
 * @zcs
 * */
public class MenuActivity extends ActionBarActivity {

	GridView gridview;
	String[] data;
	private ActionBar actionbar;
	GridAdapter adapter;
	private String tag; //当前界面要显示什么内容的标识

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		initData();
		initActionbar();
	}
	//加载菜单上面的文字描述
	private void initData() {
		gridview = (GridView) findViewById(R.id.gridView1);
		Intent intent = getIntent();
		tag = intent.getStringExtra("tag");
		if (tag == null) {
			data = new String[] { "关于我们", "计算机二级", "会计证", "统考类", "教师资格证",
					"证书展示" };
			adapter = new GridAdapter(getApplicationContext(), data);
			gridview.setOnItemClickListener(itemclick1);
		} else {
			data = new String[] { "三级心理咨询师", "三级人力资源管理师", "三级秘书资格证", "高级育婴师",
					"公共营养师", "物流师", "企业培训师", "理财规划师" };
			adapter = new GridAdapter(getApplicationContext(), data,0);
			gridview.setOnItemClickListener(itemclick2);
		}
		gridview.setAdapter(adapter);
	}

	@SuppressLint("ResourceAsColor")
	private void initActionbar() {
		actionbar = getSupportActionBar();
		if( tag!=null ){
			actionbar.setDisplayHomeAsUpEnabled(true);  
		}
		//actionbar.setHomeButtonEnabled(true);
		//actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("雷鸣教育");
	}
	
	OnItemClickListener itemclick1 =new OnItemClickListener(){
		// 点击跳转
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int postions,
				long arg3) {
			Intent intent = new Intent();
			//获取当前用户的权限
			String userPermission = AppUtil.getLocalSPF(getApplicationContext()).getString(Constants.USER_PERMISSION, "");
			AppUtil.logInfo("userPersiion", userPermission);
			//根据当前登录的用户从limit中获取对应的权限，然后进行比对，如果是当前点击的功能和权限匹配那么就能够使用对应的功能
			switch (postions) {
				case 4:
					//教师资格证
					if (!userPermission.contains(unit.TEACHER.getValue())) {
						Toast.makeText(getApplicationContext(), "权限不够",Toast.LENGTH_SHORT).show();
					} else {
						intent.setClass(MenuActivity.this, TitlesActivity.class);
						//设置intent要获取数据的类型
						//intent.putExtra("type", "teacher");
						Container.current_unit = unit.TEACHER;
						startActivity(intent);
					}
					break;
				case 1:
					//计算机二级
					if (!userPermission.contains(unit.COMPUTER.getValue())) {
						Toast.makeText(getApplicationContext(), "权限不够",Toast.LENGTH_SHORT).show();
					} else {
						intent.setClass(MenuActivity.this, TitlesActivity.class);
						//intent.putExtra("type", "computer");
						Container.current_unit = unit.COMPUTER;
						startActivity(intent);
					}
					break;
				case 2:
					//会计证
					if (!userPermission.contains(unit.ACCOUNTING.getValue())) {
						Toast.makeText(getApplicationContext(), "权限不够",Toast.LENGTH_SHORT).show();
					} else {
						intent.setClass(MenuActivity.this, TitlesActivity.class);
						//intent.putExtra("type", "accounting");
						Container.current_unit = unit.ACCOUNTING;
						startActivity(intent);
					}
					break;
				case 3:
					//统考类
					if (!userPermission.contains(unit.EXAM.getValue())) {
						Toast.makeText(getApplicationContext(), "权限不够",Toast.LENGTH_SHORT).show();
					} else {
						intent.setClass(MenuActivity.this, MenuActivity.class);
						//设置一个标示，说明当前要显示的是统考类的功能界面
						intent.putExtra("tag", "EXMA");
						//intent.putExtra("type", "exam");
						Container.current_unit = unit.EXAM;
						startActivity(intent);
					}
					break;
				case 0:
					//关于我们
					intent.setClass(MenuActivity.this, AboutUs.class);
					startActivity(intent);
					break;
				case 5:
					//证书展示
					intent.setClass(MenuActivity.this, Zhengs.class);
					startActivity(intent);
					break;
				default:
					Toast.makeText(getApplicationContext(), "权限不够", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	//统考类下所有功能对应的点击事件
	OnItemClickListener itemclick2 =new OnItemClickListener() {
		// 点击跳转
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int postions,
				long arg3) {
			//获取当前的点击的功能对应要求的权限值
			String limit =unit.EXAM.getEXAMlimit(postions);
			//获取当前用户的权限
			String userPermission = AppUtil.getLocalSPF(getApplicationContext()).getString(Constants.USER_PERMISSION, "");
			Intent intent = new Intent();
				if (!userPermission.contains(limit)) {
					Toast.makeText(getApplicationContext(), "权限不够",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, TitlesActivity.class);
					Container.current_unit = unit.EXAM.postionToEnum(postions);
					startActivity(intent);
				}
		}
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	
}
