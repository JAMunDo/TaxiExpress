package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taxiexpress.main.Profile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.logging.type.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Queue;
import java.util.Scanner;

import static com.android.volley.Request.Method.GET;

public class Distance extends AppCompatActivity  {
    // creating que queue object
    private RequestQueue queue;
    private Button button;
    private TextView text;
    private HttpRequest httpRequest;
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
                jsonParse();
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
                Toast.makeText(Distance.this, "Panic mode activated",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


}