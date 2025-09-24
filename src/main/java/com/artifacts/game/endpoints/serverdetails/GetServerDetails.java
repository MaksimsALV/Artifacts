package com.artifacts.game.endpoints.serverdetails;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;

import java.net.http.HttpResponse;

import static com.artifacts.tools.Delay.delay;

public class GetServerDetails {
    public static boolean serverIsUp;

    public static HttpResponse<String> getServerDetails() {
        //todo should redo the logic similar to other endpoints
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/";
        var count = 0;
        var retry = 7;

        while (true) {
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
                if (++count >= retry) {
                    return null;
                }
                try {
                    delay(5);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
            return null;
        }
    }
}
