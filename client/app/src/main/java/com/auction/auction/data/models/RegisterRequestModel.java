package com.auction.auction.data.models;

public class RegisterRequestModel {
    public String username;
    public String password;

    public RegisterRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
