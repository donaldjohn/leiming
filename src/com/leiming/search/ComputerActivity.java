package com.leiming.search;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.leiming.adapter.ListAdapter;
import com.leiming.bean.Title;
import com.leiming.utils.Container;
import com.leiming.utils.DBHelp;

public class ComputerActivity extends ActionBarActivity {

	ListAdapter adapter = null;
	ListView lv; //显示搜索结果
	List<Title> data; //试题的数据集合
	List<Title> data_temp; //临时保存实体的数据集合
	EditText et;
	LinearLayout empty;
	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initActionbar();
		initData();
		initEvent();
	}

	private void initView() {
		empty = (LinearLayout) findViewById(R.id.empty_view);
		lv = (ListView) findViewById(R.id.search_result);
		et = (EditText) findViewById(R.id.search);
		lv.setEmptyView(empty);
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
		actionbar.setTitle("搜索");
	}

	private void initEvent(){
		//设置具体的条目点击事件
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Title title = data.get(arg2);
				Intent intent = new Intent();
				intent.setClass(ComputerActivity.this, DetailActivity.class);
				//将当前点击的数据的实体类传递过去
				intent.putExtra("title", title);
				intent.putExtra("sreach", et.getText().toString());
				startActivity(intent);
			}
		});
		et.addTextChangedListener(textWatcher);
	}
	//edtiTexit输入改变事件监听
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			Log.d("TAG", "afterTextChanged--------------->");
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			Log.d("TAG", "beforeTextChanged--------------->");
		}
		/*
		 * 当输入内容变化的时候进行更新listView中的数据已达到搜索的目的
		 * 当使用searcheView+listView的过滤功能也能实现这样的效果
		 * */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			Log.d("TAG", "onTextChanged--------------->");
			String change = s.toString();
		    //设置字体背景色 
			data.clear();
			if(change.length()>0){
				data.addAll(DBHelp.sreachHelper(change, data_temp));
			}else {
				data.addAll(data_temp);
			}
			adapter.setSreach(change);
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	private void initData() {
		data_temp =DBHelp.searchFromSD(Container.current_unit,this);
		data = new ArrayList<Title>();
		data.addAll(data_temp);
		adapter = new ListAdapter(getApplicationContext(), data);
		lv.setAdapter(adapter);
	}

}
