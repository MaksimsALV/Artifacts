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
            if (response.statusCode() == 200) {
                return response;
            } else {
                System.err.println("Unexpected status code: " + response.statusCode());
            }
        } catch (Exception getServerDetailsError) {
            System.err.println(getServerDetailsError.getMessage());
        }
        return null;
    }
}
