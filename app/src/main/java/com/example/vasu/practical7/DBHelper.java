package com.example.vasu.practical7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by VASU on 10/24/2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int  DATABASE_VERSION=1;
    public static final String DATABASE_NAME = "db.txt";
    public static final String TABLE_NAME = "Student";
    public static final String A_TABLE_NAME = "Academic";
    public static final String NAME = "Name";
    public static final String ROLLNO = "Rollno";
    public static final String NUMBER = "Number";
    public static final String SEM = "Sem";
    public static final String SUBJECT = "Subject";
    public static final String MARKS1 = "Marks1";
    public static final String MARKS2 = "Marks2";
    public static final String S_ENTERIES ="create table "+TABLE_NAME +
            "("+ NAME+" text,"+ROLLNO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NUMBER+" text)";

    public static final String A_ENTERIES ="create table "+A_TABLE_NAME +
            "(" + ROLLNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SEM + " integer, " + SUBJECT +
            " text , " + MARKS1 + " integer, " + MARKS2 + " integer)";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(S_ENTERIES);
        db.execSQL(A_ENTERIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+ A_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertStudent(String Name,int roll,String Number)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Rollno",roll);
        contentValues.put("Number",Number);
        db.insert("Student",null,contentValues);
        db.close(); // Closing database connection
        return true;

    }

    public boolean insertAcademic(int roll,int sem, String subject,int m1, int m2)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Rollno",roll);
        contentValues.put("Sem",sem);
        contentValues.put("Subject",subject);
        contentValues.put("Marks1",m1);
        contentValues.put("Marks2",m2);
        db.insert("Academic",null,contentValues);
        db.close(); // Closing database connection
        return true;

    }

    //for display
    public Cursor getStudentData(int roll)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Student where " + ROLLNO + "="+roll+"",null);
        //db.close();
        return res;
    }
    public Cursor getAcademicData(int roll)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Academic where " + ROLLNO + "="+roll+"",null);
        //db.close();
        return res;
    }

    public boolean updateStudent(String Name,int roll,String Number)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Rollno",roll);
        contentValues.put("Number",Number);
        db.update("Student", contentValues, "rollno = ? ", new String[] { Integer.toString(roll) } );
        db.close();
        return true;
    }

    public void  deleteStudent(int  roll) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,
                ROLLNO + " = ? ",
                new String[] { Integer.toString(roll) });
        db.close();

    }

    public ArrayList<String> getAllStudent() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add("Name : "+res.getString(+res.getColumnIndex(NAME))+"\nRollno : "+res.getString(res.getColumnIndex(ROLLNO))+"\nNumber : "+res.getString(res.getColumnIndex(NUMBER))+"\n\n");

            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllAcademic() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+A_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add("Rollno : "+res.getString(res.getColumnIndex(ROLLNO))+"\nSem : "+res.getString(res.getColumnIndex(SEM))+ "\nSubject : "+res.getString(res.getColumnIndex(SUBJECT))
                    +"\nMarks 1 : "+res.getString(res.getColumnIndex(MARKS1))+"\nMarks 2 : " +res.getString(res.getColumnIndex(MARKS2))+"\n\n");

            res.moveToNext();
        }
        return array_list;
    }
}