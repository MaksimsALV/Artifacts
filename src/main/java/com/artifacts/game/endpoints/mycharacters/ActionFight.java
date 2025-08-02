package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class ActionFight {
    public static HttpResponse<String> actionFight() {
        var name = GetMyCharacters.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/fight";
        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            if (response.statusCode() == 200) {
                return response;
            } else if (response.statusCode() == 486) {
                System.err.println("actionFight An action is already in progress for this character.");
            } else if (response.statusCode() == 487) {
                System.err.println("actionFight The character's inventory is full.");
            } else if (response.statusCode() == 498) {
                System.err.println("actionFight Character not found.");
            } else if (response.statusCode() == 499) {
                System.err.println("actionFight The character is in cooldown.");
            } else if (response.statusCode() == 598) {
                    System.err.println("actionFight Monster not found on this map.");
            } else {
                System.err.println("actionFight unexpected status code: " + response.statusCode() + response.body());
            }
        } catch (Exception actionFightError) {
            System.err.println("Exception actionFightError: " + actionFightError.getMessage());
        }
        return null;
    }
}
