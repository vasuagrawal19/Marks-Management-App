package com.example.vasu.practical7;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {



        private Button submit,sendsms;
        private EditText roll,sem,sub,m1,m2;
        DBHelper mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        submit = (Button) findViewById(R.id.button1);
        roll = (EditText) findViewById(R.id.editText1);
        sem = (EditText) findViewById(R.id.editText2);
        sub = (EditText) findViewById(R.id.editText3);
        m1 = (EditText) findViewById(R.id.editText4);
        m2 = (EditText) findViewById(R.id.editText5);
        mdb = new DBHelper(this);
        sendsms = (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdb.insertAcademic(Integer.parseInt(roll.getText().toString()),Integer.parseInt(sem.getText().toString()),sub.getText().toString(),Integer.parseInt(m1.getText().toString()),Integer.parseInt(m2.getText().toString()));
                Toast.makeText(getBaseContext(),"Data Submitted",Toast.LENGTH_SHORT).show();

                ArrayList<String> arrayList=new ArrayList<String>();

                arrayList=mdb.getAllAcademic();

                String st="";
                for(String s:arrayList)
                {
                    st += s;
                }
                if(st!="")
                    Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Nothing to Display", Toast.LENGTH_SHORT).show();

            }
        });

        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(i);
            }
        });

    }
}
