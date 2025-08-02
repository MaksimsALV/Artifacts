package com.artifacts.api.http;

import com.artifacts.game.config.Auth;
import com.artifacts.game.endpoints.token.Token;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Base64;

public class Request {
    public static HttpRequest buildGetRequest(String endpoint, boolean useToken) {
        if (useToken) {
            return HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + Token.getToken()) // or just the token if needed
                    .GET()
                    .build();
        } else {
            return HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
        }
    }

    public static HttpRequest buildPostRequest(String endpoint, String body, boolean useToken) {
        var username = Auth.getUsername();
        var password = Auth.getPassword();
        var auth = username + ":" + password;
        var encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        var authHeader = "Basic " + encodedAuth;

        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", authHeader)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
}
