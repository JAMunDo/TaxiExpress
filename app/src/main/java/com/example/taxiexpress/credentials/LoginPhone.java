package com.example.taxiexpress.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.taxiexpress.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPhone extends AppCompatActivity implements  View.OnClickListener{
    private static final String TAG = "Login";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    ImageView register,phone;
    ImageButton signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        signin = findViewById(R.id.signin);
        register = findViewById(R.id.ivRegister);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivRegister:{
                Intent register = new Intent(LoginPhone.this, Register.class);
                startActivity(register);
                break;
            }

            case R.id.signin:{
                //signIn();
                break;
            }
            case R.id.ivphoneinstead:{
                Intent phonenumber = new Intent(LoginPhone.this, Login.class);
                startActivity(phonenumber);
                break;
            }
        }
    }
}