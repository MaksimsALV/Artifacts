package com.artifacts.api.endpoints.post;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.get.GetCharacter.getCharacter;
import static com.artifacts.api.endpoints.post.Token.token;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllConsumableItems;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllUtilityItems;
import static com.artifacts.tools.Retry.retry;

//ActionDepositBankItem 2.0
public class ActionDepositBankItem {
    public static JSONObject actionDepositBankItem(String name) {
        var retryCount = 0;
        var endpoint = "/my/" + name + "/action/bank/deposit/item";
        var requestBody = getItemsForBankDeposit(name).toString();
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

    public static JSONArray getItemsForBankDeposit(String name) {
        var getCharacterResponseObject = getCharacter(name); //getting latest character data for deposit
        var inventory = getCharacterResponseObject.getJSONObject("data").getJSONArray("inventory");
        for (var i = 0; i < inventory.length(); i++) {
            var item = inventory.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");

            if (!itemName.isEmpty() && itemQuantity > 0) {
                item.remove("slot");
            } else {
                inventory.remove(i);
                i--;
            }
        }
        return inventory;
    }
}