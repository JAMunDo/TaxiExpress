package com.example.taxiexpress.routes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.taxiexpress.R;
import com.example.taxiexpress.main.Profile;
import com.example.taxiexpress.main.Routes;
import com.example.taxiexpress.main.Scan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.taxiexpress.Constant.MAPVIEW_BUNDLE_KEY;

public class RouteDetails extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
    private MapView mapView;
    ArrayList<LatLng> arrayList=new ArrayList<LatLng>();
    LatLng taxi1 = new LatLng(18.006550, -76.742043);
    LatLng taxi2 = new LatLng(18.011214, -76.742646);
    LatLng taxi3 = new LatLng(18.015678, -76.744362);
    LatLng taxi4 = new LatLng(18.017353, -76.753531);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        //Initialize and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Taxi selected
        bottomNavigationView.setSelectedItemId(R.id.tracktaxi);
        //Perform Item Selected Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tracktaxi:
                        return true;
                    case R.id.routes:
                        startActivity(new Intent(getApplicationContext(), Routes.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), Scan.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = (MapView) findViewById(R.id.gMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        arrayList.add(taxi1);
        arrayList.add(taxi2);
        arrayList.add(taxi3);
        arrayList.add(taxi4);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Jamaica(UWI) and move the camera
        /*LatLng jamaica = new LatLng(18.005801, -76.741950);
        map.addMarker(new MarkerOptions().position(jamaica).title("Your Location"));
        map.moveCamera(CameraUpdateFactory.newLatLng());*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getMaxZoomLevel();
        map.setMaxZoomPreference(100);
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
        String title = getIntent().getStringExtra("title");
        mapDetails(title);


    }
    private void mapDetails(String title){

        switch (title) {
            case "83":
                map.addMarker(new MarkerOptions().position(arrayList.get(1)).title("taxi2"));
                map.addMarker(new MarkerOptions().position(arrayList.get(0)).title("taxi1"));
                map.addMarker(new MarkerOptions().position(arrayList.get(2)).title("taxi1"));
                map.addMarker(new MarkerOptions().position(arrayList.get(3)).title("taxi1"));
                break;
            case "84":
                break;
            case "85":
                break;
            case "86":
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
}