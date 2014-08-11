package com.ridenow.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.ridenow.R;
import com.ridenow.activity.SelectGroupActivity;
import com.ridenow.adapter.CreateGroupModel;
import com.ridenow.adapter.ListAdapter;
import com.ridenow.adapter.ViewList;
import com.ridenow.utils.Utils;
import com.ridenow.widgets.AddView.AddNetwork;

public class MyNetworkFragment extends SherlockFragment implements AddNetwork, OnClickListener {

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.mynetwork, container, false);
		ArrayList<CreateGroupModel> list = (ArrayList<CreateGroupModel>) Utils.getAllDummyDataNetwork();
		ViewList mylist = (ViewList)view.findViewById(R.id.groupListView);
		ListAdapter adap = new ListAdapter(getActivity(), list, 150);
		adap.enableAutoMeasure(150);
		View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.addmore, null, false);
		Button admore =(Button)footerView.findViewById(R.id.ic_addmore_button);
		admore.setOnClickListener(this);
		mylist.addFooterView(footerView);
		mylist.setAdapter(adap);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	

	@Override
	public void onAddViewClick() {
		 Intent intent = new Intent(getActivity(),SelectGroupActivity.class);
         startActivity(intent);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
