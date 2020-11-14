package com.example.taxiexpress.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taxiexpress.MainActivity2;
import com.example.taxiexpress.User;
import com.example.taxiexpress.main.HomeScreen;
import com.example.taxiexpress.main.Profile;
import com.example.taxiexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class Login extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "Login";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView register,phone,forgot;
    ImageButton signin;
    EditText email,password;
    boolean cust = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                    db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot document : task.getResult()) {
                                    if(document.getString("email").contentEquals(user.getEmail())){
                                        cust = true;;
                                    }
                                }
                                Log.d("MissionActivity", "It reached the snapshot");
                            } else {
                                Log.d("MissionActivity", "Error getting documents: ", task.getException());
                            }
                        }
                    });
                    if(cust){
                        Intent profile = new Intent(Login.this, HomeScreen.class);
                        startActivity(profile);
                    }else{
                        Intent taxi = new Intent(Login.this, MainActivity2.class);
                        startActivity(taxi);
                    }
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
    //Sign in method
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
                               loginPathway();
                            }else{
                                Toast.makeText(Login.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(Login.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
    }
    //Bottom nav method
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivRegister:{
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
                break;
            }

            case R.id.signin:{
                signIn();
                break;
            }
            case R.id.ivphoneinstead:{
                Intent phonenumber = new Intent(Login.this, LoginPhone.class);
                startActivity(phonenumber);
                break;
            }
            case R.id.forgotpassword:{
                Intent forgot = new Intent(Login.this,ForgotP.class);
                startActivity(forgot);
                break;
            }
        }
    }
    //Method to decide if its a cust or driver
    private void loginPathway (){
        //user.getEmail();

        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult()) {
                        if(document.getString("email").contentEquals(email.getText().toString())){
                            cust = true;;
                        }
                    }
                    Log.d("MissionActivity", "It reached the snapshot");
                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
        if(cust){
            Intent profile = new Intent(Login.this, HomeScreen.class);
            startActivity(profile);
        }else{
            Intent taxi = new Intent(Login.this, MainActivity2.class);
            startActivity(taxi);
        }
    }
}
