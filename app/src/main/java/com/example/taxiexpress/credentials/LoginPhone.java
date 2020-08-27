package com.example.taxiexpress.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxiexpress.R;
import com.example.taxiexpress.main.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginPhone extends AppCompatActivity implements  View.OnClickListener{
    private static final String TAG = "Login";

    //Firebase
    private FirebaseAuth mAuth;
    ImageView register;
    ImageButton signin,send;
    TextView phone,vCode;
    String phoneNumber,codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        mAuth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.phonenumbersignin);
        send = findViewById(R.id.sendcodeBtn);
        vCode = findViewById(R.id.verification);
        signin = findViewById(R.id.signinbtn);
        register = findViewById(R.id.ivRegister);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        send.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivRegister:{
                Intent register = new Intent(LoginPhone.this, Register.class);
                startActivity(register);
                break;
            }

            case R.id.signinbtn:{
                verifySignInCode();
                break;
            }
            case R.id.emailinstead:{
                Intent email = new Intent(LoginPhone.this, Login.class);
                startActivity(email);
                break;
            }
            case R.id.sendcodeBtn:{
                Log.d(TAG,"Send Code Button pressed");
                sendVerification();
                break;
            }
        }
    }
    private void verifySignInCode(){
        String code = vCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // here you can open a new activity
                            Toast.makeText(getApplicationContext(),"Verification Successful",Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(LoginPhone.this, HomeScreen.class);
                            startActivity(home);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"Verification Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private void sendVerification(){
        //format has to be  +1 888-999-9999
        phoneNumber ="+1 " + phone.getText().toString();
        if(phoneNumber.isEmpty()){
            phone.setError("Phone number is empty");
            phone.requestFocus();
            return;
        }
        if(phoneNumber.length()<10){
            phone.setError("Phone number is too short....Remember the area code");
            phone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG,"Verification code processed");
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(TAG,"Verification code not sent");
        }
        @Override
        public  void onCodeSent(String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken ){
            super.onCodeSent(s,forceResendingToken);
            Log.d(TAG,"Verification code sent");
            codesent = s;
        }
    };

}