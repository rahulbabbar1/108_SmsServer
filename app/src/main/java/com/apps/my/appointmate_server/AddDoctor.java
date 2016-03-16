package com.apps.my.appointmate_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDoctor extends AppCompatActivity {
    Button add1;
    EditText name,sch;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        final DBHandler db = new DBHandler(this);
        add1=(Button)findViewById(R.id.button_add);
        name=(EditText)findViewById(R.id.name);
        sch=(EditText)findViewById(R.id.sch);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addSchedule(new Schedule(0, name.getText().toString(), sch.getText().toString()));
                Toast.makeText(getApplicationContext(),"Done",1000).show();
                name.setText("");
                sch.setText("");

            }
        });



    }

}
