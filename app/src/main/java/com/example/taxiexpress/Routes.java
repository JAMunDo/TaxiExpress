package com.example.taxiexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class Routes extends AppCompatActivity {
    ImageButton number83, number84;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        number83 = findViewById(R.id.number83);
        number84 = findViewById(R.id.number84);
        number83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber83();
            }
        });
        number84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber84();
            }
        });
        //Initialize and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Taxi selected
        bottomNavigationView.setSelectedItemId(R.id.routes);
                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.tracktaxi:
                                startActivity(new Intent(getApplicationContext(),
                                        HomeScreen.class));
                                overridePendingTransition(0,0);
                                return true;
                            case R.id.routes:
                                return true;
                            case R.id.profile:
                                startActivity(new Intent(getApplicationContext(),Profile.class));
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

    private void buttonNumber84() {
        Intent routes3 = new Intent(this,MapsActivity.class);
        startActivity(routes3);
        finish();
    }

    public void buttonNumber83() {
        Intent routes2 = new Intent(this,MapsActivity.class);
        startActivity(routes2);
        finish();
    }
}