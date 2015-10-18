package com.leiming.search;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leiming.adapter.ListAdapter;
import com.leiming.asynctask.BaseAsyncTask;
import com.leiming.bean.Title;
import com.leiming.control.LoadTitlesDataControl;
import com.leiming.db.TitleDBM;
import com.leiming.utils.Container;
import com.leiming.utils.Container.unit;
/**
 * 各选项功能的数据显示界面
 * 显示某一个类别下的所有的试题
 * */
public class TitlesActivity extends ActionBarActivity {

	ListAdapter adapter = null;
	private PullToRefreshListView queryListView;  //显示搜索结果
	//ListView lv; //显示搜索结果
	List<Title> data; //试题的数据集合
	List<Title> data_temp; //临时保存实体的数据集合
	EditText et;
	LinearLayout empty; //当listView数据为空时显示的view
	/*
	 * empyt里面的textView显示的文本信息，在本地没有数据的显示:没有找到数据，尝试下拉刷新一下吧
	 * 如果是没有查找到数据那么就显示：没有对应的数据
	 * */
	TextView emptyViewText; 
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
		emptyViewText = (TextView) findViewById(R.id.emptyText);
		queryListView = (PullToRefreshListView) findViewById(R.id.search_result);
		et = (EditText) findViewById(R.id.search);
		//对listView设置一个空数据的时候显示的界面
		queryListView.setEmptyView(empty);
	}

	@SuppressLint("ResourceAsColor")
	private void initActionbar(){
		actionbar = getSupportActionBar();
		//actionbar.setHomeButtonEnabled(true);
		//actionbar.setIcon(R.drawable.back);
		actionbar.setDisplayHomeAsUpEnabled(true);  
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
		queryListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//获取当item对应的数据
				Title title = data.get(arg2-1);
				Intent intent = new Intent();
				intent.setClass(TitlesActivity.this, DetailActivity.class);
				//将当前点击的数据的实体类传递过去
				intent.putExtra("title", title);
				intent.putExtra("sreach", et.getText().toString());
				startActivity(intent);
			}
		});
		ILoadingLayout startLabels = queryListView.getLoadingLayoutProxy(true, false);  
        startLabels.setPullLabel("下拉获取最新数据...");// 刚下拉时，显示的提示  
        startLabels.setRefreshingLabel("好嘞，正在刷新...");// 刷新时  
        startLabels.setReleaseLabel("会消耗流量哦...");// 下来达到一定距离时，显示的提示 
        //上拉监听函数  
        queryListView.setOnRefreshListener(new OnRefreshListener<ListView>() {  
            @Override  
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {  
            	//queryListView.getLoadingLayoutProxy().setPullLabel("下拉可刷新!");  
                //执行刷新函数  
            	//重新从服务器获取最新的数据
                new GetDataTask(getApplicationContext()).execute();  
            }  
        }); 
		et.addTextChangedListener(textWatcher);
	}
	/**
	 * 用于从服务器上面获取当前类型的所有的题目数据
	 * */
	private class GetDataTask extends BaseAsyncTask  
    {  
		
		public GetDataTask(Context context) {
    		super(context);
    	}
        @Override
		protected String doInBackground(String... params) {
        	//重新从服务器获取当前类型的数据
        	unit typeUnit = Container.current_unit;
        	//获取当前的类型
        	String type = typeUnit.getValue();
        	return LoadTitlesDataControl.loadDataForType(type,getApplicationContext());
		}  
        
        @Override  
        protected void onPostExecute(String result)  
        {  
        	switch (Integer.parseInt(result)) {
			case 1: //说明获取数据成功了
				//从本地的题目数据表中重新获取数据，然后重新对apdate的data赋值
	        	TitleDBM tdbm = new TitleDBM(getApplicationContext());
	        	//获取对应类型的所有的题目数据
	        	data_temp = tdbm.getAllTitlesForType(Container.current_unit.getValue());
	        	if( !(data_temp == null || data_temp.size() <= 0) ){
	    			emptyViewText.setText("没有对应的数据");
	    		}
	        	data.clear();
	        	data.addAll(data_temp);
	        	adapter.notifyDataSetChanged();  
	        	Toast.makeText(getApplicationContext(), "获取数据成功", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "没有该类型的数据", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(getApplicationContext(), "请求类型不能为空", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
        	super.onPostExecute(result);
        	//关闭下拉刷新
        	queryListView.onRefreshComplete();  
        }

    }  
	
	
	//edtiTexit输入改变事件监听
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		/*
		 * 当输入内容变化的时候进行更新listView中的数据已达到搜索的目的
		 * 当使用searcheView+listView的过滤功能也能实现这样的效果
		 * */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String change = s.toString();
			
			//如果是，号的是要是进行多项查询的，所以，号就不用执行查询的操作
			//AppUtil.logInfo("change", change.substring(change.length()-1) );
			if( change.length()>0 && !(change.substring(change.length()-1).equals("，")) ){
				data.clear();
				 //设置字体背景色 
				data.addAll(LoadTitlesDataControl.getTitlesForCondition(change, data_temp));
			}else if( change.length()<= 0 ){
				data.clear();
				data.addAll(data_temp);
			}
			adapter.setSreach(change);
			adapter.notifyDataSetChanged();
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
	//初始化数据
	private void initData() {
		//根据获取当前类型对应的数据
		//data_temp =DBHelp.searchFromSD(Container.current_unit,this);
		TitleDBM tdbm = new TitleDBM(getApplicationContext());
		data_temp = tdbm.getAllTitlesForType(Container.current_unit.getValue());
		if( !(data_temp == null || data_temp.size() <= 0) ){
			emptyViewText.setText("没有对应的数据");
		}
		data = new ArrayList<Title>();
		data.addAll(data_temp);
		adapter = new ListAdapter(getApplicationContext(), data);
		queryListView.setAdapter(adapter);
	}

}
