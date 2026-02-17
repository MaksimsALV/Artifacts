package com.artifacts.api.endpoints.post;

import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.tools.Retry.retry;

//ActionCrafting 2.0
public class ActionCrafting {
    public static JSONObject actionCrafting(String name, String item, int quantity) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/crafting";
        var requestBody = actionCraftingBody(item, quantity);
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

    public static String actionCraftingBody(String item, int quantity) {
        var jsonObject = new JSONObject();
        jsonObject.put("code", item.toLowerCase());
        jsonObject.put("quantity", quantity);
        return jsonObject.toString();
    }
}
