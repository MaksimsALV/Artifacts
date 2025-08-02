package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.regex.Pattern;

public class ActionFight {
    public static int cooldownTotalSeconds;
    public static int cooldownRemainingSeconds;
    public static int hp;

    public static HttpResponse<String> actionFight() {
        var name = GetMyCharacters.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/fight";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            var responseBody = response.body();

            if (response.statusCode() == 200) {
                var object = new JSONObject(responseBody);
                var responseDataObject = object.getJSONObject("data");
                var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                cooldownTotalSeconds = responseCooldownDataObject.getInt("total_seconds");
                cooldownRemainingSeconds = responseCooldownDataObject.getInt("remaining_seconds");
                hp = responseCharacterDataObject.getInt("hp");
                return response;
            } else if (response.statusCode() == 486) {
                System.err.println("actionFight An action is already in progress for this character.");
            } else if (response.statusCode() == 487) {
                System.err.println("actionFight The character's inventory is full.");
            } else if (response.statusCode() == 498) {
                System.err.println("actionFight Character not found.");
            } else if (response.statusCode() == 499) {
                var object = new JSONObject(responseBody);
                var responseErrorObject = object.getJSONObject("error");
                var responseErrorMessage = responseErrorObject.getString("message");

                //todo should move whole regex logic somewhere else.
                var errorMessageFinder = Pattern.compile("\\d+(\\.\\d+)?").matcher(responseErrorMessage);
                var seconds = 1.0;
                if (errorMessageFinder.find()) {
                    seconds = Double.parseDouble(errorMessageFinder.group());
                }
                var time = (long) (seconds * 1000);

                System.err.println("499: actionFight The character is in cooldown: Sleeping for " + seconds + "s and repeating the step again.");
                Thread.sleep(time);
                actionFight();
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
