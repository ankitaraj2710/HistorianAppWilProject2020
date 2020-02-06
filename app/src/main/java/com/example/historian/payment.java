package com.example.historian;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class payment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MyDataBase1 = new DataBaseHelper(this);
        setContentView(R.layout.payment_page);


        welcometextview = (TextView) findViewById(R.id.welcometextview);
        nametextview = (TextView) findViewById(R.id.nametextview);
        editTextName1 = (EditText) findViewById(R.id.editTextName);

        cardnumbertextview = (TextView) findViewById(R.id.cardnumbertextview);
        editTextcardnumber1 = (EditText) findViewById(R.id.editTextcardnumber);

        cvvtextview = (TextView) findViewById(R.id.cvvtextview);
        editTextcvv1 = (EditText) findViewById(R.id.editTextcvv);

        expirydatetextview = (TextView) findViewById(R.id.expirydatetextview);
        editTextexpirydate1 = (EditText) findViewById(R.id.editTextexpirydate);
        calimageView = (ImageButton) findViewById(R.id.calimageView);


        SaveDetail1 =  (Button) findViewById(R.id.SaveDetail);
        AddPaymentDetail();


    }

   public void AddPaymentDetail(){

        SaveDetail1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isAdded =  MyDataBase1.insertDetail(editTextName1.getText().toString(),editTextcardnumber1.getText().toString(),editTextcvv1.getText().toString(),editTextexpirydate1 .getText().toString());
                        if(isAdded = true)
                            Toast.makeText(payment.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(payment.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }

        );

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
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.menupage)
        {
            Intent intent = new Intent(payment.this, MainMenu.class);
            startActivity(intent);

            Toast.makeText(this, "This is Main Menu Page", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}