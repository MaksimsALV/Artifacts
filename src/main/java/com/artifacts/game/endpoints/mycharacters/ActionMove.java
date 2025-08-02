package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;

public class ActionMove {
    public static HttpResponse<String> actionMove(int x, int y) {
        var name = GetMyCharacters.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);
        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);
            if (response.statusCode() == 200) {
                //var responseBody = response.body();
                //var object = new JSONObject(responseBody);
                return response;
//todo all else if logic requires automation to do something once certain error code appears, for example: wait on 486 or 499 and repeat again after certain time due to CD.
            } else if (response.statusCode() == 404) {
                System.err.println("actionMove Map not found.");
            } else if (response.statusCode() == 486) {
                System.err.println("actionMove An action is already in progress for this character.");
            } else if (response.statusCode() == 490) {
                System.err.println("actionMove The character is already at the destination.");
            } else if (response.statusCode() == 498) {
                System.err.println("actionMove Character not found.");
            } else if (response.statusCode() == 499) {
                //todo need to call getCharacter in order to retrieve current cooldown=value, then put that value +1000 millis inside the logic. There is also cooldown_expiration timestamp for minmaxing
                Thread.sleep(1000);
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
