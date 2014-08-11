package com.ridenow.activity;

import java.net.URLEncoder;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ridenow.utils.AppConstants;


public class GmailRequestTokenActivity extends Activity {

	
	
    private OAuthConsumer consumer; 
    private OAuthProvider provider;
    private SharedPreferences prefs;
   
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 	

    	try {
    		consumer = new CommonsHttpOAuthConsumer(AppConstants.CONSUMER_KEY, AppConstants.CONSUMER_SECRET);
    		provider = new CommonsHttpOAuthProvider(
    				AppConstants.REQUEST_URL  + "?scope=" + URLEncoder.encode(AppConstants.SCOPE, AppConstants.ENCODING) + "&xoauth_displayname=" + AppConstants.APP_NAME,
    				AppConstants.ACCESS_URL,
    				AppConstants.AUTHORIZE_URL);
    	} catch (Exception e) {
    		Log.e(AppConstants.TAG, "Error creating consumer / provider",e);
    	}

    	getRequestToken();
    }

	
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent); 
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(AppConstants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(AppConstants.TAG, "Callback received : " + uri);
			Log.i(AppConstants.TAG, "Retrieving Access Token");
			getAccessToken(uri);
		}
	}
	
	private void getRequestToken() {
		try {
			Log.d(AppConstants.TAG, "getRequestToken() called");
			String url = provider.retrieveRequestToken(consumer,AppConstants.OAUTH_CALLBACK_URL);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			this.startActivity(intent);
			
		} catch (Exception e) {
			Log.e(AppConstants.TAG, "Error retrieving request token", e);
		}
	}
	
	private void getAccessToken(Uri uri) {
		final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		try {
			provider.retrieveAccessToken(consumer, oauth_verifier);

			final Editor edit = prefs.edit();
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			edit.commit();
			
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			
			consumer.setTokenWithSecret(token, secret);
			this.startActivity(new Intent(this ,SelectGroupActivity.class));

			Log.i(AppConstants.TAG, "Access Token Retrieved");
			
		} catch (Exception e) {
			Log.e(AppConstants.TAG, "Access Token Retrieval Error", e);
		}
	}
	
}
