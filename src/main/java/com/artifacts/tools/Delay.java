package com.artifacts.tools;

public class Delay {
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
