package com.example.taxiexpress.main;

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

import com.example.taxiexpress.MapsActivity;
import com.example.taxiexpress.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Routes extends AppCompatActivity {
    ImageButton number83, number84;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
       // number83 = findViewById(R.id.number83);
        //number84 = findViewById(R.id.number84);
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Pineapple");
        list.add("Orange");
        list.add("Lychee");
        list.add("Gavava");
        list.add("Peech");
        list.add("Melon");
        list.add("Watermelon");
        list.add("Papaya");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(Routes.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
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
    }

    private void buttonNumber84() {
        Intent routes3 = new Intent(this, MapsActivity.class);
        startActivity(routes3);
        finish();
    }

    public void buttonNumber83() {
        Intent routes2 = new Intent(this,MapsActivity.class);
        startActivity(routes2);
        finish();
    }
}