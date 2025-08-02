package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class ActionRest {
    public static HttpResponse<String> actionRest() {
        var name = GetMyCharacters.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/rest";
        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            if (response.statusCode() == 200) {
                return response;
            } else if (response.statusCode() == 486) {
                    System.err.println("actionRest An action is already in progress for this character.");
            } else if (response.statusCode() == 498) {
                System.err.println("actionRest Character not found.");
            } else if (response.statusCode() == 499) {
                System.err.println("actionRest The character is in cooldown.");
            } else {
                System.err.println("actionRest unexpected status code: " + response.statusCode() + response.body());
            }
        } catch (Exception actionRestError) {
            System.err.println("Exception actionRestError: " + actionRestError.getMessage());
        }
        return null;
    }
}
