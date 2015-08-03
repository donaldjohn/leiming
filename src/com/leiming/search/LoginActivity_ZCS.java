package com.leiming.search;

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
import com.leiming.control.UserControl;
import com.leiming.utils.AppUtil;
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
	private UserTask userTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_zcs);
		init();
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
        if (userTask != null && !userTask.isCancelled()){
        	userTask.cancel(true);
        }
        super.onDestroy();
    }
	
	//打开进度条
    private void startProgressDialog(String msg){
        if (progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(msg);
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
			String mac = AppUtil.getLocalMacAddress(getApplicationContext());
			if( !TextUtils.isEmpty(usernameValue) ){
				userTask = new UserTask();
				if( login.getText().equals("登陆") ){
					//如果是登陆则进入登陆的判断
					//LogUser.printI("mac", mac);
					userTask.execute("login",usernameValue,mac);
				}else{
					//否则进入注册的判断
					userTask.execute("register",usernameValue,mac);
				}
			}else{
				Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.register: //注册
			//修改登录按钮的显示文本为注册，点击的时候判断文本是登录还是注册
			login.setText("注册");
			break;

		default:
			break;
		}
	}
	
	//用于登录时，使用AsyncTask进行判断用户名和密码以及显示关闭进度条
	//同时注册的判断也是走这里的
    private final class UserTask extends AsyncTask<String, Integer, String>{
    	
    	//记录当前是登录还是注册的状态值
    	private String state = null;
    	public UserTask() {}
		//相当于在子线程中进行调用的过程
		protected String doInBackground(String... params){//子线程中执行的
				//调用service进行返回登录结果
			state = params[0];
			try {
				if( "login".equals(state) ){
					//如果是登录就使用登陆的判断
					return UserControl.login(params[1],params[2],LoginActivity_ZCS.this);
				}else{
					//否则进入注册的判断
					return UserControl.register(params[1],params[2],LoginActivity_ZCS.this);
				}
				
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
        	  startProgressDialog("加载中..."); //打开进度条
        }
		//任务结束是关闭进度条
		//相当于使用handler,对相应的iamgeView对象指定数据流
		protected void onPostExecute(String result){//运行在主线程
			//关闭进度条显示
			stopProgressDialog();
			if("login".equals(state)){
				//如果是登录进行登录的处理
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
			}else{
				login.setText("登陆");
				//否则进入注册的处理
				switch (Integer.parseInt(result)) {
					
					case 1:  //注册成功
						Toast.makeText(getApplicationContext(), "注册成功",Toast.LENGTH_LONG).show();
						break;
					case 2:
						Toast.makeText(getApplicationContext(), "您不具备注册的资格，请联系雷鸣",Toast.LENGTH_LONG).show();
						break;
					default:
						break;
				}
			}
			super.onPostExecute(result);
		}	
    }
	
	
}
