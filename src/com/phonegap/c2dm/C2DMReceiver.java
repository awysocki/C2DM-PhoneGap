package com.phonegap.c2dm;

//--------------------------------------------------------
//My knowledge came from
//Android Cloud to Device Messaging (C2DM) - Tutorial
//http://www.vogella.de/articles/AndroidCloudToDeviceMessaging/article.html
//--------------------------------------------------------

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.c2dm.C2DMBaseReceiver;
import com.plugin.C2DM.C2DMPlugin;

public class C2DMReceiver extends C2DMBaseReceiver {
	
	public static final String ME="C2DMReceiver";	
	
	
    public C2DMReceiver() {
        super("man");	// This is currently not used, a constructor is required 
        Log.v(ME, "Constructor");	
    }

    @Override
    public void onRegistered(Context context, String registrationId)
            throws java.io.IOException {
    	
    	Log.v(ME + ":onRegistered", "Registration ID arrived!");
		Log.v(ME + ":onRegistered", registrationId);
		
		JSONObject json;
		
		try
		{
			json = new JSONObject().put("event", "registered");		
			json.put("regid", registrationId);

			Log.v(ME + ":onRegisterd", json.toString());
			
			// Send this JSON data to the JavaScript application above EVENT should be set to the msg type 
			// In this case this is the registration ID
			C2DMPlugin.sendJavascript( json );
 	   	
		}
		catch( JSONException e)
		{
			// No message to the user is sent, JSON failed
	 	   	Log.e(ME + ":onRegisterd", "JSON exception");
		}
    };

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.v(ME + ":onMessage", "Message: Fantastic!!!");
        // Extract the payload from the message
        Bundle extras = intent.getExtras();
        if (extras != null) {
        	try
    		{
				Log.v(ME + ":onMessage extras ", extras.getString("message"));

        		JSONObject json;
	        	json = new JSONObject().put("event", "message");

	        	
	        	// My application on my host server sends back to "EXTRAS" variables msg and msgcnt
	        	// Depending on how you build your server app you can specify what variables you want to send
	        	//
				json.put("msg", extras.getString("message"));
				json.put("msgcnt", extras.getString("msgcnt"));
	
				Log.v(ME + ":onMessage ", json.toString());
				
				C2DMPlugin.sendJavascript( json );
	            // Send the MESSAGE to the Javascript application
    		}
    		catch( JSONException e)
    		{
    	 	   	Log.e(ME + ":onMessage", "JSON exception");
    		}        	
        }
    }

    @Override
    public void onError(Context context, String errorId) {
    	try
		{
    		JSONObject json;
        	json = new JSONObject().put("event", "error");

        	
        	// My application on my host server sends back to "EXTRAS" variables msg and msgcnt
        	// Depending on how you build your server app you can specify what variables you want to send
        	//
			json.put("msg", errorId);

			Log.e(ME + ":onError ", json.toString());
			
			C2DMPlugin.sendJavascript( json );
            // Send the MESSAGE to the Javascript application
		}
		catch( JSONException e)
		{
	 	   	Log.e(ME + ":onMessage", "JSON exception");
		}        	
    	
    }
    
    
}