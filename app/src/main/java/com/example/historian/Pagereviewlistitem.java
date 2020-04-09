package com.example.historian;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class Pagereviewlistitem extends AppCompatActivity implements SlyCalendarDialog.Callback {

    public TextView aa_title;
    public TextView aa_rating;
    public TextView aa_type;
    public TextView aa_data;
    public ImageView aa_thumbnail;
    public AppBarLayout appbarlayout;
    public CollapsingToolbarLayout collapsinglayout;
    public Button booktickets_button;
    public TextView aa_hello;
    public RatingBar rating1;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagereview);

        // hide the default action bar
        getSupportActionBar().hide();

        //Recieve Data
        String name = getIntent().getExtras().getString("Museum_Name");
        String description = getIntent().getExtras().getString("Museum_Rating");
        String type = getIntent().getExtras().getString("Museum_Type");
        String img = getIntent().getExtras().getString("Museum_Image");
        String museumdata = getIntent().getExtras().getString("Museum_Data");


        aa_title = (TextView) findViewById(R.id.aa_title);
        aa_rating = (TextView) findViewById(R.id.aa_rating);
        aa_type = (TextView) findViewById(R.id.aa_type);
        aa_thumbnail = (ImageView) findViewById(R.id.aa_thumbnail);
        aa_data = (TextView) findViewById(R.id.aa_data);
        booktickets_button = (Button) findViewById(R.id.booktickets_button);
//        aa_hello = (TextView) findViewById(R.id.aa_hello);

        appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        collapsinglayout = (CollapsingToolbarLayout) findViewById(R.id.collapsinglayout);
        collapsinglayout.setTitleEnabled(true );

//        rating1 = (RatingBar) findViewById(R.id.rating1);

        // setting Values to each view

        aa_title.setText(name);
        aa_rating.setText(description);
        aa_type.setText(type);
        collapsinglayout.setTitle(name);
//        rating1.setNumStars(Integer.parseInt(description));

//        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        // set Image using glide
//        Glide.with(this).load(img).apply(requestOptions).into(aa_thumbnail);
        Glide.with(this).load(img).into(aa_thumbnail);


        // book ticket working
        // book ticket working
        booktickets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pagereviewlistitem.this,bookTicket.class);
                i.putExtra("Museum_Name",name);
                i.putExtra("Museum_Image",img);
//                intent.putExtra("latitude", letValueMain);

                startActivity(i);

            }
        });



//        dobedittext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Toast.makeText(ProfilePage.this, "ONCLICK", Toast.LENGTH_SHORT).show();
//                new SlyCalendarDialog()
//                        .setSingle(false)
//                        .setCallback(ProfilePage.this)
//                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
//
//            }
//        });

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

    }
}