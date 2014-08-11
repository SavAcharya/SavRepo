package com.ridenow.utils;

public class AppConstants {

	// Google Credentials ...
	private static final String GOOGLE_API_KEY = "AIzaSyDLf-BbWqSeHdfsPGYnfa9i8aJwV1IxKCw";
	// Facebook Credentials...
	public static final String FB_URL_PREFIX_ME = "https://graph.facebook.com/me?access_token=";
	public static final String PRODUCTION_URL = "http://www.ridenow.in/webservice/webservice";
	public final String privicyStatusPublic = "0";
	public final String privicyStatusPrivate = "1";
	public static final int rideTypeOffer = 0;
	public static final int rideTypeRequest = 1;

	// Constants For Gmail
	public static final String TAG = "OAuthExample";
	public static final String CONSUMER_KEY = "anonymous";
	public static final String CONSUMER_SECRET = "anonymous";
	public static final String SCOPE = "https://www.google.com/m8/feeds/";
	public static final String REQUEST_URL = "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL = "https://www.google.com/accounts/OAuthGetAccessToken";
	public static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken";
	public static final String GET_CONTACTS_FROM_GOOGLE_REQUEST = "https://www.google.com/m8/feeds/contacts/default/full?alt=json&max-results=30";
	public static final String ENCODING = "UTF-8";
	public static final String OAUTH_CALLBACK_SCHEME = "oauth-example";
	public static final String OAUTH_CALLBACK_HOST = "callback";
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;
	public static final String APP_NAME = "OAuthExample";
}
