package com.ridenow.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ridenow.utils.AppConstants;

public class WebService {
	private static final String CLASS_NAME = "WebService";
	private Context context;
	
	public final String SERVER_BUSY = "-1";	
	public  final String NO_INTERNET = "-2";
	
	public WebService(Context applicationContext) {
		context = applicationContext;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	// RequestType is Post
	// RequestType is Post
		public String postData(String functionUrl) {

			HttpURLConnection connection = null;
			String downloadedString = "";
			
			if(isOnline()){
				
				try {
					
					URL	url = new URL(functionUrl);
					System.out.println(CLASS_NAME+" Final URL ======================= "+url);
					connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(10000);
					connection.setUseCaches(false);
					connection.connect();
					int responsecode = connection.getResponseCode();
					Log.v("responsecode", "" + responsecode);
					if (responsecode == HttpURLConnection.HTTP_OK) {
						
						StringBuilder content = new StringBuilder();
			            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()), 8);
			            String line;
			            while ((line = bufferedReader.readLine()) != null) {
			                content.append(line + "\n");
			            }
			            bufferedReader.close();
			            downloadedString = content.toString();
						System.out.println(CLASS_NAME+ " downloaded String" + downloadedString);	
						if(downloadedString==null||downloadedString==""||downloadedString.length()==0){
							downloadedString = SERVER_BUSY;
						}
					}
					connection.disconnect();
				} 
				catch (MalformedURLException ee) {
					ee.printStackTrace();
					System.out.println("malformed url");
				} catch (IOException eee) {
					System.out.println("io excp");
					eee.printStackTrace();
				}
			}
			else{			
				System.out.println("Sorry,Please check your data package/wifi settings.");
				downloadedString = NO_INTERNET;
			}
			System.out.println("WebService downloaded string length" + downloadedString.length());
			return downloadedString;
		}
		
		// RequestType is Post With NameValuePair
				public String postData(String functionName,List<NameValuePair> nameValuePair) {

					String downloadedString = "";
					
					if(isOnline()){
						
						BufferedReader in = null;
				        try {
				        	final HttpParams httpParams = new BasicHttpParams();
				            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
				            HttpClient client =  getNewHttpClient();//new DefaultHttpClient(httpParams);
				            HttpPost request = new HttpPost(AppConstants.PRODUCTION_URL+functionName);	            
				            
				            
				            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				            
				            for(int i =0; i < nameValuePair.size();i++){
				            	
				            	postParameters.add(new BasicNameValuePair(nameValuePair.get(i).getName(), nameValuePair.get(i).getValue()));
				            }
				            
				            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,"UTF-8");

				            request.setEntity(formEntity);
				            HttpResponse response = client.execute(request);
				            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()),8);
				            StringBuffer sb = new StringBuffer("");
				            String line = "";
				            String NL = System.getProperty("line.separator");
				            while ((line = in.readLine()) != null) {
				                sb.append(line + NL);
				            }
				            in.close();
				            downloadedString = sb.toString();
				            System.out.println("downloadedString  = "+downloadedString);
				            if(downloadedString==null||downloadedString==""||downloadedString.length()==0){
								downloadedString = SERVER_BUSY;
							}
				        } 
				        catch(Exception e) {
				        }
					}
					else{			
						System.out.println("Sorry,Please check your data package/wifi settings.");
						downloadedString = NO_INTERNET;
					}
					System.out.println("WebService downloaded string length" + downloadedString.length());
					return downloadedString;
				}

				
				public HttpClient getNewHttpClient() {
				    try {
				        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				        trustStore.load(null, null);

				        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
				        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				        HttpParams params = new BasicHttpParams();
				        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

				        SchemeRegistry registry = new SchemeRegistry();
				        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				        registry.register(new Scheme("https", sf, 443));

				        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

				        return new DefaultHttpClient(ccm, params);
				    } catch (Exception e) {
				        return new DefaultHttpClient();
				    }
				}

}
