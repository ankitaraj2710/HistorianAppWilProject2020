package com.example.historian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DataBaseName = "PaymentDetail.db";
    public static final String TableName = "Payment_Detail";
    public static final String Table_Name = "User_Detail";
    public static final String Table_Name1 = "User_image";
    public static final String Table_Ticket = "Ticket_Detail";
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
    public static final String column0 = "UserImage";
    public static final String column01 = "id";
    public static final String column10 = "museumName";
    public static final String column11 = "noOfTickets";
    public static final String column12 = "date";
    public static final String column13 = "time";
    SQLiteDatabase db = this.getWritableDatabase();
    String dbId;


    public DataBaseHelper(Context context) {
        super(context, DataBaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Payment_Detail = "CREATE TABLE " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CardName TEXT,CardNumber INTEGER,CVV INTEGER,ExpiryDate INTEGER) ";


        String User_Detail = "CREATE TABLE " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,UserName TEXT,UserLastName TEXT,UserContact INTEGER,UserEmailID TEXT UNIQUE,UserDOB INTEGER)";

        String User_image = "CREATE TABLE " + Table_Name1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,userImage image) ";

        String Ticket_Detail = "CREATE TABLE " + Table_Ticket + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, museumName TEXT, noOfTickets INTEGER, date INTEGER, time INTEGER)";
        sqLiteDatabase.execSQL(Payment_Detail);
        sqLiteDatabase.execSQL(User_Detail);
        sqLiteDatabase.execSQL(User_image);
        sqLiteDatabase.execSQL(Ticket_Detail);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //create insert image method
    public  boolean insertImage(byte[] userImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues0 = new ContentValues();
        contentValues0.put(column0,userImage);
        db.insert(Table_Name1,null,contentValues0);
        return true;
    }
    // Create a method to retrieve image from database
    public Bitmap getImage(ImageView id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from " + Table_Name1 + " where id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            byte[] image = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }
     //Create insert method for user's profile

    public boolean insertData(String fname, String Lname, String contact, String  email, String DOB){

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(column1, fname);
        contentValues1.put(column2, Lname);
        contentValues1.put(column3, contact);
        contentValues1.put(column4, email);
        contentValues1.put(column5, DOB);

      db.insert(Table_Name,null,contentValues1);

        return false;
    }

    //Create insert method for payment detail
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

    // create method to insert ticket details in database
    public  boolean insertTicketDetail(String museumname, int noOftickets, String Date, String Time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(column10,museumname);
        contentValues4.put(column11,noOftickets);
        contentValues4.put(column12,Date);
        contentValues4.put(column13,Time);

        //insert data to database
        db.insert(Table_Ticket,null,contentValues4);
        return false;
    }
    //this an update method for updating details for user profile

    public boolean updateData(String firstName,String lastName,String contact,String emailID,String dob){
//        Cursor cursor = ViewData();
//           dbId = cursor.getString(0);
           dbId = "1";
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(column1, firstName);
            contentValues2.put(column2, lastName);
            contentValues2.put(column3, contact);
            contentValues2.put(column4, emailID);
            contentValues2.put(column5, dob);
            db.update(Table_Name, contentValues2, "ID = ?", new String[]{dbId});

        return true;
    }

    //this is an update method for payment details

    public boolean updatePaymentDetail(String cardName,String cardNumber,String cvv,String expiryDate){
        dbId="1";
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(column6, cardName);
        contentValues3.put(column7, cardNumber);
        contentValues3.put(column8,cvv);
        contentValues3.put(column9,expiryDate);
        db.update(TableName,contentValues3,"ID=?",new String[]{dbId});
        return true;
    }



    public void DisplayWElcomeName(TextView textView)
    {

      Cursor cr = this.getReadableDatabase().rawQuery("select * from "+ Table_Name+" where ID = 1",null);
     textView.setText("");
      while(cr.moveToNext()){
          textView.append(cr.getString(1));

      }
    }


    public Cursor ViewData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor =  db.rawQuery("select * from  "+Table_Name+" where ID = 1",null);

        return cursor;

    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TableName+" where ID = 1",null);
        return cursor;
    }

}
