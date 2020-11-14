package com.example.taxiexpress;

import android.content.Intent;
import android.location.Address;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dialog.RequestMessage;
import services.RequestService;

public class MainActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener, RequestMessage.DialogMessageListener {
    public static final String Data = "TAXI.D.lat";
    public static final String Data1 ="TAXI.D.lng";
    public static final String Data2 = "TAXI.O.lat";
    public static final String Data3 = "TAXI.O.lng";
    SearchView searchView;
    String fireid;
    HashMap<String, Double> hdest,hori;
    List<String> documentId = new ArrayList<>();
    List<Object> dest = new ArrayList<>();
    List<Object> ori = new ArrayList<>();
    LatLng destination,origin;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button dialog,refresh,service;
    List<String> test = new ArrayList<>();
    List<Address> geoOrigin = new ArrayList<>();
    List<Address> geoDest = new ArrayList<>();
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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
                        Geocoder geocoder =  new Geocoder(MainActivity2.this);
                        test.clear();
                        if (user != null) {
                            int i=0;
                            if(task.isSuccessful()){
                                for(DocumentSnapshot document : task.getResult()) {
                                    if(Objects.requireNonNull(user.getEmail()).contentEquals(Objects.requireNonNull(document.getString("taxiId")))) {
                                        try {
                                            dest.add(document.get("destination"));
                                            ori.add(document.get("origin"));
                                            hori =  (HashMap<String, Double>) ori.get(i);
                                            hdest = (HashMap<String, Double>) dest.get(i);

                                            geoOrigin = geocoder.getFromLocation(hori.get("lat"),hori.get("lng"),3);
                                            geoDest = geocoder.getFromLocation(hdest.get("lat"),hdest.get("lng"),3);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        test.add(document.getString("name") + " \n"+
                                                "Pickup:"+geoOrigin.get(i).getFeatureName()+
                                                "\nDropoff:"+geoDest.get(i).getFeatureName());

                                        i++;
                                    }
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                                adapter.notifyDataSetChanged();
                                listView.setAdapter(adapter);
                                Log.d("MissionActivity", "It reached the snapshot");
                            } else {
                                Log.d("MissionActivity", "Error getting documents: ", task.getException());
                            }
                        } else {
                            Log.d("Firebase","No information provided");
                        }

                    }
                });
            }
        });
        db.collection("Requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Geocoder geocoder =  new Geocoder(MainActivity2.this);
                if (user != null) {
                    Log.d("Firebase","User: "+user);
                    int i=0;

                    if(task.isSuccessful()){
                        for(DocumentSnapshot document : task.getResult()) {
                            if(Objects.requireNonNull(user.getEmail()).contentEquals(Objects.requireNonNull(document.getString("taxiId")))) {
                                try {
                                dest.add(document.get("destination"));
                                ori.add(document.get("origin"));
                                documentId.add(document.getId());
                                hori =  (HashMap<String, Double>) ori.get(i);
                                hdest = (HashMap<String, Double>) dest.get(i);

                                    geoOrigin = geocoder.getFromLocation(hori.get("lat"),hori.get("lng"),3);
                                    geoDest = geocoder.getFromLocation(hdest.get("lat"),hdest.get("lng"),3);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                test.add(document.getString("name") + " \n"+
                                        "Pickup:"+geoOrigin.get(i).getFeatureName()+
                                        "\nDropoff:"+geoDest.get(i).getFeatureName());
                                i++;
                            }
                        }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    Log.d("MissionActivity", "It reached the snapshot");
                    } else {
                        Log.d("MissionActivity", "Error getting documents: ", task.getException());
                    }
                } else {
                    Log.d("Firebase","No information provided");
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
                fireid = documentId.get(position);

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
        RequestMessage dialog = new RequestMessage();
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
    public void onYesClicked() {//Updates the request to let the customer kow it has been accepted
        Toast.makeText(MainActivity2.this, "It works", Toast.LENGTH_LONG).show();
        Log.d("MissionActivity", "Yess clicked works");
        DocumentReference Ref = db.collection("Requests").document(fireid);//Collection and document path
        Ref
                .update("state", 2)//firestore update call
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
    }
}