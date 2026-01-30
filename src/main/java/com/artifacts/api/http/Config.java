package com.artifacts.api.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
//Config 2.0
public class Config {

    @Value("${api.username}")
    private String username;

    @Value("${api.password}")
    private String password;

    @Value("${api.baseUrl}")
    private String baseUrl;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getBaseUrl() {
        return baseUrl;
    }
}

/*//Config 1.1
public class Config {
    private static final Properties CONFIG_PROPERTIES = new Properties();

    static {
        try (FileInputStream configProperties = new FileInputStream("src/main/resources/application.properties")) {
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
}*/
