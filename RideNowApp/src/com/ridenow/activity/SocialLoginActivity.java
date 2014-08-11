package com.ridenow.activity;


public class SocialLoginActivity{ /*extends BaseActivity implements OnClickListener,ConnectionCallbacks,
OnConnectionFailedListener,ResultCallback<People.LoadPeopleResult>{

	private static final String TAG = SocialLoginActivity.class.getSimpleName();
	private Button googleLoginInBtn, fbLoginBtn, loginBtn, appSignupBtn;
	private Activity context;
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private boolean isFacebookClick = false;
	
	private static final int STATE_DEFAULT = 0;
	private static final int STATE_SIGN_IN = 1;
	private static final int STATE_IN_PROGRESS = 2;
	private static final int RC_SIGN_IN = 0;
    private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
	private static final String SAVED_PROGRESS = "sign_in_progress";
	private GoogleApiClient mGoogleApiClient;
	private int mSignInProgress;
	private PendingIntent mSignInIntent;
	private int mSignInError;
	private ProgressDialog mProgressDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = (SocialLoginActivity) this;
        CommonUtility.fullScreenActivity(context);
        
		setContentView(R.layout.activity_social_login);
		       
       
        getFbHashKey();	   
		setupViews();       
		checkFbSession(savedInstanceState);	
		
		 if (savedInstanceState != null) {
		      mSignInProgress = savedInstanceState.getInt(SAVED_PROGRESS, STATE_DEFAULT);
		    }

		    mGoogleApiClient = buildGoogleApiClient();
		    mProgressDialog = new ProgressDialog(this);
		
	}
	
	private GoogleApiClient buildGoogleApiClient() {
	    // When we build the GoogleApiClient we specify where connected and
	    // connection failed callbacks should be returned, which Google APIs our
	    // app uses and which OAuth 2.0 scopes our app requests.
	    return new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API, Plus.PlusOptions.builder().build())
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
	}
	
	private void setupViews() {
		googleLoginInBtn = (Button) findViewById(R.id.google_signup_btn);
		fbLoginBtn = (Button) findViewById(R.id.fb_signup_btn);
		appSignupBtn = (Button) findViewById(R.id.app_signup_btn);
		loginBtn = (Button) findViewById(R.id.login_btn);
			
		fbLoginBtn.setOnClickListener(this);
		googleLoginInBtn.setOnClickListener(this);
		appSignupBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		appSignupBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
	   switch (v.getId()) {
	     case R.id.fb_signup_btn:
	     isFacebookClick = true;
		 loginToFacebookMethod();
		 break;
		 
         case R.id.google_signup_btn:
        	 System.out.println("CLick++++++++++++++++++++++");
        	    if (!mGoogleApiClient.isConnecting()) {
        	    	mProgressDialog.setIndeterminate(false);
    	          	mProgressDialog.setMessage("Loading...");
    	          	mProgressDialog.setOnCancelListener(new OnCancelListener() {
    	  				
    	  				@Override
    	  				public void onCancel(DialogInterface dialog) {
    	  				     mProgressDialog.dismiss();
    	  				}
    	  			});
    	          	mProgressDialog.show();
    	            resolveSignInError();
        	      }
        
		 break;

         case R.id.app_signup_btn:
	     Intent intent = new Intent(context,SignUpActivity.class);
	     startActivity(intent);
	     break;

         case R.id.login_btn:
        	 Intent emailLoginIintent = new Intent(context,EmailLoginActivity.class);
    	     startActivity(emailLoginIintent);
	     break;
	     
         default:
 			break;

      }
    }

	 Facebook Integration Start..............
	private void getFbHashKey() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.app",PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

	}

	private void checkFbSession(Bundle savedInstanceState)
	{
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
			}
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		      
	}

	@Override
	public void onResume() {
		super.onResume();    
		AppEventsLogger.activateApp(SocialLoginActivity.this);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int responseCode,Intent data) {
		super.onActivityResult(requestCode, responseCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				responseCode, data);

		if (requestCode == RC_SIGN_IN && responseCode == RESULT_OK) {
			// If the error resolution was successful we should continue
			// processing errors.
			mSignInProgress = STATE_SIGN_IN;
		} else {
			// If the error resolution was not successful or the user canceled,
			// we should stop processing errors.
			mSignInProgress = STATE_DEFAULT;
		}

		if (!mGoogleApiClient.isConnecting()) {
			// If Google Play services resolved the issue with a dialog then
			// onStart is not called so we need to re-attempt connection here.
			mGoogleApiClient.connect();
		}

	}
	
	public void onConnectionSuspended(int cause) {
		// The connection to Google Play services was lost for some reason.
		// We call connect() to attempt to re-establish the connection or get a
		// ConnectionResult that we can attempt to resolve.
		mGoogleApiClient.connect();
	}

	  @Override
	  protected Dialog onCreateDialog(int id) {
	    switch(id) {
	      case DIALOG_PLAY_SERVICES_ERROR:
	        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
	          return GooglePlayServicesUtil.getErrorDialog(
	              mSignInError,
	              this,
	              RC_SIGN_IN,
	              new DialogInterface.OnCancelListener() {
	                @Override
	                public void onCancel(DialogInterface dialog) {
	                  Log.e(TAG, "Google Play services resolution cancelled");
	                  mSignInProgress = STATE_DEFAULT;
	                 
	                }
	              });
	        } else {
	          return new AlertDialog.Builder(this)
	              .setMessage(R.string.play_services_error)
	              .setPositiveButton(R.string.close,
	                  new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                      Log.e(TAG, "Google Play services error could not be " + "resolved: " + mSignInError);
	                      mSignInProgress = STATE_DEFAULT;
	                     
	                    }
	                  }).create();
	        }
	      default:
	        return super.onCreateDialog(id);
	    }
	  }

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);

	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
		outState.putInt(SAVED_PROGRESS, mSignInProgress);
	}

	 Google Integration Start...........
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());
		 mProgressDialog.dismiss();
		if (mSignInProgress != STATE_IN_PROGRESS) {
			// We do not have an intent in progress so we should store the
			// latest
			// error resolution intent for use when the sign in button is
			// clicked.
			mSignInIntent = result.getResolution();
			mSignInError = result.getErrorCode();

			if (mSignInProgress == STATE_SIGN_IN) {
				// STATE_SIGN_IN indicates the user already clicked the sign in
				// button
				// so we should continue processing errors until the user is
				// signed in
				// or they click cancel.
				resolveSignInError();
			}
		}
	}

	  private void resolveSignInError() {
		    if (mSignInIntent != null) {
		      // We have an intent which will allow our user to sign in or
		      // resolve an error.  For example if the user needs to
		      // select an account to sign in with, or if they need to consent
		      // to the permissions your app is requesting.

		      try {
		        // Send the pending intent that we stored on the most recent
		        // OnConnectionFailed callback.  This will allow the user to
		        // resolve the error currently preventing our connection to
		        // Google Play services.
		        mSignInProgress = STATE_IN_PROGRESS;
		        startIntentSenderForResult(mSignInIntent.getIntentSender(),RC_SIGN_IN, null, 0, 0, 0);
		      } catch (SendIntentException e) {
		        Log.i(TAG, "Sign in intent could not be sent: "
		            + e.getLocalizedMessage());
		        // The intent was canceled before it was sent.  Attempt to connect to
		        // get an updated ConnectionResult.
		        mSignInProgress = STATE_SIGN_IN;
		        mGoogleApiClient.connect();
		      }
		    } else {
		      // Google Play services wasn't able to provide an intent for some
		      // error types, so we show the default Google Play services error
		      // dialog which may still start an intent on our behalf if the
		      // user can resolve the issue.
		      showDialog(DIALOG_PLAY_SERVICES_ERROR);
		    }
		  }

	@Override
	public void onConnected(Bundle arg0) {
		 mProgressDialog.dismiss();
		    // Retrieve some profile information to personalize our app for the user.
		    Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

		    System.out.println("Person Info - "+currentUser.getId()+","+currentUser.getDisplayName()+","+currentUser.getGender());

		    Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);

		    Intent intent = new Intent(context,HomeActivity.class);
		    startActivity(intent);
		    // Indicate that the sign in process is complete.
		    mSignInProgress = STATE_DEFAULT;
		
	}

	private void loginToFacebookMethod() {
		Session session = Session.getActiveSession();
		if ((session != null) && (session.isOpened())) {
			RideNowApp.getInstanceAppPreferences().setFacebookAccessToken(session.getAccessToken());
			new AsyncFacebookTask(AppConstants.FB_URL_PREFIX_ME + session.getAccessToken()).execute();
			isFacebookClick = false;
		} else {
			openFbDialog();
		}
	}

	private void openFbDialog() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			OpenRequest op = new Session.OpenRequest(this).setCallback(statusCallback);
			op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
			op.setCallback(null);
			List<String> permissions = new ArrayList<String>();
			permissions.add("email");
			op.setPermissions(permissions);
			session.openForRead(op);
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {

			if(session != null && session.isOpened() && isFacebookClick)
			{
				loginToFacebookMethod();
			}

		}
	}

	private class AsyncFacebookTask extends AsyncTask<Void, Void, String> implements OnCancelListener{
		String functionUrl;

		private ProgressDialog mProgressDialog;
	
		public AsyncFacebookTask(String functionUrl) {
			super();
			this.functionUrl = functionUrl;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
			mProgressDialog = new ProgressDialog(context);	
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			String response = null;	
			response = RideNowApp.getInstanceWebService().postData(functionUrl);				
			return response;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			finish();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			if (result.equals(RideNowApp.getInstanceWebService().SERVER_BUSY)) {				
				Toast.makeText(context, getString(R.string.server_busy),Toast.LENGTH_SHORT).show();			
			} else if (result.equals(RideNowApp.getInstanceWebService().NO_INTERNET)) {					
				Toast.makeText(context, getString(R.string.no_internet),Toast.LENGTH_SHORT).show();					
			}else {
				RideNowApp.getInstanceAppPreferences().setFacebookLogin(true);
				handleFacebookData(result);
				
				if(!(RideNowApp.getInstanceAppPreferences().getFacebookID().equalsIgnoreCase("")))
				{
					 downloadFbAvatar();
				}

			}

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			cancel(true);
		}

	}

	private void handleFacebookData(String result) {
		JSONObject mainJsonObject = null;
		try {
			mainJsonObject = new JSONObject(result);

			RideNowApp.getInstanceAppPreferences().setFacebookID(mainJsonObject.getString("id"));
			RideNowApp.getInstanceAppPreferences().setFbName(mainJsonObject.getString("name"));
			if(mainJsonObject.has("email"))
			{
				RideNowApp.getInstanceAppPreferences().setFbEmail(mainJsonObject.getString("email"));
			}
			else
			{
				RideNowApp.getInstanceAppPreferences().setFbEmail("");
			}

		} catch (JSONException e) {
			e.printStackTrace();

		}
	}

	private synchronized void downloadFbAvatar() {
		
		AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
			ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("Sync With Facebook...");
				progressDialog.show();
				progressDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel(true);
					}
				});
			}

			
			@Override
			public Bitmap doInBackground(Void... params) {
				//URL fbAvatarUrl = null;
				Bitmap fbAvatarBitmap = null;
				fbAvatarBitmap=CommonUtility.urlToBitmapImage("http://graph.facebook.com/"+ RideNowApp.getInstanceAppPreferences().getFacebookID() + "/picture?type=large");
				System.out.println("back ground fbAvatarBitmap---"+fbAvatarBitmap);
				return fbAvatarBitmap;
			}
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
				finish();
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				progressDialog.dismiss();
				RideNowApp.getInstanceAppPreferences().setFbProfileUrl(CommonUtility.BitMapToString(result));
				
				Intent intent = new Intent(context, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		};
		task.execute();
	}

	@Override
	public void onResult(LoadPeopleResult arg0) {
		// TODO Auto-generated method stub
		
	}

	*/
}
