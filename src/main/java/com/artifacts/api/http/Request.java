package com.artifacts.api.http;

import java.net.URI;
import java.net.http.HttpRequest;

public class Request {
    public static HttpRequest buildGetRequest(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Accept","application/json")
                .GET()
                .build();
    }

    public static HttpRequest buildPostRequest(String uri, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }
}
