package com.artifacts.api.http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties CONFIG_PROPERTIES = new Properties();

    static {
        try (FileInputStream configProperties = new FileInputStream("config/config.properties")) {
            CONFIG_PROPERTIES.load(configProperties);
        } catch (IOException ioError) {
            throw new RuntimeException(ioError.getMessage());
        }
    }

    public static String getUsername() {
        return CONFIG_PROPERTIES.getProperty("api.username");
    }
    public static String getPassword() {
        return CONFIG_PROPERTIES.getProperty("api.password");
    }
    public static String getBaseUrl() {
        return CONFIG_PROPERTIES.getProperty("api.baseUrl");
    }
}
