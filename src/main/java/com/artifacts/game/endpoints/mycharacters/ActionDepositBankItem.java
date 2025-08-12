package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.config.Characters.getWarrior;

public class ActionDepositBankItem {
    public static HashMap<String, Integer> ITEMS_FOR_DEPOSIT = new HashMap<>();
    public static HttpResponse<String> actionDepositBankItem() {
        //var name = GetCharacter.CHARACTER.get(0).get("name");
        var name = getWarrior();
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/bank/deposit/item";
        getItemsForBankDeposit();
        var requestBody = actionDepositBankItemBody();
        try {
            HttpResponse<String> response = Send.post(endpoint, requestBody, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                //var responseCharacterDataObject = responseDataObject.getJSONObject("character"); //not really need character data here I think, because I just do thing and move away
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

        } catch (Exception actionDepositBankItemException) {
            System.err.println(endpoint + " | Exception: " + actionDepositBankItemException.getMessage());
        }
        return null;
    }

    public static String actionDepositBankItemBody() { //creating an array of bodies to deposit request
        var jsonArray = new JSONArray();
        for (var entry : ITEMS_FOR_DEPOSIT.entrySet()) { //looping via the list, entrySet() returns all key-value pairs in the hashMap.
            var jsonObject = new JSONObject();
            jsonObject.put("code", entry.getKey()); //chicken
            jsonObject.put("quantity", entry.getValue()); //5
            jsonArray.put(jsonObject); //putting {chicken=5} as object into []
        }
        return jsonArray.toString(); //returning whole array with all objects as a string for the request
    }

    public static void getItemsForBankDeposit() {
        ITEMS_FOR_DEPOSIT.clear();
        GetCharacter.getCharacter(); //getting latest character data
        var inventory = GetCharacter.CHARACTER.get(0).get("inventory"); //1). might need to store inventory object in CHARACTER list as im not doing it rn, not sure
        var inventoryJSONArray = new JSONArray(inventory); //parsing inventory String into JSONArray
        for (var i = 0; i < inventoryJSONArray.length(); i++) {
            var item = inventoryJSONArray.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            if (!itemName.isEmpty() && itemQuantity > 0) {
                ITEMS_FOR_DEPOSIT.put(itemName, itemQuantity);
            }
        }
    }
}
