package com.busschedulingap.busschedulingapp.reservation;

import com.google.firebase.database.Exclude;

public class Reservation {
    @Exclude
    public String key;
    public String rstatus, rdate, rname, rnumber, rbusname, rdestination, rdriver, ravailseat,
            rresid, rdeptime, rplatenumber, rfare;

    public Reservation() { } // EMPTY PUBLIC CONSTRUCTOR TO ACCESS THE VARIABLES

    public Reservation(String rstatus, String rdate, String rname, String rnumber, String rbusname, String rdestination, String rdriver,
                       String ravailseat, String rresid, String rdeptime, String rplatenumber, String rfare) { // CONSTRUCTOR THAT ACCEPTS ARGUMENTS
        this.rdate = rdate;
        this.rstatus = rstatus;
        this.rname = rname;
        this.rnumber = rnumber;
        this.rbusname = rbusname;
        this.rdestination = rdestination;
        this.rdriver = rdriver;
        this.ravailseat = ravailseat;
        this.rresid = rresid;
        this.rdeptime = rdeptime;
        this.rplatenumber = rplatenumber;
        this.rfare = rfare;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) { this.key = key; }

    public String getRdate() { return rdate; }

    public void setRdate(String rname) { this.rdate = rdate; }

    public String getRname() { return rname; }

    public void setRname(String rname) { this.rname = rname; }

    public String getRnumber() { return rnumber; }

    public void setRnumber(String rnumber) { this.rnumber = rnumber; }

    public String getRbusname() { return rbusname; }

    public void setRbusname(String rbusname) { this.rbusname = rbusname; }

    public String getRdestination() { return rdestination; }

    public void setRdestination(String rdestination) { this.rdestination = rdestination; }

    public String getRdriver() { return rdriver; }

    public void setRdriver(String rdriver) { this.rdriver = rdriver; }

    public String getRavailseat() { return ravailseat; }

    public void setRavailseat(String ravailseat) { this.ravailseat = ravailseat; }

    public String getRresid() { return rresid; }

    public void setRresid(String rresid) { this.rresid = rresid; }

    public String getRdeptime() { return rdeptime; }

    public void setRdeptime(String rdeptime) { this.rdeptime = rdeptime; }

    public String getRplatenumber() { return rplatenumber; }

    public void setRplatenumber(String rplatenumber) { this.rplatenumber = rplatenumber; }

    public String getRfare() { return rfare; }

    public void setRfare(String rfare) { this.rfare = rfare; }
}
