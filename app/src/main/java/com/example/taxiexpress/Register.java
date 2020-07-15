package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
 ImageButton register;
 TextView pw,uname,fname,email,phone;
 String spw,suname,sfname,semail,sphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (ImageButton) findViewById(R.id.register);
        pw = (TextView) findViewById(R.id.rPhoneN);
        uname = (TextView) findViewById(R.id.uname);
        fname = (TextView) findViewById(R.id.fname);
        email = (TextView) findViewById(R.id.rEmail);
        phone = (TextView) findViewById(R.id.rPhoneN);
        spw = pw.getText().toString();
        suname = uname.getText().toString();
        sfname = fname.getText().toString();
        semail = email.getText().toString();
        sphone = phone.getText().toString();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spw.equals("") || suname.equals("") || sphone.equals("") || semail.equals("") || sfname.equals("")){
                    incorrect();
                }else{
                    verification(sphone);
                }
            }
        });
    }
    public void incorrect(){
        Toast.makeText(this,"Please fill in the blank areas", Toast.LENGTH_SHORT).show();
    }
    public void verification(String sphone){
        Intent welcome1 = new Intent(Register.this,Verification.class);
        startActivity(welcome1);
        finish();
    }
}
