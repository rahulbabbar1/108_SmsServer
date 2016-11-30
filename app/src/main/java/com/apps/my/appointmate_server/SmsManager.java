package com.apps.my.appointmate_server;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RAHUL on 3/15/2016.
 */
public class SmsManager extends BroadcastReceiver {

    static String recievedMSG = null;
    static String latitude = null;
    static String longitude = null;
    static String TAG = "smsmanager";
    private LinkAmbulance linkAmbulance = new LinkAmbulance();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;

        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                Log.d(TAG, "onReceive() called with: " + "context = [" + context + "], intent = [" + intent + "]");
                if (msg.getOriginatingAddress().equals(Constants.serverNumber)) {
                    recievedMSG = msg.getDisplayMessageBody();
                    int firstClosBrac = recievedMSG.indexOf(']');
                    latitude = recievedMSG.substring(recievedMSG.indexOf('[')+1,firstClosBrac);
                    longitude = recievedMSG.substring(recievedMSG.indexOf('[',firstClosBrac)+1,recievedMSG.indexOf(']',firstClosBrac+1));
                    Log.d(TAG, "onReceive() called with: " + "latitude = [" + latitude + "], longitude = [" + longitude + "]");
                    linkAmbulance.assignAmbulance(latitude,longitude);
//                    Intent i=new Intent(context,LinkAmbulance.class);
//                    //i.putExtra("isFromSmsReceiver",true);
//                    i.putExtra("latitude", latitude);
//                    i.putExtra("longitude",longitude);
                   // context.startService(i);
                }

            }
        }
    }



}