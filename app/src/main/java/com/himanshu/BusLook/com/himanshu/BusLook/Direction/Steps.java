package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import java.io.Serializable;

public class Steps implements Serializable{
    private String busNumber;
    private String arrivalStop;
    private String departureStop;
    private String duration;
    private double distance;
    private String numStops;
    private String arrivalTime, departureTime;
    private double lat, lang;

    Steps(String busNumber, String arrivalStop, String departureStop, String duration, double distance, String numStops, String arrivalTime, String departureTime, double lat, double lang) {
        this.busNumber = busNumber;
        this.arrivalStop = arrivalStop;
        this.departureStop = departureStop;
        this.duration = duration;
        this.distance = distance;
        this.numStops = numStops;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.lat = lat;
        this.lang = lang;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(String arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    public String getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(String departureStop) {
        this.departureStop = departureStop;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void getDistance(double distance) {
        this.distance = distance;
    }

    public String getNumStops() {
        return numStops;
    }

    public void setNumStops(String numStops) {
        this.numStops = numStops;
    }

}
