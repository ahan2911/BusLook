package com.himanshu.BusLook.DataClasses;

public class Bus_Journey {
    private String from;
    private String to;
    private String driverName;
    private String conductorName;
    private double lat;
    private double lang;
    private boolean status = false;
    private String busnumber;
    private String mobile;
    private String local_Bus_Number;

    public String getLocal_Bus_Number() {
        return local_Bus_Number;
    }

    public void setLocal_Bus_Number(String local_Bus_Number) {
        this.local_Bus_Number = local_Bus_Number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bus_Journey() {

    }

    public Bus_Journey(String busnumber, String from, String to, String driverName, String conductorName, String mobile, String local_Bus_Number) {
        this.busnumber = busnumber;
        this.from = from;
        this.to = to;
        this.driverName = driverName;
        this.conductorName = conductorName;
        this.mobile = mobile;
        this.local_Bus_Number = local_Bus_Number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getConductorName() {
        return conductorName;
    }

    public void setConductorName(String conductorName) {
        this.conductorName = conductorName;
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
}
