package com.ridenow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ridenow.BaseActivity;
import com.ridenow.R;
import com.ridenow.utils.CommonUtility;

public class InitialLoginActivity extends BaseActivity {

	private Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = (InitialLoginActivity) this;
		CommonUtility.fullScreenActivity(context);
		setContentView(R.layout.activity_search_ride);

	}

	public void signup(View view) {
         Intent intent = new Intent(this, SignUpActivity.class);
         startActivity(intent);
	}

	public void signin(View view) {
		 Intent intent = new Intent(this, HomeActivity.class);
         startActivity(intent);
	}
}
