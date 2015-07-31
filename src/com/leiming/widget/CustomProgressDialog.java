package com.leiming.widget;

import com.leiming.search.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �����������Զ���һ��԰�ͽ���������
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
    	//���øý���������ʽ
        customProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
        //���ý���������ʾ����
        customProgressDialog.setContentView(R.layout.customprogressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        //�����������ڸ�
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }
    public void onWindowFocusChanged(boolean hasFocus){
         
        if (customProgressDialog == null){
            return;
        }
        //��ȡ��������������ʾ������imageView
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        //��ȡ����������
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
  
    /**
     * @param strMessage ��ʾ����
     */
    public CustomProgressDialog setMessage(String strMessage){
    	//��ȡҪ��ʾ��ʾ���ݵ����
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }
    
    /**
     * ��Dialog
     * */
    public static void startProgressDialog(CustomProgressDialog cpd){
        if (cpd == null){
        	cpd.setMessage("��¼��...");
        }
        cpd.show();
    }
    
    /**
     * �ر�Dialog
     * */
    public static void closeProgressDialog(CustomProgressDialog cpd){
    	 if (cpd != null){
    		 cpd.dismiss();
    		 cpd = null;
         }
    }
}
