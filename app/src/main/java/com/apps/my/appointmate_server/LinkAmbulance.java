package com.apps.my.appointmate_server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rahul on 25/11/16.
 */
public class LinkAmbulance{
    public String TAG="linkambulance";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("driver");
    public void assignAmbulance(final String latitude, final String longitude){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //DS = dataSnapshot.getChildrenCount();
                Log.d(TAG, "Value is: " + dataSnapshot.getChildrenCount() );
                Thread background = new Thread(new Runnable() {

                    // After call for background.start this run method call
                    public void run() {
                        int min=-1;
                        String driverId="";
                        String phoneNo="";
                        if(dataSnapshot.getChildren().iterator().hasNext()){
                            DataSnapshot dstemp =dataSnapshot.getChildren().iterator().next();
                            min = getDistance(dstemp,latitude,longitude);
                            driverId = dstemp.getKey().toString();
                            Log.d(TAG, "onDataChange() called with: " + "driverId = [" + driverId + "]");
                        }
                        else {
                            Log.d(TAG, "Error() no driver found called with: " + "dataSnapshot = [" + dataSnapshot + "]");
                        }
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            int temp =getDistance(ds,latitude,longitude);
                            if(temp<min){
                                temp=min;
                                driverId = ds.getKey().toString();
                            }
                            //min = min
                            //Log.d(TAG, "onDataChange() called with: " + "latitude = [" + latitudeDriver + "]"+ "longitude = [" + longitudeDriver + "]");
                        }
                        Log.d(TAG, "Final onDataChange() called with: " + "driverId = [" + driverId + "]"+ "min = [" + min + "]");
                        sendMessageToDriver(phoneNo);
                    }

                    private void threadMsg(String msg) {

                        if (!msg.equals(null) && !msg.equals("")) {
                            Message msgObj = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putString("message", msg);
                            msgObj.setData(b);
                            handler.sendMessage(msgObj);
                        }
                    }

                    // Define the Handler that receives messages from the thread and update the progress
                    private final Handler handler = new Handler() {

                        public void handleMessage(Message msg) {

                            String aResponse = msg.getData().getString("message");

                            if ((null != aResponse)) {

                                // ALERT MESSAGE

                            }
                            else
                            {

                                // ALERT MESSAGE

                            }

                        }
                    };

                });
                // Start Thread
                background.start();
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Log.d(TAG, "onCancelled() called with: " + "error = [" + error.getDetails() + "]"  + "error = [" + error.getMessage() + "]");
            }
        });
    }

    public int getDistance(DataSnapshot ds, String latitude,String longitude){
        Log.d(TAG, "getDistance() called with: " + "ds = [" + ds + "], latitude = [" + latitude + "], longitude = [" + longitude + "]");
        String latitudeDriver=ds.child("latitude").getValue().toString();
        String longitudeDriver=ds.child("longitude").getValue().toString();
        int dur=-1;
        try {
            JSONObject response = getJSONObjectFromURL(Constants.mapApiUrlA+latitude+"%2c"+longitude+Constants.mapApiUrlB+latitudeDriver+"%2c"+longitudeDriver+Constants.mapApiUrlC);
            Log.d(TAG, "getDistance() called with: " + "response = [" + response + "], latitude = [" + latitude + "], longitude = [" + longitude + "]");
            Log.d(TAG, "getDistance() called with: " + "ds = [" +  response.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value")
          + "], latitude = [" + latitude + "], longitude = [" + longitude + "]");
            dur= response.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dur;
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    public void sendMessageToDriver(String phoneNo){

    }
}
