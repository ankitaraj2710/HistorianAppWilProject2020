package com.example.historian;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    public TextView welcometextview;
    public TextView nametextview;
    public EditText editTextName;

    public TextView cardnumbertextview;
    public EditText editTextcardnumber;

    public TextView cvvtextview;
    public EditText editTextcvv;

    public TextView expirydatetextview;
    public EditText editTextexpirydate;
    public ImageButton calimageView;

    public Button Savebutton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);


        welcometextview = (TextView) findViewById(R.id.welcometextview);
        nametextview = (TextView) findViewById(R.id.nametextview);
        editTextName = (EditText) findViewById(R.id.editTextName);

        cardnumbertextview = (TextView) findViewById(R.id.cardnumbertextview);
        editTextcardnumber = (EditText) findViewById(R.id.editTextcardnumber);

        cvvtextview = (TextView) findViewById(R.id.cvvtextview);
        editTextcvv = (EditText) findViewById(R.id.editTextcvv);

        expirydatetextview = (TextView) findViewById(R.id.expirydatetextview);
        editTextexpirydate = (EditText) findViewById(R.id.editTextexpirydate);
        calimageView = (ImageButton) findViewById(R.id.calimageView);


        Savebutton =  (Button) findViewById(R.id.Savebutton);




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