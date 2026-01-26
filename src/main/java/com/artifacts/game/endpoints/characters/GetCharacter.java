package com.artifacts.game.endpoints.characters;

import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.tools.Delay.delay;
import static com.artifacts.tools.Retry.retry;

//GetCharacter 2.0
public class GetCharacter {
    public static JSONObject getCharacter(String name) {
        var retryCount = 0;
        var endpoint = "/characters/" + name;
        var request = getRequest(endpoint, null);

        while (true) {
            try {
                HttpResponse<String> response = send(request);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var responseBody = new JSONObject(response.body());
                    responseBody.put("statusCode", response.statusCode());
                    return responseBody;
                }
                globalErrorHandler(response, endpoint);
                return new JSONObject().put("statusCode", response.statusCode());

            } catch (Exception e) {
                System.err.println(endpoint + " | Exception: " + e);
                if (!retry(++retryCount)) {
                    return null;
                }
            }
        }
    }
}

//GetCharacter 1.0
/*
public class GetCharacter {
    public static JSONObject getCharacter(String name) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/characters/" + name;
        var retryCount = 0;
//        var retryAmount = 7;
//        var retryDelay = 10;

        while (true) {
            try {
                HttpResponse<String> response = Send.get(endpoint, false);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var jsonObject = new JSONObject(response.body());
                    jsonObject.put("statusCode", response.statusCode());
                    return jsonObject;
                }
                globalErrorHandler(response, endpoint);
                return new JSONObject().put("statusCode", response.statusCode());

            } catch (Exception e) {
                System.err.println(endpoint + " | Exception: " + e);
                if (!retry(++retryCount)) {
                    return null;
                }
            }
//                if (++count >= retryAmount) {
//                    return null;
//                }
//                delay(retryDelay);
//            }
        }
    }
}
 */