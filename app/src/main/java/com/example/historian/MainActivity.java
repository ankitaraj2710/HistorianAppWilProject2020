package com.example.historian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback
{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public Button find;

    DataBaseHelper MyDataBase;


    //EditText firstname,contact,lastname,emailid,dob;
   // Button Save;
    GoogleMap map;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    // current Location
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    //Databae instance
    //DataBase MyDataBase;

    private  static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Call Constructer for database
        // MyDataBase = new DataBase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDataBase = new DataBaseHelper(this);
        find = (Button)findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchlastlocation();
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.ham);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        //Current Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchlastlocation();
        //call insert method from profile page
        MyDataBase.insertData("Ankitraj", "Kaur", "43722536", "ankita@gmail.com", "25/11/1994");

      MyDataBase.insertDetail("Ankitraj_Kaur","4523-3233-4674-4747","232","12/25");
    }


    private void fetchlastlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;

        }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentlocation = location;
                        // Toast.makeText(getApplicationContext(),currentlocation.getLatitude()+""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();
                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        supportMapFragment.getMapAsync(MainActivity.this);
                       }
                }
            });

        }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        if (id == R.id.menupage)
        {

//            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout,new MenuPage()).addToBackStack(null).commit();
            Intent intent = new Intent(MainActivity.this, MenuPage.class);
            startActivity(intent);
//
//            Toast.makeText(this, "This is Menu Page", Toast.LENGTH_SHORT).show();

        }


        drawerLayout.closeDrawer(GravityCompat.START);

        return false;

    }



//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//switch (menuItem.getItemId()){
//    case R.id.menupage:
//        getSupportFragmentManager().beginTransaction().replace(R.id.ham, new MenuPage()).addToBackStack(null).commit();
//        break;
//
//
////    case R.id.viewrecord:
////        getSupportFragmentManager().beginTransaction().replace(R.id.readData,new MainMenu()).addToBackStack(null).commit();
////         break;
////    c
////    ase R.id.Menu:
////        getSupportFragmentManager().beginTransaction().replace(R.id.ham, new menuPage()).commit();
////        break;
//
//}
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//
//    }
//    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);

//        map = googleMap;
//        LatLng Toronto = new LatLng(43.660063, -79.382873);
//        map.addMarker(new MarkerOptions().position(Toronto).title("Toronto"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Toronto));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    fetchlastlocation();
                }
                break;
        }
    }

}