package com.artifacts.game.engine.launcher;

import static com.artifacts.game.endpoints.serverdetails.GetServerDetails.getServerDetails;
import static com.artifacts.game.endpoints.serverdetails.GetServerDetails.serverIsUp;
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
