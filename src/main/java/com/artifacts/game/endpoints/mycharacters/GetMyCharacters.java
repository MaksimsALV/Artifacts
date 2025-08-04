package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class GetMyCharacters {
    public static List<HashMap<String, String>> MY_CHARACTERS = new ArrayList<>();

    public static HttpResponse<String> getMyCharacters() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/characters";

        try {
            HttpResponse<String> response = Send.get(endpoint, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataArray = object.getJSONArray("data");
                MY_CHARACTERS.clear();

                for (var characterObject : responseDataArray) {
                    var eachCharacter = (JSONObject) characterObject;
                    HashMap<String, String> characterData = new HashMap<>();
                    for (var key : eachCharacter.keySet()) {
                        var value = eachCharacter.get(key).toString();
                        characterData.put(key, value);
                    }
                    MY_CHARACTERS.add(characterData);
                }
            } else {
                globalErrorHandler(response, endpoint);
            }
        } catch (Exception getMyCharactersException) {
            System.err.println(endpoint + " | Exception: " + getMyCharactersException.getMessage());
        }
        return null;
    }
}
