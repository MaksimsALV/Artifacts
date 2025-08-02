package com.artifacts.game.endpoints.serverdetails;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class ServerDetails {
    public static HttpResponse<String> getServerDetails() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/";
        try {
            HttpResponse<String> response = Send.get(endpoint);
            if (response.statusCode() != 200) {
                System.err.println("Server status is unknown: " + response.statusCode());
            }
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
