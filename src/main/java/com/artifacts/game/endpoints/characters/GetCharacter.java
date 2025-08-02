package com.artifacts.game.endpoints.characters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class GetCharacter {
    public static int positionX;
    public static int positionY;

    public static HttpResponse<String> getCharacter() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/characters/" + GetMyCharacters.name;
        try {
            HttpResponse<String> response = Send.get(endpoint, false);
            if (response.statusCode() == 200) {
                var responseBody = response.body();
                var object = new JSONObject(responseBody);
                var data = object.getJSONObject("data");
                positionX = data.getInt("x");
                positionY = data.getInt("y");
                return response;
            } else if (response.statusCode() == 404) {
                System.err.println("getCharacter Character not found.");
            } else {
                System.err.println("getCharacter unexpected status code: " + response.statusCode());
            }
        } catch (Exception getCharacterError) {
            System.err.println("Exception getCharacterError: " + getCharacterError.getMessage());
        }
        return null;
    }
}
