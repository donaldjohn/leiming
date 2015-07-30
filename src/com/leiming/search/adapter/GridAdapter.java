package com.leiming.search.adapter;

import com.leiming.search.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	Context context;
	String[] titles;
	int[] img;

	public GridAdapter(Context context, String[] titles) {
		this.context = context;
		this.titles = titles;
		img = new int[] { R.drawable.customer_service, R.drawable.computer,
				R.drawable.invoice, R.drawable.student_id, R.drawable.teachers,
				R.drawable.address_book };
	}

	public GridAdapter(Context context, String[] titles, int i) {
		this.context = context;
		this.titles = titles;
		// data = new String[] {"关于我们","计算机二级","会计证","统考类","教师资格证","证书展示" };

		img = new int[] { R.drawable.address_book_detail,
				R.drawable.address_book_detail, R.drawable.address_book_detail,
				R.drawable.address_book_detail, R.drawable.address_book_detail,
				R.drawable.address_book_detail, R.drawable.address_book_detail,
				R.drawable.address_book_detail };
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return titles[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public View getView(int potions, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_item, null);
		}

		ImageView icon = (ImageView) convertView.findViewById(R.id.grid_img);
		TextView title = (TextView) convertView.findViewById(R.id.grid_item);

		icon.setImageDrawable(context.getResources().getDrawable(img[potions]));
		title.setText(titles[potions]);
		return convertView;
	}

}
