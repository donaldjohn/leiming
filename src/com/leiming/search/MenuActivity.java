/**
 * ���˵�����
 * @zcs
 * */
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
import com.leiming.utils.Container;
import com.leiming.utils.Container.unit;

public class MenuActivity extends ActionBarActivity {

	GridView gridview;
	String[] data;
	private ActionBar actionbar;
	GridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		initData();
		initActionbar();
	}
	//���ز˵��������������
	private void initData() {
		gridview = (GridView) findViewById(R.id.gridView1);
		Intent intent = getIntent();
		String tag = intent.getStringExtra("tag");
		if (tag == null) {
			data = new String[] { "��������", "���������", "���֤", "ͳ����", "��ʦ�ʸ�֤",
					"֤��չʾ" };
			adapter = new GridAdapter(getApplicationContext(), data);
			gridview.setOnItemClickListener(itemclick1);
		} else {
			data = new String[] { "����������ѯʦ", "����������Դ����ʦ", "���������ʸ�֤", "�߼���Ӥʦ",
					"����Ӫ��ʦ", "����ʦ", "��ҵ��ѵʦ", "��ƹ滮ʦ" };
			adapter = new GridAdapter(getApplicationContext(), data,0);
			gridview.setOnItemClickListener(itemclick2);
		}
		gridview.setAdapter(adapter);
	}

	@SuppressLint("ResourceAsColor")
	private void initActionbar() {
		actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("��������");
	}
	
	OnItemClickListener itemclick1 =new OnItemClickListener(){
		// �����ת
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int postions,
				long arg3) {
			Intent intent = new Intent();
			//���ݵ�ǰ��¼���û���limit�л�ȡ��Ӧ��Ȩ�ޣ�Ȼ����бȶԣ�����ǵ�ǰ����Ĺ��ܺ�Ȩ��ƥ����ô���ܹ�ʹ�ö�Ӧ�Ĺ���
			switch (postions) {
			case 4:
				if (!Container.limit[Container.current_user].contains(unit.TEACHER.getValue())) {
					Toast.makeText(getApplicationContext(), "Ȩ�޲���",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, ComputerActivity.class);
					Container.current_unit = unit.TEACHER;
					startActivity(intent);
				}
				break;
			case 1:
				if (!Container.limit[Container.current_user].contains(unit.COMPUTER.getValue())) {
					Toast.makeText(getApplicationContext(), "Ȩ�޲���",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, ComputerActivity.class);
					Container.current_unit = unit.COMPUTER;
					startActivity(intent);
				}
				break;
			case 2:
				if (!Container.limit[Container.current_user].contains(unit.ACCOUNTING.getValue())) {
					Toast.makeText(getApplicationContext(), "Ȩ�޲���",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, ComputerActivity.class);
					Container.current_unit = unit.ACCOUNTING;
					startActivity(intent);
				}
				break;
			case 3:
				if (!Container.limit[Container.current_user].contains(unit.EXAM.getValue())) {
					Toast.makeText(getApplicationContext(), "Ȩ�޲���",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, MenuActivity.class);
					intent.putExtra("tag", "EXMA");
					Container.current_unit = unit.EXAM;
					startActivity(intent);
				}
				break;
			case 0:
				intent.setClass(MenuActivity.this, AboutUs.class);
				Container.current_unit = unit.COMPUTER;
				startActivity(intent);
				break;
			case 5:
				intent.setClass(MenuActivity.this, Zhengs.class);
				Container.current_unit = unit.COMPUTER;
				startActivity(intent);
				break;
			default:
				Toast.makeText(getApplicationContext(), "Ȩ�޲���", Toast.LENGTH_LONG).show();
				break;
			}

		}
		
	};
	//ͳ���������й��ܶ�Ӧ�ĵ���¼�
	OnItemClickListener itemclick2 =new OnItemClickListener() {
		// �����ת
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int postions,
				long arg3) {
			//��ȡ��ǰ�ĵ���Ĺ��ܶ�ӦҪ���Ȩ��ֵ
			String limit =unit.EXAM.getEXAMlimit(postions);
			Intent intent = new Intent();
				if (!Container.limit[Container.current_user].contains(limit)) {
					Toast.makeText(getApplicationContext(), "Ȩ�޲���",Toast.LENGTH_LONG).show();
				} else {
					intent.setClass(MenuActivity.this, ComputerActivity.class);
					Container.current_unit = unit.TEACHER;
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
