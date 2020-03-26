package com.example.historian;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SlyCalendarDialog.Callback {

    DataBaseHelper MyDataBase;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    int REQUEST_CAMERA = 1, SELECT_FILE = 0;

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
    String dbId;

    boolean isInserted;
    AwesomeValidation awesomeValidation;

    //Calendar

    Calendar myCalendar;
    Calendar startDate;
    Calendar endDate;
    Calendar startDateC;

    //datecheck type
    int start_or_end;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA);

    protected void onCreate(Bundle savedInstanceState) {


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        super.onCreate(savedInstanceState);
        MyDataBase = new DataBaseHelper(this);
        setContentView(R.layout.profile_page);

        welcometextview = (TextView) findViewById(R.id.welcometextview);
        profilepicturetextview = (TextView) findViewById(R.id.profilepicturetextview);
        imageViewcamera = (ImageView) findViewById(R.id.imageViewcamera);

        //firstnametextview = (TextView) findViewById(R.id.firstnametextview);
        editTextfirstname = (EditText) findViewById(R.id.editTextfirstname);

        //lastnametextview = (TextView) findViewById(R.id.lastnametextview);
        editTextlastname = (EditText) findViewById(R.id.editTextlastname);

        //contacttextview = (TextView) findViewById(R.id.contacttextview);
        editTextcontact = (EditText) findViewById(R.id.editTextcontact);

        // emailidtextview = (TextView) findViewById(R.id.emailidtextview);
        editTextemailid = (EditText) findViewById(R.id.editTextemailid);

        // dobtextview = (TextView) findViewById(R.id.dobtextview);
        dobedittext = (EditText) findViewById(R.id.dobedittext);
        //imageViewcalendar = (ImageButton) findViewById(R.id.imageViewcalendar);
        Savebutton = (Button) findViewById(R.id.Savebutton);


        myCalendar = (Calendar) Calendar.getInstance();
        startDate = (Calendar) Calendar.getInstance();


        //FOR CALENDER VIEW
        dobedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile.this, "Onclick", Toast.LENGTH_SHORT).show();
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(profile.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });


        imageViewcamera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Request For Camera permission
                        if (ContextCompat.checkSelfPermission(profile.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(profile.this,
                                    new String[]{
                                            Manifest.permission.CAMERA
                                    },
                                    100);

                        }

                        byte[] UserImage = imageViewToByte(imageViewcamera);
                        // to open camera

                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");

                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image from");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent, intent});

                        startActivityForResult(chooserIntent, 100);
                        AddProfile(UserImage);
                    }

                    private void AddProfile(byte[] UserImage) {
                        MyDataBase.insertImage(UserImage);
                    }

                    private byte[] imageViewToByte(ImageView imageViewcamera) {
                        Bitmap bitmap = ((BitmapDrawable) imageViewcamera.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        return byteArray;
                    }
                });

        AddDetails();
        Validations();

    }

    private void Validations() {
        awesomeValidation.addValidation(profile.this, R.id.imageViewcamera, RegexTemplate.NOT_EMPTY, R.string.Picture_Error);
        awesomeValidation.addValidation(profile.this, R.id.editTextfirstname, "[a-zA-Z\\s]+", R.string.firstName_Error);
        awesomeValidation.addValidation(profile.this, R.id.editTextlastname, "[a-zA-Z\\s]+", R.string.LastName_Error);
        awesomeValidation.addValidation(profile.this, R.id.editTextemailid, Patterns.EMAIL_ADDRESS, R.string.Email_Error);
        awesomeValidation.addValidation(profile.this, R.id.editTextcontact, Patterns.PHONE, R.string.Contact_Error);
        awesomeValidation.addValidation(profile.this, R.id.dobedittext, RegexTemplate.NOT_EMPTY, R.string.DOB_Error);
    }

    //method for save button
    public void AddDetails() {
        Savebutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (awesomeValidation.validate()) {

                            MyDataBase.updateData(editTextfirstname.getText().toString(), editTextlastname.getText().toString(), editTextcontact.getText().toString(), editTextemailid.getText().toString(), dobedittext.getText().toString());
                            MyDataBase.DisplayWElcomeName(welcometextview);
                            Toast.makeText(profile.this, "Data updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(profile.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                //get image capture
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                //set captured image to imageView
                imageViewcamera.setImageBitmap(captureImage);
                Uri selectedImageUri = data.getData();
                imageViewcamera.setImageURI(selectedImageUri);
            }

        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stateSaved = savedInstanceState.getString("Saved_state");
        if(stateSaved==null){
            Toast.makeText(profile.this,"OnRestoreInstanceState:\n"+"NO state saved",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(profile.this,"OnRestoreInstanceState:\n"+"saved state="+stateSaved,Toast.LENGTH_LONG).show();
            editTextfirstname.setText(stateSaved);
            editTextlastname.setText(stateSaved);
            editTextcontact.setText(stateSaved);
            editTextemailid.setText(stateSaved);
            dobedittext.setText(stateSaved);

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String stateToSave = editTextfirstname.getText().toString();
        outState.putString("saved_state", stateToSave);
        Toast.makeText(profile.this,"onSaveInstanceState:\n"+"saved_state = "+stateToSave,Toast.LENGTH_LONG).show();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.viewrecord)
        {
            Cursor cursor = MyDataBase.ViewData();

            if (cursor.getCount() == 0) {
                ShowData("Error", "Nothing Found");
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext())
            {
                stringBuffer.append("ID: " + cursor.getString(0) + "\n");
                stringBuffer.append("First Name : " + cursor.getString(1) + "\n");
                stringBuffer.append("Last Name : " + cursor.getString(2) + "\n");
                stringBuffer.append("Contact : " + cursor.getString(3) + "\n");
                stringBuffer.append("Email : " + cursor.getString(4) + "\n");
                stringBuffer.append("DOB : " + cursor.getString(5) + "\n\n");
//                stringBuffer.append("ID" + cursor.getString(6)+"\n");
//                stringBuffer.append("Name" +cursor.getString(7)+"\n");
//                stringBuffer.append("CardNumber"+ cursor.getString(8)+"\n");
//                stringBuffer.append("CVV"+cursor.getString(9)+"\n");
//                stringBuffer.append("ExpiryDate"+cursor.getString(10)+"\n");
            }
            ShowData("Profile Data", stringBuffer.toString());
        }


        return super.onOptionsItemSelected(item);

    }

    public void ShowData(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.menupage)
        {
            Intent intent = new Intent(profile.this, MenuPage.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if(firstDate!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dobedittext.setText(dateFormat.format((firstDate.getTime())));
            //Toast.makeText(this,  dateFormat.format(firstDate.getTime()), Toast.LENGTH_SHORT).show();
        }
        //  Toast.makeText(Profile.this, firstDate.toString(), Toast.LENGTH_SHORT).show();


    }
}
