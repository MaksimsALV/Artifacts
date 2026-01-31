package com.artifacts.api.endpoints.get;

import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.helpers.Retry.retry;

//GetMyCharacters 2.0
public class GetMyCharacters {
    public static List<HashMap<String, String>> MY_CHARACTERS = new ArrayList<>();

    public static JSONObject getMyCharacters() {
        var retryCount = 0;
        var endpoint = "/my/characters";
        var request = getRequest(endpoint, token);

        while (true) {
            try {
                HttpResponse<String> response = send(request);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var responseBody = new JSONObject(response.body());
                    var responseDataArray = responseBody.getJSONArray("data");
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
                    return responseBody;
                }
                globalErrorHandler(response, endpoint);
                return new JSONObject().put("statusCode", response.statusCode());

            } catch (Exception e) {
                System.err.println(endpoint + " | Exception: " + e);
                if (!retry(++retryCount)) {
                    return null;
                }
            }
        }
    }
}

/*//GetMyCharacters 1.0
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
                return response;
            }
            globalErrorHandler(response, endpoint);
            return response;

        } catch (Exception getMyCharactersException) {
            System.err.println(endpoint + " | Exception: " + getMyCharactersException.getMessage());
        }
        return null;
    }
}*/


