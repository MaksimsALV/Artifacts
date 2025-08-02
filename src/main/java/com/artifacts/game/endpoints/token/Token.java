package com.artifacts.game.endpoints.token;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Token {
    public static String token;

    public static HttpResponse<String> getToken() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/token";
        try {
            HttpResponse<String> response = Send.post(endpoint, "", false);
            if (response.statusCode() == 200) {
                var responseBody = response.body();
                var object = new JSONObject(responseBody);
                token = object.getString("token");
                return response;
            } else if (response.statusCode() == 455) {
                System.err.println("getToken failed to generate token");
            } else {
                System.err.println("getToken unexpected status code: " + response.statusCode() + response.body());
            }
        } catch (Exception getTokenError) {
            System.err.println("Exception getTokenError: " + getTokenError.getMessage());
        }
        return null;
    }
}

