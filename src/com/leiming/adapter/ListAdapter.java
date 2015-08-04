
package com.leiming.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.leiming.bean.Title;
import com.leiming.bean.Title;
import com.leiming.search.R;
import com.leiming.utils.AppUtil;
/**
 * ��ʾ�����listView
 * */
public class ListAdapter extends BaseAdapter {

	Context context;
	List<Title> data;
	String sreach = "-1";

	public String getSreach() {
		return sreach;
	}

	public void setSreach(String sreach) {
		this.sreach = sreach;
	}

	public ListAdapter(Context context, List<Title> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int postions, View converView, ViewGroup arg2) {

		ViewHolder holder;
		if (converView == null) {
			holder = new ViewHolder();
			converView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			holder.tv_title = (TextView) converView.findViewById(R.id.lv_title);
			holder.tv_context = (TextView) converView.findViewById(R.id.lv_content);
			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}
		String title = data.get(postions).title;
		String content = data.get(postions).content;
		//���еĲ�ѯ����
		String sreachSplit[] = sreach.split("��");
		HashMap<String,String> namePinMap = null;
		//���ݲ�ѯ������������ʾ��Ŀ����ʽ���������ʾ��ʽ
		if (title != null ) {
			SpannableString msp = new SpannableString(title);
			if( !TextUtils.isEmpty(sreach) ){
				//��ȡ���е���������ÿ��������Ӧ����title�����״γ��ֵ�λ�ý���������ʾ��ʽ
				for(String sreachs : sreachSplit){
					//�Ƚ����жϵ�ǰ�������Ƿ��ڱ�����
					int start = title.indexOf(sreachs);
					if (start != -1) {
						// ���ñ���ɫΪ��ɫ
						msp.setSpan(new BackgroundColorSpan(Color.RED), start, start + sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
					}
				}
			}
			
			holder.tv_title.setText(msp);
		} else {
			holder.tv_title.setText("");
		}
		//���ô𰸵���ʾ��ʽ
		if (content != null  ) {
			SpannableString msp = new SpannableString(content);
			if( !TextUtils.isEmpty(sreach) ){
				//��ȡ���е���������ÿ��������Ӧ����content�����״γ��ֵ�λ�ý���������ʾ��ʽ
				for(String sreachs : sreachSplit){
					int start = content.indexOf(sreachs);
					if (start != -1) {
						// ���ñ���ɫΪ��ɫ
						msp.setSpan(new BackgroundColorSpan(Color.RED), start, start+ sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
					}
				}
			}
			
			holder.tv_context.setText(msp);
		} else {
			holder.tv_context.setText("");
		}
		return converView;
	}

	class ViewHolder {
		TextView tv_title;
		TextView tv_context;
	}
}
