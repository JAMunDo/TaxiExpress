package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.logging.type.HttpRequest;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.android.volley.Request.Method.GET;

public class Distance extends AppCompatActivity  {
    // creating que queue object
    private RequestQueue queue;
    private GeoApiContext context;
    private Button button;
    private TextView text;
    private HttpRequest httpRequest;
    private static final String TAG = "HomeScreen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        button = findViewById(R.id.api);
        text = findViewById(R.id.test);

        queue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng taxi1 = new LatLng(18.006058, -76.741963);
                calculateDirections(taxi1);jsonParse();
            }
        });


    }

    private void jsonParse() {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=18.005684, -76.742005&destinations=18.011168, -76.795912&key=AIzaSyD0oWYV8aYcqUYCD2v9B75D-OkwNv_QKRw" ;
        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray rows = response.getJSONArray("rows");
                    JSONObject elements = rows.getJSONObject(0);
                    JSONArray distance = elements.getJSONArray("elements");
                    JSONObject distance2 = distance.getJSONObject(0);
                    JSONObject distance3 = distance2.getJSONObject("duration");
                    String time = distance3.getString("text");
                    text.setText(time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Distance.this, "Error occurred",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


    private void jsonParse2(){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyD0oWYV8aYcqUYCD2v9B75D-OkwNv_QKRw";
        JsonObjectRequest request = new JsonObjectRequest(GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray rows = response.getJSONArray("rows");
                    JSONObject elements = rows.getJSONObject(0);
                    JSONArray distance = elements.getJSONArray("elements");
                    JSONObject distance2 = distance.getJSONObject(0);
                    JSONObject distance3 = distance2.getJSONObject("duration");
                    String time = distance3.getString("text");
                    text.setText(time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Distance.this, "Panic mode activated",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
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

    private void direction(){
        DirectionsApiRequest directions = new DirectionsApiRequest(context);
    }
}