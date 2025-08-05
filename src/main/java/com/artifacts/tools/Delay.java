package com.artifacts.tools;

public class Delay {
    public static void delay(int seconds) throws InterruptedException {
        for (int i = seconds; i > 0; i--) {
            System.out.println("Delay: next step in " + i + "s");
            Thread.sleep(1000);
        }
    }
}
