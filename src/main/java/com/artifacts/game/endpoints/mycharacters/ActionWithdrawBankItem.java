package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.endpoints.myaccount.GetBankItems.*;

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
    }
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
