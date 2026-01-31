package com.artifacts.api.endpoints.post;

import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.helpers.Retry.retry;

//ActionMove 2.0
public class ActionMove {
    public static JSONObject actionMove(String name, int x, int y) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/move";
        var requestBody = actionMoveBody(x, y);
        var request = postRequest(endpoint, token, requestBody);

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

/*    //ActionMove 1.0
    public class ActionMove {
        public static JSONObject actionMove(String name, int x, int y) {
            var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
            var endpoint = baseUrl + "/my/" + name + "/action/move";
            var requestBody = actionMoveBody(x, y);

            try {
                HttpResponse<String> response = Send.post(endpoint, requestBody, true);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var jsonObject = new JSONObject(response.body());
                    jsonObject.put("statusCode", response.statusCode());
                    return jsonObject;
                }
                globalErrorHandler(response, endpoint);
                return new JSONObject().put("statusCode", response.statusCode());

            } catch (Exception actionMoveException) {
                System.err.println(endpoint + " | Exception: " + actionMoveException.getMessage());
                return null;
            }
        }*/

    public static String actionMoveBody(int x, int y) {
        var jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        return jsonObject.toString();
    }
}