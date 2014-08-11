package com.ridenow;

import android.app.Application;

import com.ridenow.net.WebService;
import com.ridenow.preferences.AppPreferences;

public class RideNowApp extends Application {

	private static AppPreferences appPreferences;
	private static WebService webService;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		appPreferences = new AppPreferences(getApplicationContext());
		webService = new WebService(getApplicationContext());
		
    }
	
	public static AppPreferences getInstanceAppPreferences() {
        return appPreferences;
    }
	
	public static WebService getInstanceWebService() {
        return webService;
    }
}
