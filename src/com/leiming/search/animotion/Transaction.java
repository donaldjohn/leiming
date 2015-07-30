package com.leiming.search.animotion;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class Transaction {

	public static void transfAnimation(View v,final View v2) {
//		AnimationSet as = new AnimationSet(true);// 1.实例化AnimationSet
		TranslateAnimation aa = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
		aa.setStartOffset(1000);
		aa.setDuration(1500);// 设置动画的时间
		aa.setFillEnabled(true);
		aa.setFillAfter(true);	
//		as.addAnimation(aa);// 3.将animation加入AnimationSet
		aa.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				alphaAnimation(v2);
				v2.setVisibility(View.VISIBLE);
			}
		});
		v.startAnimation(aa);
	
	}

	public static void alphaAnimation(View v) {
		
		AnimationSet as = new AnimationSet(true);// 1.实例化AnimationSet
		AlphaAnimation aa = new AlphaAnimation(0, 1);// 2.创建需要的animation
		aa.setDuration(2000);// 设置动画的时间
		aa.setFillAfter(true);
		as.addAnimation(aa);// 3.将animation加入AnimationSet
		v.startAnimation(as);
		
	}
}
