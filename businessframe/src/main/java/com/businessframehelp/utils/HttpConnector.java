package com.businessframehelp.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
@Deprecated
public class HttpConnector {
	public static String cookie="";
	public static String getResponse(String urlString){
		Log.d("HttpConnector", "URL ==> "+urlString);
	    HttpURLConnection conn = null;
		long oldtime=System.currentTimeMillis();

	    StringBuilder jsonResults = new StringBuilder();
	    try {

	        URL url = new URL(urlString);
	        conn = (HttpURLConnection) url.openConnection();
			if(!"".equals(cookie)){
				conn.setRequestProperty("Cookie", "PHPSESSID=" + cookie);
				conn.setRequestProperty("Cookie", "JSESSIONID=" + cookie);

			}
			conn.connect();
			System.out.println(conn.getResponseCode());
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("start tobyte");
				InputStreamReader in = new InputStreamReader(conn.getInputStream());

				// Load the results into a StringBuilder
				int read;
				char[] buff = new char[512];
				while ((read = in.read(buff)) != -1) {
					jsonResults.append(buff, 0, read);
				}
				System.out.println("end tobyte");
			}
	        
	    } catch (MalformedURLException e) {
	        Log.e("AppUtil", "Error processing Autocomplete API URL", e);
	    } catch (IOException e) {
	        Log.e("AppUtil", "Error connecting to Autocomplete API", e);
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
	    Log.d("Result", jsonResults.toString());
		System.out.println("老耗时:"+(System.currentTimeMillis()-oldtime));
		return jsonResults.toString();
	}
}
