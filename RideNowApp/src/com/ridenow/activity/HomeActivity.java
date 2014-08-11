package com.ridenow.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.view.Window;
import com.ridenow.R;
import com.ridenow.adapter.ViewPagerAdapter;

public class HomeActivity extends SherlockFragmentActivity {

	// Declare Variables
	ActionBar mActionBar;
	ViewPager mPager;
	Tab tab;
	private boolean useLogo = false;
	private boolean showHomeUp = true;
	private final Handler handler = new Handler();
	ActionMode mMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.homefragment);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(true);
		mActionBar.setIcon(R.drawable.ride_now_icon);
		mActionBar.setTitle("");
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mPager = (ViewPager) findViewById(R.id.pager);
		FragmentManager fm = getSupportFragmentManager();
		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				mActionBar.setSelectedNavigationItem(position);
			}
		};

		mPager.setOnPageChangeListener(ViewPagerListener);
		ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
		mPager.setAdapter(viewpageradapter);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		};
		tab = mActionBar.newTab()
				.setText(getResources().getString(R.string.ride))
				.setTabListener(tabListener);
		mActionBar.addTab(tab);
		tab = mActionBar.newTab()
				.setText(getResources().getString(R.string.profile))
				.setTabListener(tabListener);
		mActionBar.addTab(tab);
		tab = mActionBar.newTab()
				.setText(getResources().getString(R.string.mynetwork))
				.setTabListener(tabListener);
		mActionBar.addTab(tab);
		tab = mActionBar.newTab()
				.setText(getResources().getString(R.string.updates))
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.actionbarmain_menu, menu);
		final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_refresh);
		refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				handler.postDelayed(new Runnable() {
					public void run() {
						refresh.setActionView(null);
					}
				}, 1000);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.menu_refresh:
	            item.setActionView(R.layout.indeterminate_progress_action);
	            return true;
	        case R.id.menu_both:
	        	mMode = startActionMode(new AnActionModeOfEpicProportions());
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	 private final class AnActionModeOfEpicProportions implements ActionMode.Callback {
	        @Override
	        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	            boolean isLight = false; //SampleList.THEME == R.style.Theme_Sherlock_Light;
	            mode.setTitle("RideNow");

	            menu.add("Star")
	                .setIcon(isLight ? R.drawable.ic_menu_star_holo_light : R.drawable.ic_menu_star_holo_dark)
	                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

	            menu.add("Copy")
	                .setIcon(R.drawable.ic_action_copy)
	                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        
	            return true;
	        }

	        @Override
	        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	            return false;
	        }

	        @Override
	        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	           // Toast.makeText(HomeActivity.this, "Got click: " + item, Toast.LENGTH_SHORT).show();
	            mode.finish();
	            return true;
	        }

	        @Override
	        public void onDestroyActionMode(ActionMode mode) {
	        }
	    }


}
