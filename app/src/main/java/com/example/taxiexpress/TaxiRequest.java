package com.example.taxiexpress;

import com.google.firebase.Timestamp;
import com.google.maps.model.LatLng;

public class TaxiRequest {

    private String name;
    private Timestamp time;
    private LatLng destination;
    private LatLng origin;
    private int state;


    public TaxiRequest(String name, Timestamp time, LatLng destination, LatLng origin, int state) {
        this.name = name;
        this.time = time;
        this.destination = destination;
        this.origin = origin;
        this.state = state;
    }

    public TaxiRequest(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaxiRequest{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", destination=" + destination +
                ", origin=" + origin +
                ", state=" + state +
                '}';
    }
}
