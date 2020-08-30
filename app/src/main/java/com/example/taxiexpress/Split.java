package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.taxiexpress.credentials.Login;
import com.example.taxiexpress.credentials.Register;
import com.example.taxiexpress.main.HomeScreen;
import com.example.taxiexpress.main.Routes;
import com.google.firebase.auth.FirebaseAuth;

public class Split extends AppCompatActivity implements View.OnClickListener  {
Button P,D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        P = findViewById(R.id.passenger);
        D = findViewById(R.id.driver);
        D.setOnClickListener(this);
        P.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passenger:
                Intent register = new Intent(Split.this, Register.class);
                startActivity(register);
                break;
            case R.id.routes:
                Intent registertaxi = new Intent(Split.this, Login.class);
                startActivity(registertaxi);
                break;
        }
    }
}