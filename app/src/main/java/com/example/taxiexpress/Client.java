package com.example.taxiexpress;

import android.app.Application;

public class Client extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
