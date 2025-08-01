package com.artifacts.api.http;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Response {
    public static HttpResponse<String> buildResponse(HttpRequest request) throws IOException, InterruptedException {
        var client = Client.getClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
