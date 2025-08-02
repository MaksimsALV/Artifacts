package com.artifacts.game.endpoints.serverdetails;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

public class ServerDetails {
    public static boolean serverIsUp;

    public static HttpResponse<String> getServerDetails() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/";
        try {
            HttpResponse<String> response = Send.get(endpoint, false);
            if (response.statusCode() == 200) {
                serverIsUp = true;
                return response;
            } else {
                serverIsUp = false;
                System.err.println("getServerDetails unexpected status code: " + response.statusCode());
            }
        } catch (Exception getServerDetailsError) {
            serverIsUp = false;
            System.err.println("Exception getServerDetailsError: " + getServerDetailsError.getMessage());
        }
        return null;
    }
}
