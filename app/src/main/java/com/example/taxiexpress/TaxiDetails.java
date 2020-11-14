package com.example.taxiexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dialog.TaxiMessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taxiexpress.main.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxiDetails extends AppCompatActivity implements TaxiMessage.DialogMessageListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button dialog,refresh,service;
    String company,lplate;
    List<String> test = new ArrayList<>();
    List<String> plate = new ArrayList<>();
    List<String> cmp = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_details);
        listView = findViewById(R.id.taxilist);
        db.collection("Online").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> mMissionsList = new ArrayList<>();

                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult()) {
                        if(Objects.equals(document.getString("Status"), "0")) {
                            test.add("Name: " + document.getString("Name") +
                                    "\tCompany:" + document.getString("Company") +
                                    "\nID: " + document.getString("LPlate"));
                            plate.add(document.getString("LPlate"));
                            cmp.add(document.getString("Company"));
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    Log.d("TaxiDetails", "It reached the snapshot");
                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaxiDetails.this,"You chose " + test.get(position),Toast.LENGTH_SHORT).show();
                company = cmp.get(position) ;
                lplate = plate.get(position);
                openDialog();

             //   startActivity(profile);
            }
        });
    }

    private void openDialog() {
        TaxiMessage dialog = new TaxiMessage();
        dialog.show(getSupportFragmentManager(),"Test");
    }

    @Override
    public void onYesClicked() {
        Toast.makeText(TaxiDetails.this, "It works", Toast.LENGTH_LONG).show();
        Log.d("MissionActivity", "Yess clicked works");
        TaxiRequest taxiRequest = new TaxiRequest();
        Intent intent = getIntent();
        taxiRequest.setName(user.getUid());
        taxiRequest.setCompany(company);
        taxiRequest.setDestination(new LatLng(Double.parseDouble(intent.getStringExtra(HomeScreen.Unique4)),
                Double.parseDouble(intent.getStringExtra(HomeScreen.Unique3))));
        taxiRequest.setOrigin(new LatLng(Double.parseDouble(Objects.requireNonNull(intent.getStringExtra(HomeScreen.Unique2))),
                Double.parseDouble(Objects.requireNonNull(intent.getStringExtra(HomeScreen.Unique1)))));
        taxiRequest.setState(1);
        taxiRequest.setTaxiId(company+lplate);
        if(taxiRequest.postTaxiRequest(taxiRequest)){
            Toast.makeText(TaxiDetails.this, "Request sent", Toast.LENGTH_LONG).show();
            Log.d("TaxiRequest", "Request sent");
            Intent profile = new Intent(TaxiDetails.this, Success.class);
            startActivity(profile);
        }else{
            Toast.makeText(TaxiDetails.this, "Request failed", Toast.LENGTH_LONG).show();
            Log.d("TaxiRequest", "Request failed");
        }
    }
}