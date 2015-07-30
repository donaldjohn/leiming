package com.leiming.search.adapter;

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

import com.leiming.search.R;
import com.leiming.search.bean.Title;

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
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int postions, View converView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (converView == null) {
			holder = new ViewHolder();
			converView = LayoutInflater.from(context).inflate(
					R.layout.list_item, null);
			holder.tv_title = (TextView) converView.findViewById(R.id.lv_title);
			holder.tv_context = (TextView) converView
					.findViewById(R.id.lv_content);
			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}

		String title = data.get(postions).getTitle();
		String content = data.get(postions).getContent();

		if (title != null) {
			SpannableString msp = new SpannableString(title);
			int start = title.indexOf(sreach);
			if (start != -1) {
				msp.setSpan(new BackgroundColorSpan(Color.RED), start, start
						+ sreach.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色
			}
			holder.tv_title.setText(msp);
		} else {
			holder.tv_title.setText("");
		}
		
		if (content != null) {
			SpannableString msp = new SpannableString(content);
			int start = content.indexOf(sreach);
			if (start != -1) {
				msp.setSpan(new BackgroundColorSpan(Color.RED), start, start
						+ sreach.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色
			}
			holder.tv_context.setText(msp);
		} else {
			holder.tv_context.setText("");
		}
		// 设置字体背景色

		return converView;
	}

	class ViewHolder {
		TextView tv_title;
		TextView tv_context;
	}
}
