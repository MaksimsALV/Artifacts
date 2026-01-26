package com.artifacts.game.endpoints.mycharacters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.game.endpoints.token.Token.token;
import static com.artifacts.tools.Retry.retry;

//ActionEquipItem 2.0
public class ActionEquipItem {
    public static JSONObject actionEquipItem(String name, String itemCode, String itemSlot, Integer quantity) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/equip";
        var requestBody = new JSONObject()
                .put("code", itemCode)
                .put("slot", itemSlot)
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

/*//ActionEquipItem 1.0
public class ActionEquipItem {
    public static JSONObject actionEquipItem(String name, String itemCode, String itemSlot, Integer quantity) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/equip";
        var requestBody = new JSONObject()
                .put("code", itemCode)
                .put("slot", itemSlot)
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

        } catch (Exception actionEquipItemException) {
            System.err.println(endpoint + " | Exception: " + actionEquipItemException.getMessage());
            return null;
        }
    }
}*/
