package com.ridenow.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ridenow.utils.AlertUtility;

public class URLNameValuePairBuilder {

private static String TAG = "URLNameValuePairBuilder";

	
	public static String functionSignup = "/signup";
	public static List<NameValuePair> setSignUpParam(String name, String email, String mobile, String password, String gender, String deviceid, String facebookId, String googleId) 
	{

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		
		postParameters.add(new BasicNameValuePair("name", name));
		postParameters.add(new BasicNameValuePair("email", email));
		postParameters.add(new BasicNameValuePair("mobile", mobile));
		postParameters.add(new BasicNameValuePair("password", password));
		postParameters.add(new BasicNameValuePair("gender", gender));
		postParameters.add(new BasicNameValuePair("deviceid", deviceid));
		postParameters.add(new BasicNameValuePair("devicetype","Android"));		
		postParameters.add(new BasicNameValuePair("facebookid",facebookId));
		postParameters.add(new BasicNameValuePair("googleid",googleId));
		
		AlertUtility.printStatement(TAG,"setSignUpParameter ---name = " + name+" , email = "+ email+" , mobile = "+mobile
				+" ,password = "+password+" ,gender = "+gender+" ,deviceid = "+deviceid+" ,facebookId = "+facebookId+" ,googleId = "+googleId);

		return postParameters;
	}
	
	public static String functionSignin = "/signin";
	public static List<NameValuePair> setSignInParam(String loginType, String email, String password) 
	{

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();		
		postParameters.add(new BasicNameValuePair("logintype", loginType));
		postParameters.add(new BasicNameValuePair("email", email));
		postParameters.add(new BasicNameValuePair("password", password));
		
		AlertUtility.printStatement(TAG,"setSignInParameter ---LoginType = " + loginType+" , email = "+ email+" ,password = "+password);
		
		return postParameters;
	}
	
	public static String functionUploadImage = "/uploadimage";
	public static List<NameValuePair> setUploadImageParam(String image) 
	{

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();		
		postParameters.add(new BasicNameValuePair("file", image));
		
		AlertUtility.printStatement(TAG,"setUploadImageParam ---ImageURL = " + image);
		
		return postParameters;
	}
	
	public static String functionCreateRide = "/uploadimage";
	public static List<NameValuePair> setCreateRideParam(String from, String to, String date, String time, String seats, String privacy, String rideType) 
	{

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		
		postParameters.add(new BasicNameValuePair("from", from));
		postParameters.add(new BasicNameValuePair("to", to));
		postParameters.add(new BasicNameValuePair("date", date));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("seats", seats));
		postParameters.add(new BasicNameValuePair("privacy", privacy));
		postParameters.add(new BasicNameValuePair("rideType",rideType));	
		
		AlertUtility.printStatement(TAG,"setSignUpParameter ---from = " + from+" , to = "+ to+" , date = "+date
				+" ,time = "+time+" ,seats = "+seats+" ,privacy = "+privacy+" ,rideType = "+rideType );

		return postParameters;
	}
	
}
