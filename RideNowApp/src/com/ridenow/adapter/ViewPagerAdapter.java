package com.ridenow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ridenow.fragments.RideFragment;
import com.ridenow.fragments.MyNetworkFragment;
import com.ridenow.fragments.ProfileFragment;
import com.ridenow.fragments.UpdateFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	final int PAGE_COUNT = 4;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			RideFragment ridefrag = new RideFragment();
			return ridefrag;
		case 1:
			ProfileFragment profilefrag = new ProfileFragment();
			return profilefrag;
		case 2:
			MyNetworkFragment searchfrag = new MyNetworkFragment();
			return searchfrag;
		case 3:
			UpdateFragment updatefrag= new UpdateFragment();
			return updatefrag;	
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

}
