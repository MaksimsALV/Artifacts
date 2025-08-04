package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.errorhandling.GlobalErrorHandler;
import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import com.artifacts.game.endpoints.characters.GetCharacter;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.extractCooldownSecondsFromErrorMessage;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.endpoints.characters.GetCharacter.*;

public class ActionMove {
    public static List<HashMap<String, String>> MOVE = new ArrayList<>();

    public static HttpResponse<String> actionMove(int x, int y) {
        //var name = GetCharacter.CHARACTER.get(0).get("name");
        var name = "abc";
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);

        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);

            if (response.statusCode() == CODE_SUCCESS) {
                //todo need to add Thread.sleep() after success from the response.
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                MOVE.clear();

                HashMap<String, String> characterData = new HashMap<>();
                for (var key : responseCharacterDataObject.keySet()) {
                    var value = responseCharacterDataObject.get(key).toString();
                    characterData.put(key, value);
                }
                MOVE.add(characterData);

                HashMap<String, String> cooldownData = new HashMap<>();
                for (var key : responseCooldownDataObject.keySet()) {
                    var value = responseCooldownDataObject.get(key).toString();
                    cooldownData.put(key, value);
                }
                MOVE.add(cooldownData);
            } else {
                globalErrorHandler(response, endpoint);
            }
        } catch (Exception actionMoveException) {
            System.err.println(endpoint + " | Exception in getCharacter: " + actionMoveException.getMessage());
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
