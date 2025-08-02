package com.artifacts.game.engine.launcher;

import com.artifacts.game.endpoints.mycharacters.Characters;

import java.net.http.HttpResponse;

import static com.artifacts.game.endpoints.serverdetails.ServerDetails.getServerDetails;
import static com.artifacts.game.endpoints.serverdetails.ServerDetails.serverIsUp;
import static com.artifacts.game.endpoints.token.Token.*;

public class Login {
    public static void login() {
        getServerDetails();
         if (serverIsUp) {
            getToken();
        } else {
            System.exit(0);
        }
    }
}
