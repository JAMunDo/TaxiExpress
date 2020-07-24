package com.example.taxiexpress.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.taxiexpress.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import static com.example.taxiexpress.Constant.MAPVIEW_BUNDLE_KEY;

public class HomeScreen extends AppCompatActivity implements
        OnMapReadyCallback {
    private GoogleMap map;
    private MapView mapView;

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
        mapView = (MapView) findViewById(R.id.mMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng jamaica = new LatLng(18.005457, -76.741969);
        LatLng taxi1 = new LatLng(18.006058, -76.741963);
        map.addMarker(new MarkerOptions().position(jamaica).title("Mark Jacobs").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.taximarker)));
        map.addMarker(new MarkerOptions().position(taxi1).title("Bobby Brown").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.taximarker)));
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
        map.setMaxZoomPreference(92);
        // Zoom in, animating the camera.

        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(18.005801, -76.741950))
                .radius(120)
                .strokeColor(Color.LTGRAY));
        //.fillColor(Color.BLUE));
        Polyline line = map.addPolyline(new PolylineOptions()
                .add(new LatLng(18.005458, -76.741954),
                        new LatLng(18.005630, -76.741923),
                        new LatLng(18.006307, -76.741949),
                        new LatLng(18.007627, -76.742387),
                        new LatLng(18.012221, -76.742651),
                new LatLng(18.012993, -76.742609),
                new LatLng(18.014120, -76.742493),
                new LatLng(18.014305, -76.742572),
                new LatLng(18.014566, -76.742825),
                new LatLng(18.015332, -76.743025),
                new LatLng(18.015688, -76.744464),
                new LatLng(18.016755, -76.750349),
                        new LatLng(18.017066, -76.751855),
                        new LatLng(18.017542, -76.754584),
                        new LatLng(18.019075, -76.762434),
                        new LatLng(18.020349, -76.768435))
                .width(10)
                .color(Color.BLACK));
            line.setTag("83 Route");

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
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}