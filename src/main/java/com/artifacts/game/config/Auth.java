package com.artifacts.game.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Auth {
    public static final Properties AUTH = new Properties();

    static {
        try {
            var fileInputStream = new FileInputStream("config/config.properties");
            AUTH.load(fileInputStream);
        } catch (FileNotFoundException fileInputStreamError) {
            throw new RuntimeException(fileInputStreamError.getMessage());
        } catch (IOException ioError) {
            throw new RuntimeException(ioError.getMessage());
        }
    }

    public static String getUsername() {
        return AUTH.getProperty("api.username");
    }
    public static String getPassword() {
        return AUTH.getProperty("api.password");
    }
}
