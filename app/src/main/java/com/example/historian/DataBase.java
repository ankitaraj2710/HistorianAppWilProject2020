package com.example.historian;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
     public static final String DataBase_Name = "Student.db";
     public static final  String Table_Name = "Student_Detail";

    public static final  String column1 = "Student_First_Name";
    public static final  String column2 = "Student_Last_Name";
    public static final  String column3 = "Student_Contact";
    public static final  String column4 = "Student_Email_ID";
    public static final  String column5 = "Student_DOB";



    public DataBase(Context context) {
        super(context, DataBase_Name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_Name+ "(Student_First_Name TEXT,Student_Last_Name TEXT,Student_Contact INTEGER,Student_Email_ID TEXT,Student_DOB INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
       onCreate(db);

    }
    public  boolean insertData(String firstName, String lastName, String contact, String email, String Dob)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1,firstName);
        contentValues.put(column2,lastName);
        contentValues.put(column3,contact);
        contentValues.put(column4,email);
        contentValues.put(column5,Dob);
          long result = db.insert(Table_Name,null,contentValues);
        if (result == -1)
        return false;
        else
            return true;
    }
}
