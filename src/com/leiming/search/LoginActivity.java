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

import com.leiming.search.animotion.Transaction;
import com.leiming.search.untill.Container;
import com.leiming.search.untill.DBHelp;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

	EditText username, password;
	Button login;
	LinearLayout login_area;
	ImageView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

	private void initUser() {
		Map<String, String[]> map = DBHelp.getUser(this);
		Container.username = map.get("username");
		Container.pwd = map.get("password");
		Container.limit = map.get("limit");
	}

	private void initActionBar() {
		ActionBar actionbar = getSupportActionBar();
		actionbar.hide();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (Container.getstatus(this))
			return;

		switch (view.getId()) {
		case R.id.login:
			if (Container.username == null) {
				Toast.makeText(getApplicationContext(), "用户名为空",
						Toast.LENGTH_LONG).show();
			} else {
				// if (username.getText() != null
				// && username.getText().toString().equals(Container.username)
				// && password.getText() != null
				// && password.getText().toString().equals(Container.pwd)) {
				//
				// }else {
				// Toast.makeText(getApplicationContext(), "用户资料不全",
				// Toast.LENGTH_LONG).show();
				// }

				try {

					if (username.getText() != null
							&& password.getText() != null) {
						for (int count = 0; count < Container.username.length; count++) {
							if (username.getText().toString().equals(
									Container.username[count])) {
								if (password.getText().toString().equals(
										Container.pwd[count])) {
									Container.current_user =count;
									Intent intent = new Intent(
											LoginActivity.this,
											MenuActivity.class);
									startActivity(intent);
								}
							}
						}
						Toast.makeText(getApplicationContext(), "账户密码错误",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "账户设置有问题",
							Toast.LENGTH_LONG).show();
				}
			}
			break;

		default:
			break;
		}

	}
}
