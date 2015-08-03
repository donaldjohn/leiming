package com.leiming.asynctask;

import com.leiming.utils.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * AsyncTask��һ���̳��࣬���ｫAsyncTask���õ��ж��߼��������ã�Ȼ��
 * ������AsyncTask���������
 * */
public abstract class BaseAsyncTask extends AsyncTask<String,Integer,String>{
	
	private Context context;
	
	public BaseAsyncTask(){}
	
	public BaseAsyncTask(Context context) {
		this.context = context;
	}
	//�������onPostExecute�������������״̬���ж����õ��������
	//�ټ̳и���������Ͳ���д��
	protected void onPostExecute(String result){//���������߳�
		//�ж��Ƿ���������쳣������һ���������ڽ����������쳣������ж�Ӧ�Ĵ���
		//���ڷ������������ݵ��жϾͽ����ϲ���
		switch (Integer.parseInt(result)) {
			case HttpUtil.SERVERTIMEOUT:
				//����ʱ��
				Toast.makeText(context, "�������ӳ�ʱ������ϵ����Ա��",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.REQUESTTIMEOUT:
				//����ʱ��
				Toast.makeText(context, "��������ʱ��",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.NETPARSEERROR:
				//����ʱ��
				Toast.makeText(context, "��������쳣��",Toast.LENGTH_SHORT).show();
				break;
			case HttpUtil.NONETWORKS:
				//����ʱ��
				Toast.makeText(context, "����δ���ӣ�",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
	}	
	
}