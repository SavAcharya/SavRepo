package com.ridenow.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ridenow.R;
import com.ridenow.utils.CommonUtility;

public class SplashActivity extends Activity  {
	private static final long SPLASH_TIME = 2000;
	private Handler handler = new Handler();
	private Runnable splash_runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 CommonUtility.fullScreenActivity(this);
		setContentView(R.layout.activity_splash);
		
		splash_runnable = new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						InitialLoginActivity.class);
				startActivity(intent);
				finish();
			}
		};
		handler.postDelayed(splash_runnable, SPLASH_TIME);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (isFinishing()) {
			handler.removeCallbacks(splash_runnable);
		}
	}

	

}
