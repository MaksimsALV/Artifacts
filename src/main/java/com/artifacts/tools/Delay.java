package com.artifacts.tools;

public class Delay {
//    public static void delay(int seconds) throws InterruptedException {
//        for (int i = seconds; i > 0; i--) {
//            System.out.println("Delay: next step in " + i + "s");
//            Thread.sleep(1000);
//        }
//    }

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
