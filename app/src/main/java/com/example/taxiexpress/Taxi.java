package com.example.taxiexpress;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Taxi implements ClusterItem {
    private final String username;
    private final LatLng latLng;

    public Taxi(String username, LatLng latLng) {
        this.username = username;
        this.latLng = latLng;
    }
    @Override
    public LatLng getPosition() {  // 1
        return latLng;
    }
    @Override
    public String getTitle() {  // 2
        return username;
    }
    @Override
    public String getSnippet() {
        return "";
    }
}