package com.apps.my.appointmate_server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rahul on 25/11/16.
 */
public class LinkAmbulance{
    public String TAG="linkambulance";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("driver");

    public void assignAmbulance(String latitude,String longitude){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //DS = dataSnapshot.getChildrenCount();
                Log.d(TAG, "Value is: " + dataSnapshot.getChildrenCount() );

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String latitudeDriver=ds.child("latitude").getValue().toString();
                    String longitudeDriver=ds.child("longitude").getValue().toString();
                    Log.d(TAG, "onDataChange() called with: " + "latitude = [" + latitudeDriver + "]"+ "longitude = [" + longitudeDriver + "]");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Log.d(TAG, "onCancelled() called with: " + "error = [" + error.getDetails() + "]"  + "error = [" + error.getMessage() + "]");
            }
        });
    }


}
