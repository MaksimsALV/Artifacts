package com.artifacts.game.engine.launcher;

import com.artifacts.game.endpoints.mycharacters.Characters;

import java.net.http.HttpResponse;

import static com.artifacts.game.endpoints.serverdetails.ServerDetails.getServerDetails;
import static com.artifacts.game.endpoints.token.Token.getToken;

public class Login {
    public static void login() {
        HttpResponse<String> getServerDetailsResult = getServerDetails();
        if (getServerDetailsResult.statusCode() == 200) {
            getToken();
            Characters.getMyCharacters();
        } else {
            System.exit(0);
        }
    }
}
