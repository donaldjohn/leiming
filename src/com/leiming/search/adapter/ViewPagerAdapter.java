package com.leiming.search.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 *  证书数据显示viewPager的adapter
 */
public class ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;
	int postions;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	public ViewPagerAdapter(List<View> views, Activity activity,int postion) {
		this.views = views;
		this.activity = activity;
		this.postions =postion;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		return views.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
