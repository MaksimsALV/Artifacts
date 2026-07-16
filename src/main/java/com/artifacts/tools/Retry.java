package com.artifacts.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Retry {
    private final Sleep sleep;

    public void retry() {
        final int delay = 10;
        System.out.println("retry attempt");
        sleep.sleep(null, delay);
    }
}
