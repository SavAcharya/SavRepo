package com.ridenow.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ridenow.R;

/**
 * Sample token completion view for basic contact info
 * 
 * Created on 8/6/14.
 * 
 * @author Manoj
 */
public class ContactsCompletionView extends TokenCompleteTextView {

	public ContactsCompletionView(Context context) {
		super(context);
	}

	public ContactsCompletionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ContactsCompletionView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View getViewForObject(Object object) {
		ListModel p = (ListModel) object;

		LayoutInflater l = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		LinearLayout view = (LinearLayout) l.inflate(R.layout.contact_token,
				(ViewGroup) ContactsCompletionView.this.getParent(), false);
		((TextView) view.findViewById(R.id.name)).setText(p.getName());

		return view;
	}

	@Override
	protected Object defaultObject(String completionText) {
		return new ListModel(completionText);

	}
}
