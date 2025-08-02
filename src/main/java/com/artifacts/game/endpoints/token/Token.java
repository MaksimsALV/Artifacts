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
                var token = object.getString("token");
                Token.setToken(token);
                return response;
            } else if (response.statusCode() == 455) {
                System.err.println("Failed to generate token");
            } else {
                System.err.println("Unexpected status code: " + response.statusCode() + response.body());
            }
        } catch (Exception getTokenError) {
            System.err.println(getTokenError.getMessage());
        }
        return null;
    }

    public static void setToken(String tokenValue) {
        var token = tokenValue;
    }
}

