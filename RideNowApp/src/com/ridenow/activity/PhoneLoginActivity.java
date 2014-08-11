package com.ridenow.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ridenow.BaseActivity;
import com.ridenow.R;
import com.ridenow.utils.CommonUtility;

public class PhoneLoginActivity extends BaseActivity {

	private EditText verifyMobileTxtBox;
	private Button sendVerifyCodeBtn;

	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = (PhoneLoginActivity) this;
        CommonUtility.fullScreenActivity(context);
        
		setContentView(R.layout.activity_phone_login);
		setupViews();       
		
	}
	
	private void setupViews()
	{
		verifyMobileTxtBox = (EditText) findViewById(R.id.mobile_EditText);		
		sendVerifyCodeBtn = (Button) findViewById(R.id.send_verifycode_btn);
		
		sendVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
	}
}
