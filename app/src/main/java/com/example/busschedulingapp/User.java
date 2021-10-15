package com.example.busschedulingapp;

public class User { // CLASS TO SAVE AND STORE USER DATA BEFORE SENDING TO FIREBASE

    public String fullname, mobilenumber, email;

    public User() { // EMPTY PUBLIC CONSTRUCTOR TO ACCESS THE VARIABLES

    }

    public User(String fullname, String mobilenumber, String email) { // CONSTRUCTOR THAT ACCEPTS ARGUMENTS
        this.fullname = fullname;
        this.mobilenumber = mobilenumber;
        this.email = email;
    }
}
