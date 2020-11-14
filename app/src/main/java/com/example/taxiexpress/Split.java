package com.example.taxiexpress;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


public class Split extends AppCompatActivity{
    Button popupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        popupButton = findViewById(R.id.popup);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                String channelId = "some_channel_id";
                CharSequence channelName = "Some Channel";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
                notif();
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void notif(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId = 1;
        String channelId = "some_channel_id";

        Notification notification = new Notification.Builder(Split.this,channelId)
                .setContentTitle("Some Message")
                .setContentText("You've received new messages!!")
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(notifyId, notification);
    }
}