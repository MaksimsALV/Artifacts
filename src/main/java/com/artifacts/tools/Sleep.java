package com.artifacts.tools;

public class Sleep {
    public void sleep(int seconds) {
        System.out.println("Sleep for: " + seconds + " seconds");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ie);
        }
    }
}
