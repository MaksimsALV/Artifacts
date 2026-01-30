package com.artifacts.api.http;

//import com.artifacts.game.config.Auth;
//import com.artifacts.game.config.BaseURL;
//import com.artifacts.game.endpoints.token.Token;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Component
//client 2.0
public class Client {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static String USERNAME;
    private static String PASSWORD;
    private static String BASE_URL;
    public Client(Config config) {
        this.USERNAME = config.getUsername();
        this.PASSWORD = config.getPassword();
        this.BASE_URL = config.getBaseUrl();
    }

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
