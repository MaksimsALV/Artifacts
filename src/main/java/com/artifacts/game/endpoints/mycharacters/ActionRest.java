package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.tools.Converter;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionRest {
    public static List<HashMap<String, String>> REST = new ArrayList<>();

    public static HttpResponse<String> actionRest() {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/rest";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            var responseBody = response.body();

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(responseBody);
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                REST.clear();

                HashMap<String, String> characterData = new HashMap<>();
                for (var key : responseCharacterDataObject.keySet()) {
                    var value = responseCharacterDataObject.get(key).toString();
                    characterData.put(key, value);
                }
                REST.add(characterData);

                HashMap<String, String> cooldownData = new HashMap<>();
                for (var key : responseCooldownDataObject.keySet()) {
                    var value = responseCooldownDataObject.get(key).toString();
                    cooldownData.put(key, value);
                }
                REST.add(cooldownData);
                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                var millis = cooldown * 1000;
                if (responseCooldownDataObject.getInt("remaining_seconds") > 0) {
                    //todo need to add reason from response
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s");
                    Thread.sleep(millis);
                } else {
                    globalErrorHandler(response, endpoint);
                }

                /*
                //todo i am handling cooldown via response 200 result data, so should remove this if all tests pass good.
            } else if (response.statusCode() == CODE_CHARACTER_IN_COOLDOWN) {
                var object = new JSONObject(responseBody);
                var responseErrorObject = object.getJSONObject("error");
                var responseErrorMessage = responseErrorObject.getString("message");
                var errorMessageFinder = Pattern.compile("\\d+(\\.\\d+)?").matcher(responseErrorMessage);
                var seconds = 1.0;
                if (errorMessageFinder.find()) {
                    seconds = Double.parseDouble(errorMessageFinder.group());
                }
                System.err.println("499: actionRest The character is in cooldown: Sleeping for " + seconds + "s and repeating the step again.");
                Thread.sleep(Converter.SecondsToMillisConverter(seconds));
                actionRest();
                 */
            }
        } catch (Exception actionRestException) {
            System.err.println(endpoint + " | Exception: " + actionRestException.getMessage());
        }
        return null;
    }
}
