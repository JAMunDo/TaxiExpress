package com.example.taxiexpress;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener, DialogMessage.DialogMessageListener {
    public static final String Data = "TAXI.D.lat";
    public static final String Data1 ="TAXI.D.lng";
    public static final String Data2 = "TAXI.O.lat";
    public static final String Data3 = "TAXI.O.lng";
    SearchView searchView;
    Geocoder geocoder;
    HashMap<String, Double> hdest,hori;
    List<Object> dest = new ArrayList<>();
    List<Object> ori = new ArrayList<>();
    LatLng destination,origin;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button dialog,refresh,service;
    List<String> test = new ArrayList<>();
    List<String> lat = new ArrayList<>();
    List<String> lng = new ArrayList<>();
    String [] values = {"Romario","Kehli","Keisha","Daequan"};
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dialog = findViewById(R.id.dialog);
        refresh = findViewById(R.id.refresh);
        service = findViewById(R.id.Service);
       // searchView = findViewById(R.id.testsearch);
        listView = findViewById(R.id.testview);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> mMissionsList = new ArrayList<>();
                        List<String> test = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()) {
                                // User miss = document.toObject(User.class);
                                test.add(document.getString("Name")+" \n"+document.getString("Latitude")+" \n"+document.getString("Longitude"));
                                lat.add(document.getString("Latitude"));
                                lng.add(document.getString("Longitude"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                  /*  ListView mMissionsListView = (ListView) findViewById(R.id.missionList);
                    MissionsAdapter mMissionAdapter = new MissionsAdapter(this, mMissionsList);
                    mMissionsListView.setAdapter(mMissionAdapter);*/
                            Log.d("MissionActivity", "It reached the snapshot");
                        } else {
                            Log.d("MissionActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });
        db.collection("Requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> mMissionsList = new ArrayList<>();

                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult()) {
                       // User miss = document.toObject(User.class);
                        test.add(document.getString("name")+" \n"+document.getString("description"));
                        dest.add(document.get("destination"));
                        ori.add(document.get("origin"));
                        lat.add(document.getString("Latitude"));
                        lng.add(document.getString("Longitude"));
                       // miss.setPassword("");
                        //miss.setUser_id("");
                       // mMissionsList.add(miss);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    Log.d("Test","Value: "+ test);
                    Log.d("MissionActivity", "It reached the snapshot");
                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity2.this,"You choose" + test.get(position),Toast.LENGTH_SHORT).show();
                hori =  (HashMap<String, Double>) ori.get(position);
                hdest = (HashMap<String, Double>) dest.get(position);
                destination = new LatLng(hdest.get("lat"),hdest.get("lng"));
                origin = new LatLng(hori.get("lat"),hori.get("lng"));
                String longitude = lng.get(position) ;
                String latitude = lat.get(position) ;
                openDialog();

            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), RequestService.class));

            }
        });

    }

    private void openDialog() {
        DialogMessage dialog = new DialogMessage();
        dialog.show(getSupportFragmentManager(),"Test");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (list.contains(query)) {
            adapter.getFilter().filter(query);
        } else {
            Toast.makeText(MainActivity2.this, "No Match found", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onYesClicked() {
        Toast.makeText(MainActivity2.this, "It works", Toast.LENGTH_LONG).show();
        Log.d("MissionActivity", "Yess clicked works");
        DocumentReference Ref = db.collection("Requests").document("0d4BMBkQwEVHuPH057sv");

// Set the "isCapital" field of the city 'DC'
        Ref
                .update("state", 2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Apache", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Apache", "Error updating document", e);
                    }
                });
        Intent profile = new Intent(MainActivity2.this, com.example.taxiexpress.Map.class);
       profile.putExtra(Data, String.valueOf(destination.lat));
       profile.putExtra(Data1, String.valueOf(destination.lng));
        profile.putExtra(Data2, String.valueOf(origin.lat));
        profile.putExtra(Data3, String.valueOf(origin.lng));
        startActivity(profile);
        // Add a new document with a generated id.
      /*  Map<String, Object> data = new HashMap<>();
        data.put("description", "Tokyo");
        data.put("destination1", "18.005418");
        data.put("destination2", "-76.741962");
        data.put("location1", "18.011042");
        data.put("location2", "-76.796428");
        data.put("name", "Tokyo");


        db.collection("Requests")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });*/

    }
}