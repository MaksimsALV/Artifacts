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
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.endpoints.characters.GetCharacter.*;

public class ActionMove {
    //todo need to think if I even need lists for each endpoint like this. Because i work with data directly from response 200 body data
    public static List<HashMap<String, String>> MOVE = new ArrayList<>();

    public static HttpResponse<String> actionMove(int x, int y) {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);

        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                //todo if i will not use lists of actions then maybe i can remove whole bottom logic of putting data into the lists. Because i work with data directly from response 200 body data
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
                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                var reason = responseCooldownDataObject.getString("reason");
                var millis = cooldown * 1000;
                if (responseCooldownDataObject.getInt("remaining_seconds") > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(millis);
                } else {
                    globalErrorHandler(response, endpoint);
                }
            }
        } catch (Exception actionMoveException) {
            System.err.println(endpoint + " | Exception: " + actionMoveException.getMessage());
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
