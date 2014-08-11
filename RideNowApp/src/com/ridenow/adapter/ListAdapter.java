package com.ridenow.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ridenow.R;
import com.ridenow.activity.SelectGroupActivity;

public class ListAdapter extends CommonListAdapter<CreateGroupModel>
{

	public ListAdapter(Activity ctx, ArrayList<CreateGroupModel> list, Integer bucketSize)
	{
		super(ctx, list, bucketSize);
	}
	@Override
	protected View getMyElement(final int position, final CreateGroupModel currentElement, View convertView)
	{

		View myElement = View.inflate(ctx, R.layout.element, null);
		TextView title = (TextView) myElement.findViewById(R.id.ic_mynetwork_textview);
		title.setText(currentElement.getGroupname());
		myElement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ctx, SelectGroupActivity.class);
				Bundle b = new Bundle();
				//b.putSerializable("details", currentElement);
				intent.putExtra("data", b);
				ctx.startActivity(intent);

			}
		});

		return myElement;

	}
}
