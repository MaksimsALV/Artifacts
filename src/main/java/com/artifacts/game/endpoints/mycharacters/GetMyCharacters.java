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
    public static String name;
    public static List<HashMap<String, String>> MY_CHARACTERS = new ArrayList<>();

    public static HttpResponse<String> getMyCharacters() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/characters";

        try {
            HttpResponse<String> response = Send.get(endpoint, true);
            globalErrorHandler(response);

            if (response.statusCode() == CODE_SUCCESS) {
                var object = new JSONObject(response.body());
                var responseDataArray = object.getJSONArray("data");
                MY_CHARACTERS.clear();

                for (var line : responseDataArray) {
                    var responseData = (JSONObject) line;
                    HashMap<String, String> dataMap = new HashMap<>();
                    for (String key : responseData.keySet()) {
                        dataMap.put(key, responseData.get(key).toString());
                    }
                    MY_CHARACTERS.add(dataMap);
                }
            }
        } catch (Exception getMyCharactersException) {
            System.err.println("getMyCharacters() exception occurred: " + getMyCharactersException.getMessage());
        }
        return null;
    }
}
