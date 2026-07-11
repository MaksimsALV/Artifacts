package com.artifacts.tools;

import com.artifacts.game.account.MyCharacters;
import org.springframework.stereotype.Component;

@Component
public class Sleep {
    public void sleep(MyCharacters character, int seconds) {
        if (character != null) {
            System.out.println(character.getName() + " Sleep for: " + seconds + " seconds");
        } else {
            System.out.println("Sleep for: " + seconds + " seconds");
        }

        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ie);
        }
    }
}
