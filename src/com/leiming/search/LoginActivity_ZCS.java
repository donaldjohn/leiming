package com.leiming.search;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leiming.animotion.Transaction;
import com.leiming.control.LoginControl;
import com.leiming.utils.AppUtil;
import com.leiming.utils.LogUser;
import com.leiming.widget.CustomProgressDialog;

/**
 * 系统的登录界面，该登录通过访问服务器实现一机一码登录
 * @zcs
 * */
public class LoginActivity_ZCS extends Activity implements OnClickListener{

	private EditText username; //用户帐号
	private TextView register; //注册的功能
	private LinearLayout login_area; //帐号填写区域
	private ImageView logo; //logo图标
	private Button login; //登录
	
	private CustomProgressDialog progressDialog = null;
	//进行登录判断的任务
	private LoginTask loginTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//对logo以及帐号填写区域进行设置动画
		Transaction.transfAnimation(logo, login_area);
	}
	
	private void init(){
		initView();
		initListener();
	}
	//初始化界面控件的监视器
	private void initListener() {
		register.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	//初始化界面的控件
	private void initView() {
		
		username = (EditText) findViewById(R.id.username);
		logo = (ImageView) findViewById(R.id.logo);
		login_area = (LinearLayout) findViewById(R.id.user_area);
		login = (Button) findViewById(R.id.login);
		
		register = (TextView) findViewById(R.id.register);
		register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
		register.getPaint().setAntiAlias(true);//抗锯齿

	}

	@Override
    protected void onDestroy() {
        stopProgressDialog();
        if (loginTask != null && !loginTask.isCancelled()){
        	loginTask.cancel(true);
        }
        super.onDestroy();
    }
	
	//打开进度条
    private void startProgressDialog(){
        if (progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage("登录中...");
        }
        progressDialog.show();
    }
    //关闭进度条
    private void stopProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login: //登录
			String usernameValue = username.getText().toString();
			if( !TextUtils.isEmpty(usernameValue) ){
				String mac = AppUtil.getInstance().getLocalMacAddress();
				LogUser.printI("mac", mac);
				loginTask = new LoginTask();
				loginTask.execute(usernameValue,mac);
			}else{
				Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.register: //注册
			
			break;

		default:
			break;
		}
	}
	
	//用于登录时，使用AsyncTask进行判断用户名和密码以及显示关闭进度条
    private final class LoginTask extends AsyncTask<String, Integer, String>{
    	
    	public LoginTask() {}
		//相当于在子线程中进行调用的过程
		protected String doInBackground(String... params){//子线程中执行的
				//调用service进行返回登录结果
			try {
				return LoginControl.login(params[0],params[1],getApplicationContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
        protected void onCancelled(){
            stopProgressDialog();
            super.onCancelled();
        }
		//任务启动是打开进度条
		@Override
        protected void onPreExecute(){
            startProgressDialog(); //打开进度条
        }
		//任务结束是关闭进度条
		//相当于使用handler,对相应的iamgeView对象指定数据流
		protected void onPostExecute(String result){//运行在主线程
			//关闭进度条显示
			stopProgressDialog();
			//判断登录是否成功
			switch (Integer.parseInt(result)) {
				case 1:  //登录成功
					//启动系统的主界面
					Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
			    	startActivity(intent);
			    	//结束当前activity
					finish();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "用户不存在/没有使用权限",Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			super.onPostExecute(result);
		}	
    }
	
	
}
