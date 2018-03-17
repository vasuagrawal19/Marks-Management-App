package com.example.vasu.practical7;

import android.Manifest;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    EditText edRoll,ph,msg;
    Button btnSend,btnget;
    Cursor cursor=null,cursor1=null;
    String detail;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btnSend=(Button)findViewById(R.id.btnSend);
        btnget = (Button)findViewById(R.id.button2);
        edRoll=(EditText)findViewById(R.id.smsroll);
        ph=(EditText)findViewById(R.id.editText6);
        msg = (EditText)findViewById(R.id.editText7);
        mydb=new DBHelper(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)throws SecurityException {

                sendSms();
                clear();
                Toast.makeText(Main3Activity.this,"SMS sent...",Toast.LENGTH_LONG).show();
            }
        });

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int roll = Integer.parseInt(edRoll.getText().toString());
                cursor = mydb.getStudentData(roll);//rawQuery("SELECT * FROM Student where roll = "+roll+";", null);

                if(cursor.moveToNext())
                {
                    ph.setText(cursor.getString(cursor.getColumnIndex("Number")));

                    cursor1 = mydb.getAcademicData(roll);//rawQuery("SELECT Student.roll,name,sub,m1,m2 FROM Student INNER JOIN Academy ON Student.roll = "+roll+ ";", null);
                    if(cursor1.moveToNext())
                    {
                        int t1marks=0,t2marks=0;
                        int total=0;
                        t1marks = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("Marks1")));
                        t2marks = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("Marks2")));
                        total = t1marks + t2marks;
                        detail = cursor.getString(cursor.getColumnIndex("Name")) + "\nRoll : " + roll + "\nSub : " + cursor1.getString(cursor1.getColumnIndex("Subject")) + "\nMarks 1 : " + t1marks + "\nMarks 2 : " + t2marks;
                        msg.setText(detail);
                        // Toast.makeText(SMSActivity.this,detail,Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(Main3Activity.this,"Student Not Exist...",Toast.LENGTH_LONG).show();
                mydb.close();
            }
        });
    }

    private void clear() {
        edRoll.setText("");
        ph.setText("");
    }

    private void sendSms() {
        SmsManager mn=SmsManager.getDefault();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        mn.sendTextMessage(ph.getText().toString(),null,msg.getText().toString(),null,null);
        Log.e("sms","SMS Sent");
    }

}
