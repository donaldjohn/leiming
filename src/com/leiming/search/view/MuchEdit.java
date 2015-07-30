package com.leiming.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MuchEdit extends EditText {

	public MuchEdit(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MuchEdit(Context context,AttributeSet attr){
		super(context,attr);
	}

	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		// TODO Auto-generated method stub
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
	}
	
	
}
