package com.ridenow.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ridenow.R;
import com.ridenow.adapter.ContactsCompletionView;
import com.ridenow.adapter.ListModel;
import com.ridenow.adapter.SelectGroupListAdapter;
import com.ridenow.adapter.TokenCompleteTextView.TokenListener;
import com.ridenow.utils.AppConstants;
import com.ridenow.utils.Utils;

public class SelectGroupActivity extends Activity implements
		OnItemClickListener, TextWatcher, OnClickListener, TokenListener {
	private ListView listView;
	private SelectGroupListAdapter listAdapter;
	private ArrayList<ListModel> applist = new ArrayList<ListModel>();
	private EditText addPeople;
	ArrayAdapter<ListModel> adapter;
	private ImageView captureProfileGroup;
	private ContactsCompletionView completionView;

	private static final int REQUEST_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectgroup);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (isOAuthSuccessful()) {
			listView = (ListView) findViewById(R.id.ic_newtworkgroup_listview);
			addPeople = (EditText) findViewById(R.id.add_user_group);
			captureProfileGroup = (ImageView) findViewById(R.id.ic_imagebutton_selectGroupPics_);
			captureProfileGroup.setOnClickListener(this);
			addPeople.addTextChangedListener(this);
			listView.setOnItemClickListener(this);
			listView.setTextFilterEnabled(true);
			
			getContacts();
	//		applist.addAll(Utils.getAllDummyData());
			// This is used to token selection in blue color in form of ListModel
			// Class
			completionView = (ContactsCompletionView) findViewById(R.id.add_user_group);
			completionView.setAdapter(adapter);
			completionView.setTokenListener(this);
			// This Adaptor is used for ListView and its Filtering of String
			listAdapter = new SelectGroupListAdapter(this, applist);
			listView.setAdapter(listAdapter);
    	}
    	else {
    		startActivity(new Intent().setClass(this, GmailRequestTokenActivity.class));
    	}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		ListModel list = applist.get(pos);
		boolean isSelect = list.isSelected() ? false : true;
		list.setisSelected(isSelect);
		if (isSelect)
			completionView.addObject(list);
		else
			completionView.removeObject(list);
		applist.remove(pos);
		applist.add(pos, list);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (isOAuthSuccessful()) {
    		applist.clear();
			getContacts();
			listAdapter.notifyDataSetChanged();
    	}
    	else {
    		Toast.makeText(this, "AuthToken Failure", Toast.LENGTH_LONG).show();
    		startActivity(new Intent().setClass(this, GmailRequestTokenActivity.class));
    	}
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// listAdapter.getFilter().filter(s.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_group, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.ic_menu_creategroup) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ic_imagebutton_selectGroupPics_:
			PopupMenu popup = new PopupMenu(this, v);
			popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
			popup.show();
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = null;
					switch (item.getItemId()) {
					case R.id.menu_camera:
						intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						File f = new File(android.os.Environment
								.getExternalStorageDirectory(), "temp.jpg");
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(f));
						startActivityForResult(intent, REQUEST_CAMERA);
						break;
					case R.id.menu_gallery:
						intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intent.setType("image/*");
						startActivityForResult(
								Intent.createChooser(intent, "Select File"),
								PICK_FROM_GALLERY);
						break;
					}

					return true;
				}
			});

			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);

					bm = Bitmap.createScaledBitmap(bm, 80, 80, true);
					captureProfileGroup.setImageBitmap(Utils
							.getCircularBitmap(bm));

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == PICK_FROM_GALLERY) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
				captureProfileGroup.setImageBitmap(Utils.getCircularBitmap(bm));
			}
		}
	}

	public Bitmap getCroppedBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void getContacts() {
		String jsonOutput = "";
		try {
			jsonOutput = makeSecuredReq(
					AppConstants.GET_CONTACTS_FROM_GOOGLE_REQUEST,
					getConsumer(this.prefs));
			JSONObject jsonResponse = new JSONObject(jsonOutput);
			JSONObject m = (JSONObject) jsonResponse.get("feed");
			JSONArray entries = (JSONArray) m.getJSONArray("entry");
			for (int i = 0; i < entries.length(); i++) {
				JSONObject entry = entries.getJSONObject(i);
				JSONObject title = entry.getJSONObject("title");
				if (title.getString("$t") != null
						&& title.getString("$t").length() > 0) {
					ListModel object = new ListModel(i,  title.getString("$t"), "", false, false);
					applist.add(object);
				}
			}
			
		} catch (Exception e) {
			Log.e(AppConstants.TAG, "Error executing request", e);
			
		}

	}

	  private boolean isOAuthSuccessful() {
	    	String token = prefs.getString(OAuth.OAUTH_TOKEN, null);
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, null);
			if (token != null && secret != null)
				return true;
			else 
				return false;
	    }
	private OAuthConsumer getConsumer(SharedPreferences prefs) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(AppConstants.CONSUMER_KEY,
				AppConstants.CONSUMER_SECRET);
		consumer.setTokenWithSecret(token, secret);
		return consumer;
	}
	private String makeSecuredReq(String url,OAuthConsumer consumer) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(url);
    	Log.i(AppConstants.TAG,"Requesting URL : " + url);
    	consumer.sign(request);
    	HttpResponse response = httpclient.execute(request);
    	Log.i(AppConstants.TAG,"Statusline : " + response.getStatusLine());
    	InputStream data = response.getEntity().getContent();
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responeLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((responeLine = bufferedReader.readLine()) != null) {
        	responseBuilder.append(responeLine);
        }
        Log.i(AppConstants.TAG,"Response : " + responseBuilder.toString());
        return responseBuilder.toString();
	}	
	
	@Override
	public void onTokenAdded(Object token) {
	}

	@Override
	public void onTokenRemoved(Object token) {
	}

}
