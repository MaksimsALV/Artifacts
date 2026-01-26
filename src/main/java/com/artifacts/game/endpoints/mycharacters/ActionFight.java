package com.artifacts.game.endpoints.mycharacters;

import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.game.endpoints.token.Token.token;
import static com.artifacts.tools.Retry.retry;

//ActionFight 2.0
public class ActionFight {
    public static JSONObject actionFight(String name) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/fight";
        var request = postRequest(endpoint, token, null);

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

/*
//ActionFight 1.0
public class ActionFight {
    public static JSONObject actionFight(String name) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/fight";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                jsonObject.put("statusCode", response.statusCode());
                return jsonObject;
            }
            globalErrorHandler(response, endpoint);
            return new JSONObject().put("statusCode", response.statusCode());

        } catch (Exception actionFightException) {
            System.err.println(endpoint + " | Exception: " + actionFightException.getMessage());
            return null;
        }
    }
}*/
