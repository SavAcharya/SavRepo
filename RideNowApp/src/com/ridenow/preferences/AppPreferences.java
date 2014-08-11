package com.ridenow.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ridenow.utils.AlertUtility;

public class AppPreferences {
	
	private static final String CLASS_NAME = "AppPreferences";
	private static String APP_SHARED_PREFS;
	private static final String KEY_PREFIX = "APP_PREFERENCE_";

	private static final String FACEBOOK_LOGIN = KEY_PREFIX+"fb_login";
	private static final String FACEBOOK_ACCESS_TOKEN = KEY_PREFIX+"fb_access_token";	
	private static final String FACEBOOK_EXPIRES_TOKEN = KEY_PREFIX+"fb_expire_token";	
	private static final String FACEBOOK_USER_ID = KEY_PREFIX+"fb_user_id";
	private static final String FACEBOOK_USER_NAME = KEY_PREFIX+"fb_user_name";
	private static final String FACEBOOK_USER_EMAIL = KEY_PREFIX+"fb_user_email";
	private static final String FACEBOOK_USER_PROFILE_URL = KEY_PREFIX+"fb_user_profile_url";

	private static final String GOOGLE_USER_ID = KEY_PREFIX+"google_user_id";
	private static final String GOOGLE_USER_PROFILE_URL = KEY_PREFIX+"google_user_profile_url";
	private static final String GOOGLE_USER_NAME = KEY_PREFIX+"google_user_name";
	private static final String GOOGLE_USER_EMAIL = KEY_PREFIX+"google_user_email";
	
	private static final String APP_USER_ID = KEY_PREFIX+"app_user_id";
	private static final String APP_USER_NAME = KEY_PREFIX+"app_user_name";
	private static final String APP_USER_EMAIL = KEY_PREFIX+"app_user_email";
	private static final String APP_USER_PHONE = KEY_PREFIX+"app_user_phone";
	private static final String APP_USER_PASSWORD = KEY_PREFIX+"app_user_password";
	private static final String APP_USER_GENDER = KEY_PREFIX+"app_user_gender";
	private static final String APP_USER_IMAGEURL = KEY_PREFIX+"app_user_imageurl";

	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public AppPreferences(Context context) {

		APP_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(APP_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}

	/** Facebook Integration Data...**/

	public void setFacebookLogin(Boolean value) {
		mPrefsEditor.putBoolean(FACEBOOK_LOGIN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookLogin=" + value);
	}

	public boolean getFacebookLogin() {
		boolean value = mPrefs.getBoolean(FACEBOOK_LOGIN, false);
		AlertUtility.printStatement(CLASS_NAME, "getFacebookLogin=" + value);
		return value;
	}

	public void setFacebookAccessToken(String value) {
		mPrefsEditor.putString(FACEBOOK_ACCESS_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookAccessToken = " + value);
	}

	public String getFacebookAccessToken() {
		String value = mPrefs.getString(FACEBOOK_ACCESS_TOKEN, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookAccessToken=" + value);
		return value;
	}

	public void setFacebookTokenExpires(long value) {
		mPrefsEditor.putLong(FACEBOOK_EXPIRES_TOKEN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookTokenExpires = " + value);
	}

	public String getFacebookTokenExpires() {
		String value = mPrefs.getString(FACEBOOK_EXPIRES_TOKEN, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookTokenExpires=" + value);
		return value;
	}

	public void setFacebookID(String value) {
		mPrefsEditor.putString(FACEBOOK_USER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFacebookID = " + value);
	}

	public String getFacebookID() {
		String value = mPrefs.getString(FACEBOOK_USER_ID, "");
		AlertUtility.printStatement(CLASS_NAME, "getFacebookID=" + value);
		return value;
	}

	public void setFbName(String value) {
		mPrefsEditor.putString(FACEBOOK_USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFbName = " + value);
	}

	public String getFbName() {
		String value = mPrefs.getString(FACEBOOK_USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getFbName=" + value);
		return value;
	}

	public void setFbEmail(String value) {
		mPrefsEditor.putString(FACEBOOK_USER_EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFbEmail = " + value);
	}

	public String getFbEmail() {
		String value = mPrefs.getString(FACEBOOK_USER_EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getFbEmail=" + value);
		return value;
	}

	public void setFbProfileUrl(String value) {
		mPrefsEditor.putString(FACEBOOK_USER_PROFILE_URL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFbProfileUrl = " + value);
	}

	public String getFbProfileUrl() {
		String value = mPrefs.getString(FACEBOOK_USER_PROFILE_URL, "");
		AlertUtility.printStatement(CLASS_NAME, "getFbProfileUrl=" + value);
		return value;
	}


	/* Google Data....*/

	public void setGoogleId(long i) {
		mPrefsEditor.putLong(GOOGLE_USER_ID, i);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleId = " + i);
	}

	public long getGoogleId() {
		long value = mPrefs.getLong(GOOGLE_USER_ID, 0);
		AlertUtility.printStatement(CLASS_NAME, "getGoogleId=" + value);
		return value;
	}

	public void setGoogleName(String value) {
		mPrefsEditor.putString(GOOGLE_USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleName = " + value);
	}

	public String getGoogleName() {
		String value = mPrefs.getString(GOOGLE_USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getGoogleName=" + value);
		return value;
	}

	public void setGoogleEmail(String value) {
		mPrefsEditor.putString(GOOGLE_USER_EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleEmail = " + value);
	}

	public String getGoogleEmail() {
		String value = mPrefs.getString(GOOGLE_USER_EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getGoogleEmail=" + value);
		return value;
	}

	public void setGoogleProfileUrl(String value) {
		mPrefsEditor.putString(GOOGLE_USER_PROFILE_URL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setGoogleProfileUrl = " + value);
	}

	public String getGoogleProfileUrl() {
		String value = mPrefs.getString(GOOGLE_USER_PROFILE_URL, "");
		AlertUtility.printStatement(CLASS_NAME, "getGoogleProfileUrl=" + value);
		return value;
	}

	/** User values...**/
	
	public void setAppUserId(int value) {
		mPrefsEditor.putInt(APP_USER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserId = " + value);
	}

	public int getAppUserId() {
		int value = mPrefs.getInt(APP_USER_ID, 0);
		AlertUtility.printStatement(CLASS_NAME, "getAppUserId=" + value);
		return value;
	}
	
	public void setAppUserName(String value) {
		mPrefsEditor.putString(APP_USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserName = " + value);
	}

	public String getAppUserName() {
		String value = mPrefs.getString(APP_USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserName=" + value);
		return value;
	}
	
	public void setAppUserEmail(String value) {
		mPrefsEditor.putString(APP_USER_EMAIL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserEmail = " + value);
	}

	public String getAppUserEmail() {
		String value = mPrefs.getString(APP_USER_EMAIL, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserEmail=" + value);
		return value;
	}
	
	public void setAppUserPhone(String value) {
		mPrefsEditor.putString(APP_USER_PHONE, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserPhone = " + value);
	}

	public String getAppUserPhone() {
		String value = mPrefs.getString(APP_USER_PHONE, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserPhone=" + value);
		return value;
	}
	
	public void setAppUserPassword(String value) {
		mPrefsEditor.putString(APP_USER_PASSWORD, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserPassword = " + value);
	}

	public String getAppUserPassword() {
		String value = mPrefs.getString(APP_USER_PASSWORD, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserPassword=" + value);
		return value;
	}
	
	public void setAppUserGender(String value) {
		mPrefsEditor.putString(APP_USER_GENDER, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserGender = " + value);
	}

	public String getAppUserGender() {
		String value = mPrefs.getString(APP_USER_GENDER, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserGender=" + value);
		return value;
	}
	
	public void setAppUserImageUrl(String value) {
		mPrefsEditor.putString(APP_USER_IMAGEURL, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAppUserImageUrl = " + value);
	}

	public String getAppUserImageUrl() {
		String value = mPrefs.getString(APP_USER_IMAGEURL, "");
		AlertUtility.printStatement(CLASS_NAME, "getAppUserImageUrl=" + value);
		return value;
	}

	public void resetData()
	{
		
	}

}
