
package com.leiming.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
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
/**
 * 显示试题的listView
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
		String title = data.get(postions).getTitle();
		String content = data.get(postions).getContent();
		//所有的查询条件
		String sreachSplit[] = sreach.split("，");
		//根据查询的数据设置显示条目的样式
		if (title != null) {
			SpannableString msp = new SpannableString(title);
			//获取所有的条件，对每个条件对应的在title标题首次出现的位置进行设置显示样式
			for(String sreachs : sreachSplit){
				int start = title.indexOf(sreachs);
				if (start != -1) {
					// 设置背景色为青色
					msp.setSpan(new BackgroundColorSpan(Color.RED), start, start + sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
				}
			}
			holder.tv_title.setText(msp);
		} else {
			holder.tv_title.setText("");
		}
		
		if (content != null) {
			SpannableString msp = new SpannableString(content);
			//获取所有的条件，对每个条件对应的在content内容首次出现的位置进行设置显示样式
			for(String sreachs : sreachSplit){
				int start = content.indexOf(sreachs);
				if (start != -1) {
					// 设置背景色为青色
					msp.setSpan(new BackgroundColorSpan(Color.RED), start, start+ sreachs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
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
