package com.example.taxiexpress;

import android.os.Parcel;
import android.os.Parcelable;

public class Driver implements Parcelable {

    private String email;
    private String user_id;
    private String username;
    private  String name;
    private String phone;
    private String password;
    private int type;

    public Driver(String email, String user_id, String username, String name, String phone, String password, int type) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.type = type;
    }

    public Driver() {

    }

    protected Driver(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        name = in.readString();
        phone = in.readString();
        password = in.readString();
        type =in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeInt(type);
    }
}