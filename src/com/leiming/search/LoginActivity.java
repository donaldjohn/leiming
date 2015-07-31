package com.leiming.search;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leiming.animotion.Transaction;
import com.leiming.utils.Container;
import com.leiming.utils.DBHelp;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

	EditText username, password;
	Button login;
	LinearLayout login_area;
	ImageView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		DBHelp.intFile("/LeiM");
		DBHelp.intFile("/LeiM/class");
		initUser();
		initActionBar();
		initView();
		initClicke();
		Transaction.transfAnimation(logo, login_area);
	}
	//获取当前登录文件中存储的用户
	private void initUser() {
		//进行读取asset文件夹下面的login文件，获取所有的用户账号，密码以及权限
		Map<String, String[]> map = DBHelp.getUser(this);
		Container.username = map.get("username");
		Container.pwd = map.get("password");
		Container.limit = map.get("limit");
	}
	
	private void initActionBar() {
		ActionBar actionbar = getSupportActionBar();
		actionbar.hide();
	}

	private void initView() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		logo = (ImageView) findViewById(R.id.logo);
		login_area = (LinearLayout) findViewById(R.id.user_area);
		login = (Button) findViewById(R.id.login);
		login_area.setVisibility(View.GONE);
		Container.saveDate(this);
	}

	private void initClicke() {
		login.setOnClickListener(this);
	}
	//登录判断
	@Override
	public void onClick(View view) {
		if (Container.getstatus(this))
			return;
		switch (view.getId()) {
		case R.id.login:
			if (Container.username == null) {
				Toast.makeText(getApplicationContext(), "用户名为空",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					if (username.getText() != null&& password.getText() != null) {
						//遍历所有的用户进行判断是否通过验证登录
						for (int count = 0; count < Container.username.length; count++) {
							if (username.getText().toString().equals(Container.username[count])) {
								if (password.getText().toString().equals(Container.pwd[count])) {
									Container.current_user =count;
									Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
									startActivity(intent);
								}
							}
						}
						Toast.makeText(getApplicationContext(), "账户密码错误",Toast.LENGTH_LONG).show();
					}
				} catch (Exception e){
					Toast.makeText(getApplicationContext(), "账户设置有问题",Toast.LENGTH_LONG).show();
				}
			}
			break;
		default:
			break;
		}

	}
}
