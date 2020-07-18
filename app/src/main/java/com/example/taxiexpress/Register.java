package com.example.taxiexpress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
 TextView pw,uname,fname,memail,phone;
 String spw,suname,sfname,semail,sphone;
    private FirebaseFirestore mDb;
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (ImageButton) findViewById(R.id.register);
        pw = (TextView) findViewById(R.id.rPassword);
        uname = (TextView) findViewById(R.id.uname);
        fname = (TextView) findViewById(R.id.fname);
        memail = (TextView) findViewById(R.id.rEmail);
        phone = (TextView) findViewById(R.id.rPhoneN);
        spw = pw.getText().toString();
        suname = uname.getText().toString();
        sfname = fname.getText().toString();
        semail = memail.getText().toString();
        sphone = phone.getText().toString();
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
     */
    public void registerNewEmail(final String email, final String password, final String name, final String phone, final String username){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //insert some default data
                            User user = new User();
                            user.setName(name);
                            user.setPhone(phone);
                            user.setEmail(email);
                            user.setUsername(username);
                            user.setPassword(password);
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

                        }
                        else {
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            Log.d(TAG, "onClick: attempting to register.");

            //check for null valued EditText fields
            if (!isEmpty(memail.getText().toString())
                    && !isEmpty(pw.getText().toString())) {

                //Initiate registration task
                registerNewEmail(memail.getText().toString(), pw.getText().toString(),uname.getText().toString(),fname.getText().toString(),phone.getText().toString());
            } else {
                Toast.makeText(Register.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }
}
