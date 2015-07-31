package com.leiming.widget;

import com.leiming.search.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 该类是用于自定义一个园型进度条的类
 * @author zcs
 * */
public class CustomProgressDialog extends Dialog{
	
	private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
     
    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
    }
     
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
     
    public static CustomProgressDialog createDialog(Context context){
    	//设置该进度条的样式
        customProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
        //设置进度条的显示界面
        customProgressDialog.setContentView(R.layout.customprogressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        //让其他区域被遮盖
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }
    public void onWindowFocusChanged(boolean hasFocus){
         
        if (customProgressDialog == null){
            return;
        }
        //获取进度条界面中显示动画的imageView
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        //获取动画并启动
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
  
    /**
     * @param strMessage 提示内容
     */
    public CustomProgressDialog setMessage(String strMessage){
    	//获取要显示提示内容的组件
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }
    
    /**
     * 打开Dialog
     * */
    public static void startProgressDialog(CustomProgressDialog cpd){
        if (cpd == null){
        	cpd.setMessage("登录中...");
        }
        cpd.show();
    }
    
    /**
     * 关闭Dialog
     * */
    public static void closeProgressDialog(CustomProgressDialog cpd){
    	 if (cpd != null){
    		 cpd.dismiss();
    		 cpd = null;
         }
    }
}
