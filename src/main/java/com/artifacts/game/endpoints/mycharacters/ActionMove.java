package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import com.artifacts.game.endpoints.characters.GetCharacter;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.endpoints.characters.GetCharacter.*;

public class ActionMove {
    public static List<HashMap<String, String>> MOVE = new ArrayList<>();

    public static HttpResponse<String> actionMove(int x, int y) {
        //var name = GetMyCharacters.MY_CHARACTERS.get(0).get("name");
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);

        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);
            globalErrorHandler(response);

            if (response.statusCode() == CODE_SUCCESS) {
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                MOVE.clear();

                HashMap<String, String> characterData = new HashMap<>();
                for (var key : responseCharacterDataObject.keySet()) {
                    var value = responseCharacterDataObject.getString(key).toString();
                    characterData.put(key, value);
                }
                MOVE.add(characterData);

                HashMap<String, String> cooldownData = new HashMap<>();
                for (var key : responseCooldownDataObject.keySet()) {
                    var value = responseCooldownDataObject.getString(key).toString();
                    cooldownData.put(key, value);
                }
                MOVE.add(cooldownData);

            //todo all else if logic requires automation to do something once certain error code appears, for example: wait on 486 or 499 and repeat again after certain time due to CD.
            } else if (response.statusCode() == CODE_CHARACTER_IN_COOLDOWN) {
                var object = new JSONObject(response.body());
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
            }
        } catch (Exception actionMoveException) {
            System.err.println("actionMove() exception occurred: " + actionMoveException.getMessage());
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
