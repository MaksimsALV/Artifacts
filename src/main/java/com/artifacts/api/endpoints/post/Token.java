package com.artifacts.api.endpoints.post;

//import com.artifacts.api.http.Client;

import java.net.http.HttpResponse;
import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.*;
import static com.artifacts.tools.Retry.retry;

//Token 2.0
public class Token {
    public static String token;

    public static JSONObject getToken() {
        var retryCount = 0;
        var endpoint = "/token";
        var request = postRequest(endpoint, null, null);

        while (true) {
            try {
                HttpResponse<String> response = send(request);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var responseBody = new JSONObject(response.body());
                    token = responseBody.optString("token");
                    return responseBody;
                }
                globalErrorHandler(response, endpoint);
                return new JSONObject().put("statusCode", response.statusCode());
            } catch (Exception e) {
                System.err.println("Exception getTokenError: " + e);
                if (!retry(++retryCount)) {
                    return null;
                }
            }
        }
    }
}

