
package com.artifacts.helpers;

import static com.artifacts.helpers.Delay.delay;

public class Retry {
    public static final int RETRY_ATTEMPTS = 7;
    public static final int RETRY_DELAY = 10;

    public static boolean retry(int count) {
        if (count >= RETRY_ATTEMPTS) {
            return false;
        } else {
            delay(RETRY_DELAY);
            return true;
        }
    }
}

