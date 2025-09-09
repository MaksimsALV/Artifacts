package com.artifacts.game.endpoints.myaccount;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

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
}
