package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.regex.Pattern;

public class ActionMove {
    public static int positionX;
    public static int positionY;
    public static int cooldownTotalSeconds;
    public static int cooldownRemainingSeconds;
    public static int hp;

    public static HttpResponse<String> actionMove(int x, int y) {
        var name = GetMyCharacters.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);

        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);
            var responseBody = response.body();

            if (response.statusCode() == 200) {
                System.out.println("200: actionMove was successful");
                var object = new JSONObject(responseBody);
                var responseDataObject = object.getJSONObject("data");
                var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                positionX = responseCharacterDataObject.getInt("x");
                positionY = responseCharacterDataObject.getInt("y");
                cooldownTotalSeconds = responseCooldownDataObject.getInt("total_seconds");
                cooldownRemainingSeconds = responseCooldownDataObject.getInt("remaining_seconds");
                hp = responseCharacterDataObject.getInt("hp");
                return response;
            //todo all else if logic requires automation to do something once certain error code appears, for example: wait on 486 or 499 and repeat again after certain time due to CD.
            } else if (response.statusCode() == 404) {
                System.err.println("404: actionMove Map not found.");
            } else if (response.statusCode() == 486) {
                System.err.println("486: actionMove An action is already in progress for this character.");
            } else if (response.statusCode() == 490) {
                System.err.println("490: actionMove The character is already at the destination.");
            } else if (response.statusCode() == 498) {
                System.err.println("498: actionMove Character not found.");
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

                System.err.println("499: actionMove The character is in cooldown: Sleeping for " + seconds + "s and repeating the step again.");
                Thread.sleep(time);
                actionMove(x, y);
            } else {
                System.err.println("actionMove unexpected status code: " + response.statusCode() + response.body());
            }
        } catch (Exception actionMoveError) {
            System.err.println("Exception actionMoveError: " + actionMoveError.getMessage());
        }
        return null;
    }

    public static String actionMoveBody(int x, int y) {
        var json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json.toString();
    }
}
