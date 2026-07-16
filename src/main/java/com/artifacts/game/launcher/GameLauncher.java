package com.artifacts.game.launcher;

import com.artifacts.api.service.account.GenerateToken;
import com.artifacts.api.service.GetServerStatus;
import com.artifacts.game.account.ValidateAndCreateCharacters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameLauncher {
    private final GetServerStatus getServerStatus;
    private final GenerateToken generateToken;

    public void gameStart() {
        System.out.println("Starting the game...");
        System.out.println("Getting Server Status...");

        if (getServerStatus.serverIsUp()) {
            System.out.println("Server is Up!");
            System.out.println("Generating Token...");

            generateToken.generateToken();
            System.out.println("Token Generated!");
            System.out.println("Enjoy the game!");
        } else {
            System.out.println("Server is Down!");
            System.exit(0);
        }
    }
}
