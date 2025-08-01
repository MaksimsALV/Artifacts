package com.artifacts.api.http;

import java.net.http.HttpClient;

public class Client {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpClient getClient() {
        return client;
    }
}
