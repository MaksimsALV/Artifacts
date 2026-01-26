package com.artifacts.api.http;

//import com.artifacts.game.config.Auth;
//import com.artifacts.game.config.BaseURL;
//import com.artifacts.game.endpoints.token.Token;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

//client 2.0
public class Client {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = Config.getBaseUrl();
    private static final String USERNAME = Config.getUsername();
    private static final String PASSWORD = Config.getPassword();

    //GET request
    public static HttpRequest getRequest(String endpoint, String token) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json")
                .GET();

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder.build();
    }

    //POST request
    public static HttpRequest postRequest(String endpoint, String token, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json");

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        } else if (USERNAME != null && PASSWORD != null) {
            var encodedBasicAuth = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
            builder.header("Authorization", "Basic " + encodedBasicAuth);
        }

        if (body != null) {
            builder
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body));
        } else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }
        return builder.build();
    }

    //response
    public static HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //todo temporary, must delete later
        public static HttpClient getClient() {
        return CLIENT;
    }
}

//client 1.0
//public class Client {
//    private static final HttpClient client = HttpClient.newHttpClient();
//
//    public static HttpClient getClient() {
//        return client;
//    }
//}
