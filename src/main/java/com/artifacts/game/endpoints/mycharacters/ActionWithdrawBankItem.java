package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.myaccount.GetBankItems;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionWithdrawBankItem {
    public static HashMap<String, Integer> ITEMS_TO_WITHDRAW = new HashMap<>();
    public static HttpResponse<String> actionWithdrawBankItem(String itemCode, Integer quantity) {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/bank/withdraw/item";
        //getItemsForBankWithdraw();
        ITEMS_TO_WITHDRAW.clear();
        ITEMS_TO_WITHDRAW.put(itemCode, quantity);
        var requestBody = actionWithdrawBankItemBody();

        try {
            HttpResponse<String> response = Send.post(endpoint, requestBody, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                var responseCharacterDataObject = responseDataObject.getJSONObject("character"); //not really need character data here I think, because I just do thing and move away
                var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                var reason = responseCooldownDataObject.getString("reason");
                var millis = cooldown * 1000;
                if (responseCooldownDataObject.getInt("remaining_seconds") > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(millis);
                }
                return response;
            }
            globalErrorHandler(response, endpoint);
            return response;

        } catch (Exception actionWithdrawBankItemException) {
            System.err.println(endpoint + " | Exception: " + actionWithdrawBankItemException.getMessage());
        }
        return null;
    }

    public static String actionWithdrawBankItemBody() { //creating an array of bodies to deposit request
        var jsonArray = new JSONArray();
        for (var entry : ITEMS_TO_WITHDRAW.entrySet()) { //looping via the list, entrySet() returns all key-value pairs in the hashMap.
            var jsonObject = new JSONObject();
            jsonObject.put("code", entry.getKey()); //chicken
            jsonObject.put("quantity", entry.getValue()); //5
            jsonArray.put(jsonObject); //putting {chicken=5} as object into []
        }
        return jsonArray.toString(); //returning whole array with all objects as a string for the request
    }
/*
    public static void getItemsForBankWithdraw() {
        ITEMS_TO_WITHDRAW.clear();
        GetBankItems.getBankItems(""); //getting latest bank data for certain item (optional parameter), else gets all items
        var bankInventory = GetBankItems.BANK_ITEMS.get(0).get("data");
        var bankInventoryJSONArray = new JSONArray(bankInventory);
        for (var i = 0; i < bankInventoryJSONArray.length(); i++) {
            var item = bankInventoryJSONArray.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            if (!itemName.isEmpty() && itemQuantity > 0) {
                ITEMS_TO_WITHDRAW.put(itemName, itemQuantity);
            }
        }
    }

 */
}
