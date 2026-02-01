package com.artifacts.api.endpoints.get;

import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.tools.Retry.retry;

//GetBankItems 2.0
public class GetBankItems {

    public static JSONObject getBankItems() {
        var retryCount = 0;
        var sizeParam = "?size=" + 100; //todo make it differently, for more than 1 param in endpoint
        var endpoint = "/my/bank/items" + sizeParam;
        var request = getRequest(endpoint, token);

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

/*//GetBankItems 1.0
public class GetBankItems {

    public static JSONObject getBankItems(String name) {
        var sizeParam = "?size=" + 100; //todo make it differently, for more than 1 param in endpoint
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/bank/items" + sizeParam;

        try {
            HttpResponse<String> response = Send.get(endpoint, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                jsonObject.put("statusCode", response.statusCode());
                return jsonObject;
            }
            globalErrorHandler(response, endpoint);
            return new JSONObject().put("statusCode", response.statusCode());

        } catch (Exception getBankItemsException) {
            System.err.println(endpoint + " | Exception: " + getBankItemsException.getMessage());
            return null;
        }
    }
}*/
