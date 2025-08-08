package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class ActionDepositBankItem {
    public static HashMap<String, Integer> ITEMS_FOR_DEPOSIT = new HashMap<>();
    public static HttpResponse<String> actionDepositBankItem() {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/bank/deposit/item";
        getItemsForBankDeposit();
        actionDepositBankItemBody();
        return null; //need to remove later
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
