package com.artifacts.game.endpoints.characters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.config.Characters.getWarrior;
import static com.artifacts.tools.Converter.convertCooldownExpirationTimeToMillis;

public class GetCharacter {
    public static List<HashMap<String, String>> CHARACTER = new ArrayList<>();

    public static HttpResponse<String> getCharacter() {
        var name = getWarrior();
        //var name = GetMyCharacters.MY_CHARACTERS.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/characters/" + name;

        try {
            HttpResponse<String> response = Send.get(endpoint, false);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                CHARACTER.clear();
                HashMap<String, String> characterData = new HashMap<>();
                for (var key : responseDataObject.keySet()) {
                    var value = responseDataObject.get(key).toString();
                    characterData.put(key, value);
                }
                CHARACTER.add(characterData);
                var cooldownExpirationMillis = convertCooldownExpirationTimeToMillis();
                var cooldown = cooldownExpirationMillis - System.currentTimeMillis();
                var seconds = cooldown / 1000;
                if (cooldown > 0) {
                    //todo need to add reason from response
                    System.out.println(name + " is now on a cooldown for: " + seconds + "s");
                    //Thread.sleep(cooldown);
                    return response;
                }
                return response;
            }
            globalErrorHandler(response, endpoint);
            return response;

        } catch (Exception getCharacterException) {
            System.err.println(endpoint + " | Exception: " + getCharacterException.getMessage());
        }
        return null;
    }

    //helper that I can use to get response body object directly accessible after calling the endpoint. One for all
    //todo actually should add this to all endpoints
    public static JSONObject getCharacterResponseDataObject() {
        HttpResponse<String> response = getCharacter();
        if (response == null || response.statusCode() != CODE_SUCCESS) {
            return null;
        }
        return new JSONObject(response.body()).getJSONObject("data");
    }
}
