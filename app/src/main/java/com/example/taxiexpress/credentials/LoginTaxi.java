package com.example.taxiexpress.credentials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taxiexpress.MainActivity2;
import com.example.taxiexpress.R;
import com.example.taxiexpress.main.HomeScreen;
import com.example.taxiexpress.main.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

public class LoginTaxi extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "Login";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    ImageView register,phone,forgot;
    ImageButton signin;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        register = findViewById(R.id.ivRegister);
        phone = findViewById(R.id.ivphoneinstead);
        forgot = findViewById(R.id.forgotpassword);
        phone.setOnClickListener(this);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot.setOnClickListener(this);

        setupFirebaseAuth();
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(LoginTaxi.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginTaxi.this, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(){
        //check if the fields are filled out
        if(!isEmpty(email.getText().toString())
                && !isEmpty(password.getText().toString())){
            Log.d(TAG, "onClick: attempting to authenticate.");


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent profile = new Intent(LoginTaxi.this, MainActivity2.class);
                                startActivity(profile);
                            }else{
                                Toast.makeText(LoginTaxi.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginTaxi.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(LoginTaxi.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivRegister:{
                Intent register = new Intent(LoginTaxi.this, Register.class);
                startActivity(register);
                break;
            }

            case R.id.signin:{
                signIn();
                break;
            }
            case R.id.ivphoneinstead:{
                Intent phonenumber = new Intent(LoginTaxi.this, LoginPhone.class);
                startActivity(phonenumber);
                break;
            }
            case R.id.forgotpassword:{
                Intent forgot = new Intent(LoginTaxi.this,ForgotP.class);
                startActivity(forgot);
                break;
            }
        }
    }
}
