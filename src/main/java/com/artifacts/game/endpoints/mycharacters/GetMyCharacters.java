package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class GetMyCharacters {
    public static String name;

    public static HttpResponse<String> getMyCharacters() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/characters";
        try {
            HttpResponse<String> response = Send.get(endpoint, true);
            globalErrorHandler(response);

            if (response.statusCode() == CODE_SUCCESS) {
                var responseBody = response.body();
                JSONObject object = new JSONObject(responseBody);
                name = object
                        .getJSONArray("data")
                        .getJSONObject(0)
                        .getString("name");
                return response;
            } else {
                System.err.println("getMyCharacters unexpected status code: " + response.statusCode());
            }
        } catch (Exception getMyCharactersError) {
            System.err.println("Exception getMyCharactersError: " + getMyCharactersError.getMessage());
        }

        return null;
    }
}
