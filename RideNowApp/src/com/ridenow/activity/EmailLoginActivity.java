package com.ridenow.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ridenow.BaseActivity;
import com.ridenow.R;
import com.ridenow.RideNowApp;
import com.ridenow.net.URLNameValuePairBuilder;
import com.ridenow.utils.AlertUtility;
import com.ridenow.utils.CommonUtility;
import com.ridenow.utils.StringUtility;

public class EmailLoginActivity extends BaseActivity implements OnClickListener {

	private Activity context;
	
	private Button phoneLoginBtn, loginBtn;
	private EditText nameText, passwordText;
	private String nameStr, passwordStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = (EmailLoginActivity) this;
        CommonUtility.fullScreenActivity(context);
        
		setContentView(R.layout.activity_email_login);
		setupViews();       
		
	}

	private void setupViews()
	{
		
		nameText = (EditText) findViewById(R.id.signin_name_EditText);
		passwordText = (EditText) findViewById(R.id.signin_password_EditText);
		phoneLoginBtn = (Button) findViewById(R.id.phone_signin_btn);
		loginBtn = (Button) findViewById(R.id.login_btn);
		
		phoneLoginBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.phone_signin_btn:
			Intent intent = new Intent(context,PhoneLoginActivity.class);
			startActivity(intent);
			break;

        case R.id.login_btn:
			if(isValidInput())
			{
				new LoginAsyncTask().execute();
			}
			break;
		default:
			break;
		}
	}
	
	private boolean isValidInput()
	{
		boolean isValid = true;
		
		nameStr = nameText.getText().toString();
		passwordStr = passwordText.getText().toString();
				
		if(! StringUtility.isNotNullOrEmpty(nameStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.name_field_empty));
		}		
		else if( ! StringUtility.isNotNullOrEmpty(passwordStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.password_field_empty));
		}
				
		return isValid;
	}
	
	private class LoginAsyncTask extends AsyncTask<Void, Void, String>
	{

		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
           
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Loading...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);			
			progressDialog.setOnCancelListener(new OnCancelListener() {				
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});	
			progressDialog.show();	
		}

		@Override
		protected String doInBackground(Void... params) {
			
			String response = "";
			response = RideNowApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionSignin,
					URLNameValuePairBuilder.setSignInParam("email",nameStr,passwordStr));
			return response;
		}
		
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			progressDialog.dismiss();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			handleEmailLoginResult(result);
			
			Intent intent = new Intent(context,HomeActivity.class);
			startActivity(intent);
		}
		
	}
	
	private void handleEmailLoginResult(String result){
		try {
			JSONObject jsonObject = new JSONObject(result);
			int success = jsonObject.getInt("suceess");
			int UserId = jsonObject.getInt("userID");
			String status = jsonObject.getString("status");
			if (UserId > -1 && success == 1) {
				RideNowApp.getInstanceAppPreferences().setAppUserId(jsonObject.getInt("userID"));		
				Intent intent = new Intent(context,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
