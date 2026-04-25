package com.artifacts.tools;


public class Retry {
    private final Sleep sleep;

    public Retry(Sleep sleep) {
        this.sleep = sleep;
    }

    public void retry(int count) {
        final int delay = 30;
        System.out.println("retry attempt: " + count);
        sleep.sleep(delay);
    }
}
