package com.example.dell.findbus;

/**
 * Created by DELL on 11/11/2017.
 */

public class Detail {
    private double latitude;
    private double longitude;
    private String contact;
    private String email;
    private String name;
    private String id;

    public Detail(double latitude, double longitude, String contact, String email, String name, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = contact;
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public Detail() {
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
