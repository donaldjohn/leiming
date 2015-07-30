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

import com.leiming.search.adapter.ListAdapter;
import com.leiming.search.bean.Title;
import com.leiming.search.untill.Container;
import com.leiming.search.untill.DBHelp;

public class ComputerActivity extends ActionBarActivity {

	ListAdapter adapter = null;
	ListView lv;
	List<Title> data;
	List<Title> data_temp;
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
		// ViewGroup parentView = (ViewGroup) lv.getParent();
		// parentView.addView(empty, 2); // 你需要在这儿设置正确的位置，以达到你需要的效果。
		// lv.setEmptyView(empty);
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

	private void initEvent() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Title title = data.get(arg2);
				Intent intent = new Intent();
				intent.setClass(ComputerActivity.this, DetailActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("sreach", et.getText().toString());
				startActivity(intent);
			}

		});

		et.addTextChangedListener(textWatcher);
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			Log.d("TAG", "afterTextChanged--------------->");
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			Log.d("TAG", "beforeTextChanged--------------->");
		}

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
