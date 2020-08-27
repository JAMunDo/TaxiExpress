package com.example.taxiexpress.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.taxiexpress.R;
import com.example.taxiexpress.routes.RouteDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Routes extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ImageButton number83, number84;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    String [] values = {"Romario","Kehli","Keisha","Daequan"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        searchView = findViewById(R.id.Viewsearch);
        listView = findViewById(R.id.viewlist);
        /*number83 = findViewById(R.id.number83);
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
        });*/
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
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                overridePendingTransition(0,0);
                                return true;
                            case R.id.scan:
                                startActivity(new Intent(getApplicationContext(), Scan.class));
                                overridePendingTransition(0,0);
                                return true;
                        }
                        return false;
                    }
                });
                adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,values);
                listView.setAdapter(adapter);
                searchView.setOnQueryTextListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(Routes.this, values[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buttonNumber84() {
        String title = "83";
        Intent routes3 = new Intent(this, RouteDetails.class);
        routes3.putExtra("route",title);
        startActivity(routes3);
        finish();
    }

    public void buttonNumber83() {
        Intent routes2 = new Intent(this,RouteDetails.class);
        routes2.putExtra("title","83");
        startActivity(routes2);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}