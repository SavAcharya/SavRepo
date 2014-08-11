package com.ridenow.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

public class StringUtility {

public static boolean isNotNullOrEmpty (String data){
		
		Boolean isSuccess = true;
			
		if(data.equalsIgnoreCase(null)){
		    isSuccess = false;
		}
		else if(data.trim().length() <= 0){
			isSuccess = false;
		}
		else if(data.trim().equalsIgnoreCase("null")){
			isSuccess = false;
		}		
		else if(data.trim().equalsIgnoreCase("\"\"")){
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public static String FirstLetterInUpperCase(String word){
		
		String result = "";		
		if(word.length()>0){			
		  char c = word.charAt(0);
		  String splitedString = word.substring(1, word.length());
		  result = Character.toUpperCase(c)+splitedString;
		}
		return result;
	}
	
	
	public static boolean isSuccess(Activity context,String response) {
		
		Boolean isSuccess = true;
		JSONObject joResponse = null;
		try {
			joResponse = new JSONObject(response);
			int success = joResponse.getInt("success");
			if (success == 1) {				
			} 
			else {					
				String error = joResponse.getString("error").trim();
				AlertUtility.showToast(context, error);
				isSuccess = false;
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
	
}
