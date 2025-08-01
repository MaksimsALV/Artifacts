package com.artifacts.api.serverdetails;

import com.artifacts.api.http.Send;

import java.net.http.HttpResponse;

public class ServerDetails {
    public static HttpResponse<String> getServerDetails() {
        var uri = "https://api.artifactsmmo.com/";
        try {
            HttpResponse<String> response = Send.get(uri);
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
