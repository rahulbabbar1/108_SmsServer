package com.apps.my.appointmate_server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button add,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHandler db = new DBHandler(this);
        boolean mboolean = false;

        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            // do the thing for the first time
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();

            db.addSchedule(new Schedule(1, "Dr. Saxena", "000000000000000000000000000000000000000000000000000000000000000000000000"));
            db.addSchedule(new Schedule(2,"Dr. Mehta", "000000000000000000000000000000000000000000000000000000000000000000000000"));
            db.addSchedule(new Schedule(3,"Dr. Sharma", "000000000000000000000000000000000000000000000000000000000000000000000000"));
            db.addSchedule(new Schedule(4, "Dr. Gupta", "000000000000000000000000000000000000000000000000000000000000000000000000"));
            db.addSchedule(new Schedule(5, "Dr. R. Babbar", "000000000000000000000000000000000000000000000000000000000000000000000000"));
            Toast.makeText(getApplicationContext(),"Welcome To Appointmate_Server",1000).show();

        } else {
            // other time your app loads
        }





        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddDoctor.class);


                startActivity(intent);

            }
        });

        update=(Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UpdateDoctor.class);

                startActivity(intent);

            }
        });


    }
}
