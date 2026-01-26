package com.artifacts.game.endpoints.mycharacters;

import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.game.endpoints.token.Token.token;
import static com.artifacts.tools.Retry.retry;

//ActionUseItem 2.0
public class ActionUseItem {
    public static JSONObject actionUseItem(String name, String itemCode, int quantity) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/use";
        var requestBody = new JSONObject()
                .put("code", itemCode)
                .put("quantity", quantity)
                .toString();
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
}

/*//ActionUseItem 1.0
public class ActionUseItem {
    public static JSONObject actionUseItem(String name, String itemCode, int quantity) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/use";
        var requestBody = new JSONObject()
                .put("code", itemCode)
                .put("quantity", quantity)
                .toString();

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

        } catch (Exception actionUseItemException) {
            System.err.println(endpoint + " | Exception: " + actionUseItemException.getMessage());
            return null;
        }
    }
}*/
