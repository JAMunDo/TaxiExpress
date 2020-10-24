package com.example.taxiexpress;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.taxiexpress.main.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import directions.Url;

public class MainActivity2 extends AppCompatActivity implements SearchView.OnQueryTextListener, DialogMessage.DialogMessageListener {
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Button dialog,refresh;
    List<String> test = new ArrayList<>();
    String [] values = {"Romario","Kehli","Keisha","Daequan"};
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dialog = findViewById(R.id.dialog);
        refresh = findViewById(R.id.refresh);
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
                                test.add(document.getString("name")+" \n"+document.getString("description"));
                                // miss.setPassword("");
                                //miss.setUser_id("");
                                // mMissionsList.add(miss);
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
                       // miss.setPassword("");
                        //miss.setUser_id("");
                       // mMissionsList.add(miss);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,test);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
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
                openDialog();
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
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("description", "Tokyo");
        data.put("destination", "Japan");
        data.put("location", "Japan");
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
                });

    }
}