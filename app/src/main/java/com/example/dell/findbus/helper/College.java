package com.example.dell.findbus.helper;

/**
 * Created by DELL on 11/12/2017.
 */

public class College {

    String id;
    String name;
    String email;
    String address;
    String contact;

    public College(String id, String name, String email, String address, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
    }

    public College() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }
}
