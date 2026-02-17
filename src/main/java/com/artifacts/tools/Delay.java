package com.artifacts.tools;

public class Delay {
    //delay 2.0
    public static void delay(int seconds) {
        System.out.println("Delay for: " + seconds + " seconds");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ie);
        }
    }
}
