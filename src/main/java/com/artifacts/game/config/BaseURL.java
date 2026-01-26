//package com.artifacts.game.config;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Properties;
//
//public class BaseURL {
//    public static final Properties BASE_URL = new Properties();
//
//    static {
//        try {
//            var fileInputStream = new FileInputStream("config/config.properties");
//            BASE_URL.load(fileInputStream);
//            } catch (FileNotFoundException fileInputStreamError) {
//                throw new RuntimeException(fileInputStreamError.getMessage());
//        } catch (IOException ioError) {
//            throw new RuntimeException(ioError.getMessage());
//        }
//    }
//
//    public static String getBaseUrl(String key) {
//        return BASE_URL.getProperty(key);
//    }
//}
