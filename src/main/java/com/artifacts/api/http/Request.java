package com.artifacts.api.http;

import java.net.URI;
import java.net.http.HttpRequest;

public class Request {
    public static HttpRequest buildGetRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept","application/json")
                .GET()
                .build();
    }

    public static HttpRequest buildPostRequest(String endpoint, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
}
