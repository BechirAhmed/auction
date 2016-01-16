package com.auction.auction.models;

public class RegisterLoginRequestModel {
    public String username;
    public String password;

    public RegisterLoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
