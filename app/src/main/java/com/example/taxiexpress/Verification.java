package com.example.taxiexpress;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.taxiexpress.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Verification extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    ImageButton sendBtn,submit;
    EditText txtphoneNo;
    EditText code;
    String phoneNo,message,vcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        sendBtn = (ImageButton) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.phoneNumber);
        submit = (ImageButton) findViewById(R.id.submitCode);
        code = (EditText) findViewById(R.id.verificationCode);
        vcode = code.getText().toString();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vcode.equals("9865")){
                    incorrect();
                }else{
                    verified();
                }
            }
        });
    }

    protected void sendSMSMessage() {
        phoneNo = "8768682192";
        message = "9865";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Code sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    public void incorrect(){
        Toast.makeText(this,"Incorrect verification code provided please try again", Toast.LENGTH_SHORT).show();
    }
    public void verified(){
        Intent welcome1 = new Intent(Verification.this,Login.class);
        startActivity(welcome1);
        finish();
    }
}