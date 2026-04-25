package com.artifacts.game.launcher;

import com.artifacts.game.launcher.service.Login;
import com.artifacts.game.server.service.GetServerStatus;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartLauncher {
    private final GetServerStatus getServerStatus;
    private final Login login;

    public StartLauncher(GetServerStatus getServerStatus, Login login) {
        this.getServerStatus = getServerStatus;
        this.login = login;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startLauncher() {
        System.out.println("Launcher Started");
        System.out.println("Getting Server Status...");
        if (getServerStatus.serverIsUp()) {
            System.out.println("Server is Up!");
            // continue with login
        } else {
            System.out.println("Server is Down!");
            System.exit(0);
        }
    }
}
