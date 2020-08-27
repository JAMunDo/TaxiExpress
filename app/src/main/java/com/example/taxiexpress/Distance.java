package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Distance extends AppCompatActivity  {
    // creating que queue object
    private RequestQueue queue;
    private Button button;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        button = findViewById(R.id.api);
        text = findViewById(R.id.test);
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=18.005684, -76.742005&destinations=18.011168, -76.795912&key=AIzaSyD0oWYV8aYcqUYCD2v9B75D-OkwNv_QKRw" ;
        // initializing the queue object
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        text.setText("Response is: "+ response.substring(0,500));
                        Toast.makeText(Distance.this, response.substring(0,500), Toast.LENGTH_LONG).show();
                        Toast.makeText(Distance.this, "It passed the test area", Toast.LENGTH_LONG).show();
                       /* try {
                            JSONObject result = new JSONObject(response).getJSONObject("duration");
                            String time = result.getString("text");
                            JSONArray resultList = result.getJSONArray("item");
                            Toast.makeText(Distance.this, time , Toast.LENGTH_LONG).show();

                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(Distance.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(Distance.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
    }

}