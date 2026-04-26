package com.artifacts.game.launcher;

import com.artifacts.game.Events.*;
import com.artifacts.game.server.service.GenerateToken;
import com.artifacts.game.server.service.GetServerStatus;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameLauncher {
    private final GetServerStatus getServerStatus;
    private final GenerateToken generateToken;
    private final ApplicationEventPublisher applicationEventPublisher;

    public GameLauncher(GetServerStatus getServerStatus, GenerateToken generateToken, ApplicationEventPublisher applicationEventPublisher) {
        this.getServerStatus = getServerStatus;
        this.generateToken = generateToken;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void gameStart() {
        System.out.println("Starting the game...");
        System.out.println("Getting Server Status...");

        if (getServerStatus.serverIsUp()) {
            System.out.println("Server is Up!");
            System.out.println("Generating Token...");

            generateToken.generateToken();
            System.out.println("Token Generated!");
            System.out.println("Enjoy the game!");
            applicationEventPublisher.publishEvent(new GameLaunchedSuccessfullyEvent());
        } else {
            System.out.println("Server is Down!");
            System.exit(0);
        }
    }
}
