package com.auction.auction.utils;

public class ValidationUtils {
    public static boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z0-9._-]{3,}$");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}