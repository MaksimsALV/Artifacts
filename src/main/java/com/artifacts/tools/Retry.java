/*
package com.artifacts.tools;

import static com.artifacts.tools.Delay.delay;

public class Retry {
    private static int count = 0;
    private static final int retry = 7;

    public static boolean retry() {
        if (++count >= retry) {
            count = 0;
            return false;
        }

        try {
            delay(1);
            return true;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}

 */
