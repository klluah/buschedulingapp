package com.busschedulingap.busschedulingapp.schedule;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Schedule implements Serializable {
    @Exclude
    public String key;

    public String sbusname, sdestination, stime, sfare;

    public Schedule() { } // EMPTY PUBLIC CONSTRUCTOR TO ACCESS THE VARIABLES

    public Schedule(String sbusname, String sdestination, String stime, String sfare) { // CONSTRUCTOR THAT ACCEPTS ARGUMENTS
        this.sbusname = sbusname;
        this.sdestination = sdestination;
        this.stime = stime;
        this.sfare = sfare;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) { this.key = key; }

    public String getSbusname() {
        return sbusname;
    }

    public void setSbusname(String sbusname) {
        this.sbusname = sbusname;
    }

    public String getSdestination() {
        return sdestination;
    }

    public void setSdestination(String sdestination) {
        this.sdestination = sdestination;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSfare() {
        return sfare;
    }

    public void setSfare(String sfare) {
        this.sfare = sfare;
    }

}
