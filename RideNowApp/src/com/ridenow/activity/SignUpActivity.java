package com.ridenow.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ridenow.BaseActivity;
import com.ridenow.R;
import com.ridenow.RideNowApp;
import com.ridenow.net.URLNameValuePairBuilder;
import com.ridenow.utils.AlertUtility;
import com.ridenow.utils.CommonUtility;
import com.ridenow.utils.StringUtility;

public class SignUpActivity extends BaseActivity implements OnClickListener{
	
	private EditText nameTxtBox, emailTxtBox, phoneTxtBox, passwordTxtBox;
	private CheckBox maleRadioBtn, femaleRadioBtn;
	private Button signupBtn;
	private ImageView profileImageView;
	private TextView maleTextView, femaleTextView;
	private LinearLayout maleLayout, femaleLayout;
	private String nameStr, emailStr, phoneStr, passwordStr, genderStr = ""; 
	
	private final String IMAGE_UPLOAD_PROCESS = "image_upload";
	private final String SIGNUP_PROCESS = "register_user";

	private Activity context;
	private static final int GALLERY_PIC_SELECTION = 200;
	private static final int CAMERA_PIC_SELECTION = 201;
	private static final int CROP_FROM_CAMERA = 202;
	private File f;

	private Uri mImageCaptureUri;
	private String strImageEncodeToBase64 = "";
	private Bitmap bitmapImage = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = (SignUpActivity) this;
        CommonUtility.fullScreenActivity(context);
        
		setContentView(R.layout.activity_signup);
		setupViews();       
		
	}
	
	private void setupViews()
	{
		profileImageView = (ImageView) findViewById(R.id.signup_profileImageView);
		nameTxtBox = (EditText) findViewById(R.id.signup_name_EditText);
		emailTxtBox = (EditText) findViewById(R.id.signup_email_EditText);
		phoneTxtBox = (EditText) findViewById(R.id.signup_phone_EditText);
		passwordTxtBox = (EditText) findViewById(R.id.signup_password_EditText);
		maleRadioBtn = (CheckBox) findViewById(R.id.maleRadioBtn);
		femaleRadioBtn = (CheckBox) findViewById(R.id.femaleRadioBtn);
		signupBtn = (Button) findViewById(R.id.signup_btn);
		maleTextView = (TextView)  findViewById(R.id.maleTextView);
		femaleTextView = (TextView) findViewById(R.id.femaleTextView);
		maleLayout = (LinearLayout) findViewById(R.id.maleLayout);
		femaleLayout = (LinearLayout) findViewById(R.id.femaleLayout);
		
		profileImageView.setOnClickListener(this);
		signupBtn.setOnClickListener(this);
		maleLayout.setOnClickListener(this);
		femaleLayout.setOnClickListener(this);
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_profileImageView:
			openPictureDialog();
			break;
		case R.id.maleLayout:
			femaleRadioBtn.setChecked(false);
			maleRadioBtn.setChecked(true);
			genderStr = maleTextView.getText().toString();
			maleRadioBtn.setBackgroundResource(R.drawable.circle);
			femaleRadioBtn.setBackgroundResource(R.drawable.white_circle_normal);
			//onRadioButtonClicked(maleRadioBtn);
			break;
		case R.id.femaleLayout:
			maleRadioBtn.setChecked(false);
			femaleRadioBtn.setChecked(true);
			femaleRadioBtn.setBackgroundResource(R.drawable.circle);
			maleRadioBtn.setBackgroundResource(R.drawable.white_circle_normal);
			genderStr = femaleTextView.getText().toString();
			//onRadioButtonClicked(femaleRadioBtn);
			break;
        case R.id.signup_btn:
        	
        	if(isValidInput())
        	{
        		new SignupAsyncTask(IMAGE_UPLOAD_PROCESS).execute();
        	}
			
			break;
		default:
			break;
		}
	}
	
	private boolean isValidInput()
	{
		boolean isValid = true;
		
		nameStr = nameTxtBox.getText().toString();
		emailStr = emailTxtBox.getText().toString();
		phoneStr = phoneTxtBox.getText().toString();
		passwordStr = passwordTxtBox.getText().toString();
		
		
		if(! StringUtility.isNotNullOrEmpty(nameStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.name_field_empty));
		}
		else if( ! StringUtility.isNotNullOrEmpty(emailStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.email_field_empty));
		}
		else if(! CommonUtility.isEmailValid(emailStr)){
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.email_not_valid));
		}
		else if( ! StringUtility.isNotNullOrEmpty(phoneStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.phone_field_empty));
		}
		else if( ! StringUtility.isNotNullOrEmpty(passwordStr))
		{
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.password_field_empty));
		}
		else if((! maleRadioBtn.isChecked()) && (! femaleRadioBtn.isChecked()))
		{
			System.out.println("Male Check - "+maleRadioBtn.isChecked()+", Female - "+femaleRadioBtn.isChecked());
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.gender_field_empty));
		}
		
		return isValid;
	}
	
	private void openPictureDialog()
	{
		final Dialog pictureDialog = new Dialog(context);
		pictureDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		pictureDialog.setContentView(R.layout.profile_image_dialog);
		
        Button captureCameraBtn = (Button) pictureDialog.findViewById(R.id.take_photo_btn);
		
		captureCameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_SELECTION);
				pictureDialog.dismiss();
			}
		});
		
		Button addFromGallery = (Button) pictureDialog.findViewById(R.id.add_from_gallery_btn);		
		addFromGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
                startActivityForResult(i, GALLERY_PIC_SELECTION);
				pictureDialog.dismiss();
			}
		});
		
		pictureDialog.show();
		
	}

	
	private class SignupAsyncTask extends AsyncTask<Void, Void, String>
	{

		ProgressDialog progressDialog;
		String ProcessName;
		
		public SignupAsyncTask(String processName) {
			super();
			ProcessName = processName;
		}

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
			
			if(ProcessName.equalsIgnoreCase(IMAGE_UPLOAD_PROCESS)){
				response = RideNowApp.getInstanceWebService().postData(CommonUtility.getCurrentDateTime()+"_"+RideNowApp.getInstanceAppPreferences().getAppUserImageUrl());
			}
			
            if(ProcessName.equalsIgnoreCase(SIGNUP_PROCESS)){
            	response = RideNowApp.getInstanceWebService().postData(URLNameValuePairBuilder.functionSignup,
    					URLNameValuePairBuilder.setSignUpParam(nameStr,emailStr,phoneStr,passwordStr,genderStr, CommonUtility.getDeviceId(context),"",""));
			}
			
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
			System.out.println("Result - "+result);
			
            if(ProcessName.equalsIgnoreCase(IMAGE_UPLOAD_PROCESS)){
            	new SignupAsyncTask(SIGNUP_PROCESS).execute();
			}
            if(ProcessName.equalsIgnoreCase(SIGNUP_PROCESS)){
            	handleSignUpResult(result);
            }
			
			
		}

		
	}
	
	private void handleSignUpResult(String result){
		try {
			

			JSONObject jsonObject = new JSONObject(result);
			int success = jsonObject.getInt("suceess");
			int UserId = jsonObject.getInt("userID");
			String status = jsonObject.getString("status");
			if (UserId > -1 && success == 1) {
				RideNowApp.getInstanceAppPreferences().setAppUserId(jsonObject.getInt("userID"));		
				RideNowApp.getInstanceAppPreferences().setAppUserName(nameStr);
				RideNowApp.getInstanceAppPreferences().setAppUserEmail(emailStr);
				RideNowApp.getInstanceAppPreferences().setAppUserPhone(phoneStr);
				RideNowApp.getInstanceAppPreferences().setAppUserPassword(passwordStr);
				RideNowApp.getInstanceAppPreferences().setAppUserGender(genderStr);
				Intent intent = new Intent(context,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) return;
		   
	    switch (requestCode) {
		        
	       case CROP_FROM_CAMERA:	    	
	        Bundle extras = data.getExtras();	
	        if (extras != null) {	        	
	        	bitmapImage = extras.getParcelable("data");
	        	if(bitmapImage!=null){
	                RideNowApp.getInstanceAppPreferences().setAppUserImageUrl(CommonUtility.BitMapToString(bitmapImage));
	               
	                profileImageView.setImageBitmap(CommonUtility.getRoundedShape(bitmapImage));             
	               }		                
	        }	
	        f = new File(mImageCaptureUri.getPath());            

	        break;	

		    case GALLERY_PIC_SELECTION:
		    	Uri selectedImage = data.getData();  
	            String[] filePathColumn = {MediaStore.Images.Media.DATA };  

	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);  
	            cursor.moveToFirst();  

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	            String picturePath = cursor.getString(columnIndex);  
	            cursor.close(); 

	            profileImageView.setImageBitmap(CommonUtility.getRoundedShape(BitmapFactory.decodeFile(picturePath))); 
	            
		    	break;
		    case CAMERA_PIC_SELECTION :
		    	bitmapImage = (Bitmap) data.getExtras().get("data");
		    	if(bitmapImage!=null){
	                RideNowApp.getInstanceAppPreferences().setAppUserImageUrl(CommonUtility.BitMapToString(bitmapImage));
	                profileImageView.setImageBitmap(CommonUtility.getRoundedShape(ThumbnailUtils.extractThumbnail(bitmapImage,500,500))); 
	               	                
	               }
				mImageCaptureUri = data.getData();
		    	break;
		        
	    }
	}
	
	private String encodeImageToBase64(Bitmap bitmapImage) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		if (bitmapImage != null) {
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		} else {
			Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.ride_now_icon);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		}
		byte[] byteArray = stream.toByteArray();
		strImageEncodeToBase64 = Base64.encodeToString(byteArray,Base64.DEFAULT);
     //   AlertUtility.printStatement("ProfileActivity","base64 code of -"+strImageEncodeToBase64);
		return strImageEncodeToBase64;
	}
	
}
