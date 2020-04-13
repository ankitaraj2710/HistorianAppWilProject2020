package com.example.historian;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class payment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SlyCalendarDialog.Callback {
    //DataBase
    DataBaseHelper MyDataBase1;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    public TextView welcometextview;
    public TextView nametextview;
    public EditText editTextName1;

    public TextView cardnumbertextview;
    public EditText editTextcardnumber1;

    public TextView cvvtextview;
    public EditText editTextcvv1;

    public TextView expirydatetextview;
    public EditText editTextexpirydate1;
    public ImageButton calimageView;

    public Button SaveDetail1;
    AwesomeValidation awesomeValidation;


    protected void onCreate(Bundle savedInstanceState) {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        super.onCreate(savedInstanceState);
        MyDataBase1 = new DataBaseHelper(this);
        setContentView(R.layout.payment_page);


        welcometextview = (TextView) findViewById(R.id.welcometextview);
        //nametextview = (TextView) findViewById(R.id.nametextview);
        editTextName1 = (EditText) findViewById(R.id.editTextName);

        // cardnumbertextview = (TextView) findViewById(R.id.cardnumbertextview);
        editTextcardnumber1 = (EditText) findViewById(R.id.editTextcardnumber);

        //cvvtextview = (TextView) findViewById(R.id.cvvtextview);
        editTextcvv1 = (EditText) findViewById(R.id.editTextcvv);

        //expirydatetextview = (TextView) findViewById(R.id.expirydatetextview);
        editTextexpirydate1 = (EditText) findViewById(R.id.editTextexpirydate);


        SaveDetail1 = (Button) findViewById(R.id.SaveDetail);


        //for calender view
        editTextexpirydate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(payment.this, "ONCLICK", Toast.LENGTH_SHORT).show();
                new SlyCalendarDialog()
                        .setSingle(true)
                        .setCallback(payment.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });
        AddPaymentDetail();
        CheckValidData();

    }

    private void CheckValidData() {
        awesomeValidation.addValidation(payment.this, R.id.editTextName, "[a-zA-Z\\s]+", R.string.NameOnCard_Error);
        awesomeValidation.addValidation(payment.this, R.id.editTextcardnumber, RegexTemplate.NOT_EMPTY, R.string.CardNumber_Error);
        awesomeValidation.addValidation(payment.this, R.id.editTextcvv, RegexTemplate.NOT_EMPTY, R.string.CVV_Error);
        awesomeValidation.addValidation(payment.this, R.id.editTextexpirydate, RegexTemplate.NOT_EMPTY, R.string.ExpiryDate_Error);

    }

    public void AddPaymentDetail() {

        SaveDetail1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (awesomeValidation.validate()) {
                            boolean isAdded = MyDataBase1.updatePaymentDetail(editTextName1.getText().toString(), editTextcardnumber1.getText().toString(), editTextcvv1.getText().toString(), editTextexpirydate1.getText().toString());
                            if (isAdded = true)
                                Toast.makeText(payment.this, "Data Updated", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(payment.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                        }


                    }


                }
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.menupage) {
            Intent intent = new Intent(payment.this, MenuPage.class);
            startActivity(intent);

            Toast.makeText(this, "This is Main Menu Page", Toast.LENGTH_SHORT).show();

        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.viewrecord)
        {
            Cursor cursor1 = MyDataBase1.getData();
            if (cursor1.getCount() == 0)
            {
                AppealData("Error","Nothing Found");
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor1.moveToNext())
            {
                stringBuffer.append("ID: " + cursor1.getString(0) + "\n");
                stringBuffer.append("CARDHOLDER NAME:" +cursor1.getString(1) + "\n");
                stringBuffer.append("CARD NUMBER:" +cursor1.getString(2) +"\n");
                stringBuffer.append("CVV:" + cursor1.getString(3) +"\n");
                stringBuffer.append("EXPIRY DATE:" +cursor1.getString(4) +"\n");
            }
            AppealData("Payment Data", stringBuffer.toString());

        }

        return super.onOptionsItemSelected(item);
    }

    public void AppealData(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

        if (firstDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
            editTextexpirydate1.setText(dateFormat.format((firstDate.getTime())));

        }
    }


}