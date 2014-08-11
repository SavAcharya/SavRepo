package com.ridenow.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class AddView extends Button {

	private AddNetwork listener;



	public AddView(Context context, AddNetwork listener) {
		super(context);
		
	}
	public AddView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	public void setOnAddListener(AddNetwork add){
		this.listener = add;
	}
	
	
	public static interface AddNetwork{
		public void onAddViewClick();
		
	}

}
