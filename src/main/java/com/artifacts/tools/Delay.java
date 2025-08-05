package com.artifacts.tools;

public class Delay {
    public static void delay(int seconds) throws InterruptedException {
        for (int i = 0; i < seconds; i--) {
            System.out.println("Delay: next step in " + seconds + "s");
            Thread.sleep(1000);
        }
    }
}
