package com.example.taxiexpress.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import directions.TaskCallback;
import directions.Url;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.taxiexpress.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import java.util.List;

import static com.example.taxiexpress.Constant.MAPVIEW_BUNDLE_KEY;

public class HomeScreen extends AppCompatActivity implements
        OnMapReadyCallback, SearchView.OnQueryTextListener, TaskCallback {
    private static final String TAG = "HomeScreen";
    private GoogleMap map;
    private MapView mapView;
    private TextView distance;
    private SearchView searchView;
    private SupportMapFragment mapFragment;
    private GeoApiContext context;
    private LatLng user;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Polyline currentPolyline;
    private MarkerOptions place1, place2;
    Button getDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
         place1 = new MarkerOptions().position(new LatLng(18.005418, -76.741962));
         place2 = new MarkerOptions().position(new LatLng(18.011042, -76.796428)).title("Location 2");
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
        searchView = findViewById(R.id.locator);
        searchView.setOnQueryTextListener(this);
        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Url(HomeScreen.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        distance= findViewById(R.id.distance);
        map.addMarker(place1);
        map.addMarker(place2);
        Location location = new Location("");
        Location taxilocation = new Location("");
        taxilocation.setLatitude(18.016755 );
        taxilocation.setLongitude(-76.750349);
        location.getLatitude();
        location.getLongitude();
        distance.setText("The taxi is "+ (location.distanceTo(taxilocation))/100000 +" away from your position");
        LatLng jamaica = new LatLng(18.005457, -76.741969);
        LatLng taxi1 = new LatLng(18.006058, -76.741963);
       // map.addMarker(new MarkerOptions().position(jamaica).title("Mark Jacobs").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.taximarker)));
       // map.addMarker(new MarkerOptions().position(taxi1).title("Bobby Brown").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.taximarker)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getMaxZoomLevel();
        map.setMaxZoomPreference(92);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        String location = searchView.getQuery().toString();
        List<Address> addressList = null;
        map.clear();
        if(location  !=null || !location.equals("") ){
            Geocoder geocoder = new Geocoder(HomeScreen.this);
            try{
                addressList = geocoder.getFromLocationName(location,1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            map.addMarker(new MarkerOptions().position(latLng).title(location));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        }
        return  false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void calculateDirections(LatLng latLng){
        Log.d(TAG, "calculateDirections: calculating directions.");
        LatLng destination = latLng;
        DirectionsApiRequest directions = new DirectionsApiRequest(context);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        18.005337, -76.741987
                )

        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(String.valueOf(destination)).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}