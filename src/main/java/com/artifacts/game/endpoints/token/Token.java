package com.artifacts.game.endpoints.token;

//import com.artifacts.api.http.Client;

import java.net.http.HttpResponse;
import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_TOKEN_GENERATION_FAIL;
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

//Token 1.0
//public class Token {
//    public static String token;
//
//    public static HttpResponse<String> getToken() {
//        //todo should redo whole logic similar to other endpoints
//        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
//        var endpoint = baseUrl + "/token";
//        try {
//            HttpResponse<String> response = Send.post(endpoint, "", false);
//            if (response.statusCode() == 200) {
//                var responseBody = response.body();
//                var object = new JSONObject(responseBody);
//                token = object.getString("token");
//                return response;
//            } else if (response.statusCode() == 455) {
//                System.err.println("getToken failed to generate token");
//            } else {
//                System.err.println("getToken unexpected status code: " + response.statusCode() + response.body());
//            }
//        } catch (Exception getTokenError) {
//            System.err.println("Exception getTokenError: " + getTokenError.getMessage());
//        }
//        return null;
//    }
//}

