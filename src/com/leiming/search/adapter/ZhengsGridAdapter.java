
package com.leiming.search.adapter;


import com.leiming.search.R;
import com.leiming.search.untill.Container;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ZhengsGridAdapter extends BaseAdapter{

	Context context;
	String [] img;

	
	public ZhengsGridAdapter(Context context){
		this.context =context;
		img=Container.url;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return img[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return img.length;
	}

	@Override
	public View getView(int potions, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView ==null){
			convertView =LayoutInflater.from(context).inflate(R.layout.grid_item2, null);
		}
		
		ImageView icon =(ImageView) convertView.findViewById(R.id.grid_img);
		
		ImageLoader.getInstance().displayImage(img[potions], icon, Container.adUrl_options);
		return convertView;
	}

}
