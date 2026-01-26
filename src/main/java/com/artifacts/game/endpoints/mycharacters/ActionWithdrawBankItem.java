package com.artifacts.game.endpoints.mycharacters;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.game.endpoints.myaccount.GetBankItems.*;
import static com.artifacts.game.endpoints.token.Token.token;
import static com.artifacts.tools.Retry.retry;

//ActionWithdrawBankItem 2.0
public class ActionWithdrawBankItem {
    public static JSONObject actionWithdrawBankItem(String name, String itemCode, Integer quantity) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/bank/withdraw/item";
        var requestBody = new JSONArray()
                .put(new JSONObject()
                        .put("code", itemCode)
                        .put("quantity", quantity))
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

/*//ActionWithdrawBankItem 1.0
public class ActionWithdrawBankItem {
    public static JSONObject actionWithdrawBankItem(String name, String itemCode, Integer quantity) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/bank/withdraw/item";
        var requestBody = new JSONArray()
                .put(new JSONObject()
                        .put("code", itemCode)
                        .put("quantity", quantity))
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

        } catch (Exception actionWithdrawBankItemException) {
            System.err.println(endpoint + " | Exception: " + actionWithdrawBankItemException.getMessage());
            return null;
        }
    }*/
/*
    public static JSONArray getItemsForBankWithdraw(String name) { //creating an array of bodies to withdraw request
        var getBankItemsResponseObject = getBankItems(name);
        var bankInventory = getBankItemsResponseObject.getJSONArray("data");
        for (var i = 0; i < bankInventory.length(); i++) { //looping via the list, entrySet() returns all key-value pairs in the hashMap.
            var item = bankInventory.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            if (!itemName.isEmpty() && itemQuantity > 0) {
                item.remove("slot");
            } else {
                bankInventory.remove(i);
                i--;
            }
        }
        return bankInventory;
    }

 */
}
