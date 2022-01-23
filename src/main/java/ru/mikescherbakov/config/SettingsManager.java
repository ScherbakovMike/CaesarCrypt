package ru.mikescherbakov.config;

import java.util.ResourceBundle;

public class SettingsManager {
    private static final ResourceBundle rb = ResourceBundle.getBundle("commonsettings");

    public static String getString(String key) {
        return rb.getString(key);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(rb.getString(key));
    }
}
