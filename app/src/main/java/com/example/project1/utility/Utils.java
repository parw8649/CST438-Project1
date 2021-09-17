package com.example.project1.Utility;

public class Utils {

    public static boolean verifyUsername(String username, String persistedUsername) {

        return username.equals(persistedUsername);
    }

    public static boolean verifyPassword(String password, String persistedPassword) {

        return password.equals(persistedPassword);
    }
}
