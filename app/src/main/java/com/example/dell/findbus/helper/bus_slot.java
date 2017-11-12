package com.example.dell.findbus.helper;

/**
 * Created by root on 11/11/17.
 */
public class bus_slot {
    String time,driver_name,driver_contact,bus_no,from,to,note,driver_email,collegeId;

    public bus_slot(String time, String driver_name, String driver_contact, String bus_no,String from,String to,String note,String driver_email, String collegeId) {
        this.time = time;
        this.driver_name = driver_name;
        this.driver_contact = driver_contact;
        this.bus_no = bus_no;
        this.from=from;
        this.to=to;
        this.collegeId = collegeId;
        this.note=note;
        this.driver_email=driver_email;
    }

    public bus_slot() {
    }

    public String getTime() {
        return time;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getDriver_contact() {
        return driver_contact;
    }

    public String getBus_no() {
        return bus_no;
    }


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getNote() {
        return note;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public String getCollegeId() {
        return collegeId;
    }
}
