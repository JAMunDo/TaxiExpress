package com.example.taxiexpress.credentials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.taxiexpress.Driver;
import com.example.taxiexpress.MainActivity2;
import com.example.taxiexpress.main.HomeScreen;
import com.example.taxiexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.example.taxiexpress.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

public class Register extends AppCompatActivity implements
        View.OnClickListener{
 ImageButton register;
 ToggleButton toggle ;//
 TextView pw,uname,fname,memail,phone;
 String spw,suname,sfname,semail,sphone;
 int type;
    private FirebaseFirestore mDb;
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        pw = findViewById(R.id.rPassword);
        uname = findViewById(R.id.uname);
        fname = findViewById(R.id.fname);
        memail = findViewById(R.id.rEmail);
        phone = findViewById(R.id.rPhoneN);
        spw = pw.getText().toString();
        register.setOnClickListener(this);
        mDb = FirebaseFirestore.getInstance();

    }
    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     * @param name
     * @param phone
     * @param username
     * @param type
     */
    public void registerNewEmail(final String email, final String password, final String name, final String phone, final String username,final int type){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            if(type==500){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //insert some default data
                            User user = new User();
                            user.setName(name);
                            user.setPhone(phone);
                            user.setEmail(email);
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setType(type);
                            user.setUser_id(FirebaseAuth.getInstance().getUid());

                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                    .build();
                            mDb.setFirestoreSettings(settings);

                            DocumentReference newUserRef = mDb
                                    .collection(getString(R.string.collection_users))
                                    .document(FirebaseAuth.getInstance().getUid());

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        redirectLoginScreen();
                                    }else{
                                        View parentLayout = findViewById(android.R.id.content);
                                        Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else if(type==600){
                                Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                                //insert some default data
                                Driver user = new Driver();
                                user.setName(name);
                                user.setPhone(phone);
                                user.setEmail(email);
                                user.setUsername(username);
                                user.setPassword(password);
                                user.setType(type);
                                user.setUser_id(FirebaseAuth.getInstance().getUid());

                                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                        .build();
                                mDb.setFirestoreSettings(settings);

                                DocumentReference newUserRef = mDb
                                        .collection(getString(R.string.collection_drivers))
                                        .document(FirebaseAuth.getInstance().getUid());

                                newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            redirectLoginScreen();
                                        }else{
                                            View parentLayout = findViewById(android.R.id.content);
                                            Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        else {
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }}
                });
    }

    @Override
    public void onClick(View view) {
        toggle= findViewById(R.id.toggleButton);
        if (view.getId() == R.id.register) {
            Log.d(TAG, "onClick: attempting to register.");

            //check for null valued EditText fields
            if (!isEmpty(memail.getText().toString())
                    && !isEmpty(pw.getText().toString())) {
                toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        // The toggle is enabled // set the value for the user if he is a driver
                        type= 600;
                    } else {
                        // The toggle is disabled // set the value for the user if he is a customer
                        type=500;
                    }
                });
                //Initiate registration task
                registerNewEmail(memail.getText().toString(), pw.getText().toString(),fname.getText().toString(),phone.getText().toString(),uname.getText().toString(),600);
                //redirectLoginScreen();
            } else {
                Toast.makeText(Register.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");
        Intent intent = new Intent(Register.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}
