package com.himanshu.BusLook.DataClasses;

public class Bus {
    private String busNumber;
    private String engineNumber;
    private String chassisNumber;
    private String password;


    public Bus(String busNumber, String engineNumber, String chassisNumber, String password) {
        this.busNumber = busNumber;
        this.engineNumber = engineNumber;
        this.chassisNumber = chassisNumber;
        this.password = password;
    }
    public Bus(){

    }


    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
