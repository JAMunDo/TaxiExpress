package com.example.taxiexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.taxiexpress.credentials.Login;
import com.example.taxiexpress.credentials.Register;
import com.example.taxiexpress.main.HomeScreen;
import com.example.taxiexpress.main.Routes;
import com.google.firebase.auth.FirebaseAuth;

public class Split extends AppCompatActivity{
    Button popupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        popupButton = findViewById(R.id.popup);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenuExample();
            }
        });
    }
    private void popupMenuExample() {
        PopupMenu p = new PopupMenu(Split.this, popupButton);
        p.getMenuInflater().inflate(R.menu.menu_navigation, p .getMenu());
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(Split.this,item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        p.show();
    }
}