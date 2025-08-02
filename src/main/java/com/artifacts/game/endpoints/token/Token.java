package com.artifacts.game.endpoints.token;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class Token {
    public static HttpResponse<String> getToken() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/token";
        try {
            HttpResponse<String> response = Send.get(endpoint);
            if (response.statusCode() == 200) {
                return response;
            } else if (response.statusCode() == 455) {
                System.err.println("Failed to generate token");
            } else {
                System.err.println("Unexpected status code: " + response.statusCode());
            }
        } catch (Exception getTokenError) {
            System.err.println(getTokenError.getMessage());
        }
        return null;
    }
}

