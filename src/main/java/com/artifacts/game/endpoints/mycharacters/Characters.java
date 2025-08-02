package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class Characters {
    public static HttpResponse<String> getMyCharacters() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/characters";
        try {
            HttpResponse<String> response = Send.get(endpoint, true);
            if (response.statusCode() == 200) {
                return response;
            } else {
                System.err.println("Unexpected status code: " + response.statusCode());
            }
        } catch (Exception getServerDetailsError) {
            System.err.println(getServerDetailsError.getMessage());
        }
        return null;
    }
}
