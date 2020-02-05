package com.example.historian;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
     public static final String DataBase_Name = "UserDetail.db";
   //  public static final String DataBase_Name2 = "Payment.db";
     public static final  String Table_Name = "User_Detail";
     public static final  String Table_Name1 = "Payment_Detail";


    public static final  String column1 = "Student_First_Name";
    public static final  String column2 = "Student_Last_Name";
    public static final  String column3 = "Student_Contact";
    public static final  String column4 = "Student_Email_ID";
    public static final  String column5 = "Student_DOB";

    public static final  String column6 = "CardName";
    public static final  String column7 = "CardNumber";
    public static final  String column8 = "CVV";
    public static final  String column9 = "Expiry";




    public DataBase(Context context) {
        super(context, DataBase_Name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //to create a table
        String User_Detail = "CREATE TABLE " + Table_Name + "(Student_First_Name TEXT,Student_Last_Name TEXT,Student_Contact INTEGER,Student_Email_ID TEXT,Student_DOB INTEGER)";
        String User_Payment = "CREATE TABLE " + Table_Name1 + "(CardName TEXT,CardNumber INTEGER,CVV INTEGER,Expiry INTEGER)";
        db.execSQL(User_Detail);
        db.execSQL(User_Payment);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // to drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name1);
       onCreate(db);

    }
    public  boolean insertData(String firstName, String lastName, String contact, String email, String Dob)
    {
        // get writable Database
        SQLiteDatabase db = this.getWritableDatabase();
        //Create Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1,firstName);
        contentValues.put(column2,lastName);
        contentValues.put(column3,contact);
        contentValues.put(column4,email);
        contentValues.put(column5,Dob);
        db.insert(Table_Name,null,contentValues);
        return true;

    }


    public boolean insert(String cardNAme, String CardNum, String cvv, String date) {

        SQLiteDatabase db = this.getWritableDatabase();

        //create Content values
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(column6,cardNAme);
        contentValues1.put(column7,CardNum);
        contentValues1.put(column8,cvv);
        contentValues1.put(column9,date);
        //insert into database
        db.insert(Table_Name1,null,contentValues1);
//        if (result == -1)
//        return false;
//        else

        return false;
    }
}
