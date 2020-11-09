package com.example.taxiexpress;

import android.util.Log;
import android.widget.Toast;

import com.example.taxiexpress.main.HomeScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.model.LatLng;

import androidx.annotation.NonNull;

public class TaxiRequest {

    private String name;
    private String company;
    //private Timestamp time;
    private LatLng destination;
    private LatLng origin;
    private int state;
    private String taxiId; //Combination of Company and LPlate

    public TaxiRequest(String name, String company, LatLng destination, LatLng origin, int state, String taxiId) {
        this.name = name;
        this.company = company;
        this.destination = destination;
        this.origin = origin;
        this.state = state;
        this.taxiId = taxiId;
    }



    public TaxiRequest(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    protected boolean postTaxiRequest (TaxiRequest taxiRequest){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(taxiRequest != null) {
            db.collection("Requests")
                    .add(taxiRequest)
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
            return true;
        }else {
            Log.w("Failure", "Request is null");
            return false;
        }
    }

}
