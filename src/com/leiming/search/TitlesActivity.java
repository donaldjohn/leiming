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
 * ��ѡ��ܵ�������ʾ����
 * ��ʾĳһ������µ����е�����
 * */
public class TitlesActivity extends ActionBarActivity {

	ListAdapter adapter = null;
	private PullToRefreshListView queryListView;  //��ʾ�������
	//ListView lv; //��ʾ�������
	List<Title> data; //��������ݼ���
	List<Title> data_temp; //��ʱ����ʵ������ݼ���
	EditText et;
	LinearLayout empty; //��listView����Ϊ��ʱ��ʾ��view
	/*
	 * empyt�����textView��ʾ���ı���Ϣ���ڱ���û�����ݵ���ʾ:û���ҵ����ݣ���������ˢ��һ�°�
	 * �����û�в��ҵ�������ô����ʾ��û�ж�Ӧ������
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
		//��listView����һ�������ݵ�ʱ����ʾ�Ľ���
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
		actionbar.setTitle("����");
	}

	private void initEvent(){
		//���þ������Ŀ����¼�
		queryListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//��ȡ��item��Ӧ������
				Title title = data.get(arg2-1);
				Intent intent = new Intent();
				intent.setClass(TitlesActivity.this, DetailActivity.class);
				//����ǰ��������ݵ�ʵ���ഫ�ݹ�ȥ
				intent.putExtra("title", title);
				intent.putExtra("sreach", et.getText().toString());
				startActivity(intent);
			}
		});
		ILoadingLayout startLabels = queryListView.getLoadingLayoutProxy(true, false);  
        startLabels.setPullLabel("������ȡ��������...");// ������ʱ����ʾ����ʾ  
        startLabels.setRefreshingLabel("���ϣ�����ˢ��...");// ˢ��ʱ  
        startLabels.setReleaseLabel("����������Ŷ...");// �����ﵽһ������ʱ����ʾ����ʾ 
        //������������  
        queryListView.setOnRefreshListener(new OnRefreshListener<ListView>() {  
            @Override  
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {  
            	//queryListView.getLoadingLayoutProxy().setPullLabel("������ˢ��!");  
                //ִ��ˢ�º���  
            	//���´ӷ�������ȡ���µ�����
                new GetDataTask(getApplicationContext()).execute();  
            }  
        }); 
		et.addTextChangedListener(textWatcher);
	}
	/**
	 * ���ڴӷ����������ȡ��ǰ���͵����е���Ŀ����
	 * */
	private class GetDataTask extends BaseAsyncTask  
    {  
		
		public GetDataTask(Context context) {
    		super(context);
    	}
        @Override
		protected String doInBackground(String... params) {
        	//���´ӷ�������ȡ��ǰ���͵�����
        	unit typeUnit = Container.current_unit;
        	//��ȡ��ǰ������
        	String type = typeUnit.getValue();
        	return LoadTitlesDataControl.loadDataForType(type,getApplicationContext());
		}  
        
        @Override  
        protected void onPostExecute(String result)  
        {  
        	switch (Integer.parseInt(result)) {
			case 1: //˵����ȡ���ݳɹ���
				//�ӱ��ص���Ŀ���ݱ������»�ȡ���ݣ�Ȼ�����¶�apdate��data��ֵ
	        	TitleDBM tdbm = new TitleDBM(getApplicationContext());
	        	//��ȡ��Ӧ���͵����е���Ŀ����
	        	data_temp = tdbm.getAllTitlesForType(Container.current_unit.getValue());
	        	if( !(data_temp == null || data_temp.size() <= 0) ){
	    			emptyViewText.setText("û�ж�Ӧ������");
	    		}
	        	data.clear();
	        	data.addAll(data_temp);
	        	adapter.notifyDataSetChanged();  
	        	Toast.makeText(getApplicationContext(), "��ȡ���ݳɹ�", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "û�и����͵�����", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(getApplicationContext(), "�������Ͳ���Ϊ��", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
        	super.onPostExecute(result);
        	//�ر�����ˢ��
        	queryListView.onRefreshComplete();  
        }

    }  
	
	
	//edtiTexit����ı��¼�����
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		/*
		 * ���������ݱ仯��ʱ����и���listView�е������Ѵﵽ������Ŀ��
		 * ��ʹ��searcheView+listView�Ĺ��˹���Ҳ��ʵ��������Ч��
		 * */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String change = s.toString();
			
			//����ǣ��ŵ���Ҫ�ǽ��ж����ѯ�ģ����ԣ��žͲ���ִ�в�ѯ�Ĳ���
			//AppUtil.logInfo("change", change.substring(change.length()-1) );
			if( change.length()>0 && !(change.substring(change.length()-1).equals("��")) ){
				data.clear();
				 //�������屳��ɫ 
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
	//��ʼ������
	private void initData() {
		//���ݻ�ȡ��ǰ���Ͷ�Ӧ������
		//data_temp =DBHelp.searchFromSD(Container.current_unit,this);
		TitleDBM tdbm = new TitleDBM(getApplicationContext());
		data_temp = tdbm.getAllTitlesForType(Container.current_unit.getValue());
		if( !(data_temp == null || data_temp.size() <= 0) ){
			emptyViewText.setText("û�ж�Ӧ������");
		}
		data = new ArrayList<Title>();
		data.addAll(data_temp);
		adapter = new ListAdapter(getApplicationContext(), data);
		queryListView.setAdapter(adapter);
	}

}
