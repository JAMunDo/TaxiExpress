package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    ImageButton signin, register;
    EditText email,password;
    String emailLogin = "BroGad@gmail.com",passwordLogin = "Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        register = findViewById(R.id.register);
        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text1 = String.valueOf(email.getText());
                String text2 = String.valueOf(password.getText());
                if(!text1.equals(emailLogin) && !text2.equals(passwordLogin)){
                    incorrect();
                }else{
                    signUp();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }
    public void incorrect(){
        Toast.makeText(this,"Incorrect Email/Password provided please try again", Toast.LENGTH_SHORT).show();
    }
    public void signUp() {
        Intent welcome = new Intent(this,MapsActivity.class);
        startActivity(welcome);
        finish();
    }
    public void register(){
        Intent welcome1 = new Intent(Login.this,Register.class);
        startActivity(welcome1);
        finish();
    }
}
