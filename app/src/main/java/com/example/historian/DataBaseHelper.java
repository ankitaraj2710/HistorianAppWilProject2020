package com.example.historian;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DataBaseName = "PaymentDetail.db";
    public static final String TableName = "Payment_Detail";
    public static final String Table_Name = "User_Detail";
    //public static final  String Table_Name1 = "Payment_Detail";

    public static final String column1 = "UserName";
    public static final String column2 = "UserLastName";
    public static final String column3 = "UserContact";
    public static final String column4 = "UserEmailID ";
    public static final String column5 = "UserDOB";
    public static final String column6 = "CardName";
    public static final String column7 = "CardNumber";
    public static final String column8 = "CVV";
    public static final String column9 = "ExpiryDate";

    public DataBaseHelper(Context context) {
        super(context, DataBaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Payment_Detail = "CREATE TABLE " + TableName + " (CardName TEXT,CardNumber INTEGER,CVV INTEGER,ExpiryDate INTEGER) ";


        String User_Detail = "CREATE TABLE " + Table_Name + "(UserName TEXT,UserLastName TEXT,UserContact INTEGER,UserEmailID TEXT,UserDOB INTEGER)";

        sqLiteDatabase.execSQL(Payment_Detail);
        sqLiteDatabase.execSQL(User_Detail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
//
//Create insert method

    public boolean insertData(String fname, String Lname, String contact, String  email, String DOB){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(column1, fname);
        contentValues1.put(column2, Lname);
        contentValues1.put(column3, contact);
        contentValues1.put(column4, email);
        contentValues1.put(column5, DOB);
        db.insert(Table_Name,null,contentValues1);
        return false;
    }



    //Create insert method
    public boolean insertDetail(String cardname, String cardnum, String cvv, String expirydate) {
        // get writable Database
        SQLiteDatabase db = this.getWritableDatabase();

        //Create Content Values
        ContentValues contentValues = new ContentValues();
        contentValues.put(column6, cardname);
        contentValues.put(column7, cardnum);
        contentValues.put(column8, cvv);
        contentValues.put(column9, expirydate);

        //Insert Data into Database
        db.insert(TableName, null, contentValues);
return  false;
    }

}
