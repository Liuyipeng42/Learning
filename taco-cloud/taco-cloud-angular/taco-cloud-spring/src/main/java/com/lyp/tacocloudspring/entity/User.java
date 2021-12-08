package com.lyp.tacocloudspring.entity;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User{

    private static final long serialVersionUID = 1L;

    private String id;

    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    public User() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.fullname = null;
        this.street = null;
        this.city = null;
        this.state = null;
        this.zip = null;
        this.phoneNumber = null;
    }

    public User(String id, String username, String password, String fullname, String street, String city, String state, String zip, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }



}
