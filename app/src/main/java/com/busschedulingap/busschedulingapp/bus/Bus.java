package com.busschedulingap.busschedulingapp.bus;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Bus implements Serializable {
    @Exclude
    public String key;
    public String busname, availableseats, destination, arrivaltime,
            departuretime, driver, fare, platenumber;

    public Bus() {
    } // EMPTY PUBLIC CONSTRUCTOR TO ACCESS THE VARIABLES

    public Bus(String busname, String availableseats, String destination, String arrivaltime,
               String departuretime, String driver, String fare, String platenumber) { // CONSTRUCTOR THAT ACCEPTS ARGUMENTS
        this.busname = busname;
        this.availableseats = availableseats;
        this.destination = destination;
        this.arrivaltime = arrivaltime;
        this.departuretime = departuretime;
        this.driver = driver;
        this.fare = fare;
        this.platenumber = platenumber;
    }

    //              GETTER AND SETTER               //

    public String getKey() {
        return key;
    }

    public void setKey(String key) { this.key = key; }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getAvailableseats() {
        return availableseats;
    }

    public void setAvailableseats(String availableseats) {
        this.availableseats = availableseats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }
}

