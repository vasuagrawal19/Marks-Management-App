package com.example.vasu.practical7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name,roll,number;
    private Button submit,next;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.editText1);
        roll = (EditText) findViewById(R.id.editText2);
        number = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button1);
        next = (Button)findViewById(R.id.button3);
        mydb = new DBHelper(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Main2Activity.class);
                startActivity(i);
            }
        });
        submit.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        ArrayList<String> arrayList=new ArrayList<String>();

        mydb.insertStudent(name.getText().toString(),Integer.parseInt(roll.getText().toString()),number.getText().toString());
        Toast.makeText(getBaseContext(), "Data Submitted", Toast.LENGTH_SHORT).show();

        arrayList=mydb.getAllStudent();

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
}
