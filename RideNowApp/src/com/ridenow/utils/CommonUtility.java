package com.ridenow.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class CommonUtility {

	public static boolean isOnline(Activity context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}
	
	public static Bitmap getCroppedBitmap(Bitmap bitmap) {
		if(bitmap != null){
			 Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
			    Canvas canvas = new Canvas(output);

			    final int color = 0xff424242;
			    final Paint paint = new Paint();
			    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

			    paint.setAntiAlias(true);
			    canvas.drawARGB(0, 0, 0, 0);
			    paint.setColor(color);
			    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
			    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			    canvas.drawBitmap(bitmap, rect, rect, paint);
			    return output;
		}
	   return null;
	}
	//http://graph.facebook.com/100003516621384/picture?type=large
	public static Bitmap urlToBitmapImage(String url) {
		String AvatarUrl = null;
		Bitmap AvatarBitmap = null;
		try {
			AvatarUrl = url;
			HttpGet httpRequest = new HttpGet(URI.create(AvatarUrl) );
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			AvatarBitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
			httpRequest.abort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return AvatarBitmap;
	}
	
	public static String urlToStringImage(String Url){
		  URL AvatarUrl = null;
		  Bitmap AvatarBitmap = null;
		  String profileImage=null;
		  try {
		   AvatarUrl = new URL(Url);
		   AvatarBitmap = BitmapFactory.decodeStream(AvatarUrl.openConnection().getInputStream());
		  profileImage=CommonUtility.BitMapToString(AvatarBitmap);
		   
		   
		  } catch (MalformedURLException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return profileImage;
		  }
	
	public static Bitmap getStringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
          }catch(Exception e){
            e.getMessage();
           return null;
          }
  }
	
	// To get Rounded Shape Image...
		public static Bitmap getRoundedShape(Bitmap bitmap) {
			// TODO Auto-generated method stub
			if (bitmap != null) {
				Bitmap targetBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(targetBitmap);

				final int color = 0xff424242;
				final Paint paint = new Paint();
				paint.setAntiAlias(true);
				canvas.drawARGB(0, 0, 0, 0);
				paint.setColor(color);

				Path path = new Path();
				path.addCircle(((float) bitmap.getWidth() - 1) / 2,((float) bitmap.getHeight() - 1) / 2,
						(Math.min(((float) bitmap.getWidth()),((float) bitmap.getHeight())) / 2),
						 Path.Direction.CCW);
				canvas.clipPath(path);
				Bitmap sourceBitmap = bitmap;
				canvas.drawBitmap(bitmap,new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
						new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), paint);
				return targetBitmap;
			}
			return null;

		}
		
	public static void fullScreenActivity(Activity context)
	{
		context.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public static Boolean SaveMyProfileImageToApp (Activity cxt,Bitmap bitmap, String FileName) {
    	try
    	{
	    	FileOutputStream fos = cxt.openFileOutput(FileName, Context.MODE_PRIVATE);
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    	bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
	    	byte[] b = baos.toByteArray(); 
	    	fos.write(b);
	    	fos.flush();
	    	fos.close();
	    	System.out.println("Image Saved : "  + FileName );
	    	return true;
    	}
    	catch(Exception er)
    	{
    		System.out.println(" Errrrrrrrrrrrrrrrrrr while Saving  : (" + FileName + ") " + er.getMessage());
    		return false;
    	}
    }
	
	public static String BitMapToString(Bitmap bitmap){
		System.out.println("fb bitmap===="+bitmap);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }
	
	public static Bitmap StringToBitMap(String image){
	       try{
	           byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
	           Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	           return bitmap;
	         }catch(Exception e){
	           e.getMessage();
	          return null;
	         }
	 }
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        Log.e("src",src);
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        Log.e("Bitmap","returned");
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("Exception",e.getMessage());
	        return null;
	    }
	}
	
	public static String convertBitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String imageUrl=Base64.encodeToString(b, Base64.DEFAULT);
        return imageUrl;
    }
	
	
	public static String getDeviceId(Activity context)
	{
		 String deviceId = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
		 return deviceId;
	}

	public static boolean isEmailValid(String email) {
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Boolean validation = emailPattern.matcher(email).matches();
		return validation;
	}
	
    public static String getCurrentDateTime(){
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
 
}
