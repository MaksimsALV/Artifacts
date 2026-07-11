package com.artifacts.tools;

import org.springframework.stereotype.Component;

@Component
public class Retry {
    private final Sleep sleep;

    public Retry(Sleep sleep) {
        this.sleep = sleep;
    }

    public void retry() {
        final int delay = 10;
        System.out.println("retry attempt");
        sleep.sleep(null, delay);
    }
}
