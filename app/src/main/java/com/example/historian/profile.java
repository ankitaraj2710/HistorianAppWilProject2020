package com.example.historian;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class profile extends AppCompatActivity {

    DataBaseHelper MyDataBase;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public TextView welcometextview;
    public TextView profilepicturetextview;
    public ImageView imageViewcamera;

    public TextView firstnametextview;
    public EditText editTextfirstname;

    public TextView lastnametextview;
    public EditText editTextlastname;

    public TextView contacttextview;
    public EditText editTextcontact;

    public TextView emailidtextview;
    public EditText editTextemailid;

    public TextView dobtextview;
    public EditText dobedittext;
    public ImageButton imageViewcalendar;
    public Button Savebutton;
    boolean isInserted;

    //Calendar

    Calendar myCalendar;
    Calendar startDate;
    Calendar endDate;
    Calendar startDateC;

    //datecheck type
    int start_or_end;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA);

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MyDataBase = new DataBaseHelper(this);
        setContentView(R.layout.profile_page);

        welcometextview = (TextView) findViewById(R.id.welcometextview);
        profilepicturetextview = (TextView) findViewById(R.id.profilepicturetextview);
        imageViewcamera = (ImageView) findViewById(R.id.imageViewcamera);

        firstnametextview = (TextView) findViewById(R.id.firstnametextview);
        editTextfirstname = (EditText) findViewById(R.id.editTextfirstname);

        lastnametextview = (TextView) findViewById(R.id.lastnametextview);
        editTextlastname = (EditText) findViewById(R.id.editTextlastname);

        contacttextview = (TextView) findViewById(R.id.contacttextview);
        editTextcontact = (EditText) findViewById(R.id.editTextcontact);

        emailidtextview = (TextView) findViewById(R.id.emailidtextview);
        editTextemailid = (EditText) findViewById(R.id.editTextemailid);

        dobtextview = (TextView) findViewById(R.id.dobtextview);
        dobedittext = (EditText) findViewById(R.id.dobedittext);
        imageViewcalendar = (ImageButton) findViewById(R.id.imageViewcalendar);
        Savebutton = (Button) findViewById(R.id.SaveDetail);


        myCalendar = (Calendar) Calendar.getInstance();
        startDate = (Calendar) Calendar.getInstance();
        imageViewcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        if (start_or_end == 1) {
                            startDate.set(Calendar.YEAR, year);
                            startDate.set(Calendar.MONTH, month);
                            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            dobedittext.setText(sdf.format(myCalendar.getTime()));
                        } else {

                        }

                    }
                };


                dobedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            start_or_end = 1;
                            DatePickerDialog dialog = new DatePickerDialog(profile.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                            dialog.show();

                        } else {

                        }
                    }
                });


            }
        });


        //Request For Camera permission
        if (ContextCompat.checkSelfPermission(profile.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(profile.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);

        }
        imageViewcamera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byte[] UserImage = imageViewToByte(imageViewcamera);
                        // to open camera
                      
                        AddProfile(UserImage);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 100);
                    }

                    private void AddProfile(byte[] UserImage) {
                        MyDataBase.insertImage(UserImage);
                    }

                    private byte[] imageViewToByte(ImageView imageViewcamera)
                    {
                      Bitmap bitmap = ((BitmapDrawable) imageViewcamera.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,256,stream);
                        byte[] byteArray = stream.toByteArray();
                        return byteArray;
                    }
                });

        AddDetails();


    }


    //method for save button
    public void AddDetails() {
        Savebutton.setOnClickListener(
                 new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyDataBase.insertData(editTextfirstname.getText().toString(), editTextlastname.getText().toString(), editTextcontact.getText().toString(), editTextemailid.getText().toString(), dobedittext.getText().toString());

                        if (isInserted = true)
                            Toast.makeText(profile.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(profile.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            //get image capture
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set captured image to imageView
            imageViewcamera.setImageBitmap(captureImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
