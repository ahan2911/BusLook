package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import com.google.android.gms.location.places.Place;

public class BusStopList {
    private String placeId;
    private String busStopName;

    public BusStopList(String placeId, String busStopName) {
        this.placeId = placeId;
        this.busStopName = busStopName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getBusStopName() {
        return busStopName;
    }

    @Override
    public String toString() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }
}
