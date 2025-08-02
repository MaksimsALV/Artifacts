package com.artifacts.game.engine.launcher;

import java.net.http.HttpResponse;

import static com.artifacts.game.endpoints.serverdetails.ServerDetails.getServerDetails;

public class Login {
    public static void login() {
        HttpResponse<String> getServerDetailsResult = getServerDetails();
        if (getServerDetailsResult.statusCode() == 200) {
            //getToken();
        } else {
            System.exit(0);
        }
    }
}
