package com.example.taxiexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Initialize and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Taxi selected
        bottomNavigationView.setSelectedItemId(R.id.tracktaxi);
        //Perform Item Selected Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.tracktaxi:
                        return true;
                    case R.id.routes:
                        startActivity(new Intent(getApplicationContext(),Routes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(),Scan.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}