package com.example.taxiexpress.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxiexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotP extends AppCompatActivity implements View.OnClickListener {

    Button forgotpassword;
    TextView email;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_p);
        forgotpassword = findViewById(R.id.resetp);
        email = findViewById(R.id.emailR);
        forgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String emailAddress = email.getText().toString();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Class ForgotP", "Email sent.");
                        }else{
                            Toast.makeText(ForgotP.this, "Email not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}