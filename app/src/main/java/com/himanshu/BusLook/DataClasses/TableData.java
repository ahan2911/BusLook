package com.himanshu.BusLook.DataClasses;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.himanshu.BusLook.com.himanshu.BusLook.Direction.GetBusesList;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.GetDetails;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Parser;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TableData {
    private String busNumber;
    private double lat, lang;
    private String place = "";
    private String distance;
    private String eta;

    public TableData(String busNumber, double lat, double lang) {
        this.busNumber = busNumber;
        this.lat = lat;
        this.lang = lang;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public String getPlace() {
        String location = "";
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=");
        sb.append(String.valueOf(lat));
        sb.append(",");
        sb.append(String.valueOf(lang));
        sb.append("&radius=1000&type=bus_station&key=AIzaSyB4RI2YTKf19dDjb6Qqoa4F8NGe3WnmCOg");
        Object[] object = new Object[1];
        object[0] = sb.toString();
        GetBusesList getBusesList = new GetBusesList();
        getBusesList.execute(object);
        try {
            Document document = getBusesList.get();
            Parser parser = new Parser(document);
            if(parser.getBusStop() != null)
                location = parser.getBusStop().get(0).getBusStopName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return location;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
