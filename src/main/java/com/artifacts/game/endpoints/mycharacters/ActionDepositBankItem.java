package com.artifacts.game.endpoints.mycharacters;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.postRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.items.GetAllItems.getAllItems;
import static com.artifacts.game.endpoints.token.Token.token;
import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.library.characters.Characters.getChef;
import static com.artifacts.game.library.items.GetItemsByItemType.getAllConsumableItems;
import static com.artifacts.game.library.items.GetItemsByItemType.getAllUtilityItems;
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

/*    //ActionDepositBankItem 1.0
    public class ActionDepositBankItem {
        public static JSONObject actionDepositBankItem(String name) {
            var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
            var endpoint = baseUrl + "/my/" + name + "/action/bank/deposit/item";
            var requestBody = getItemsForBankDeposit(name).toString();

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

            } catch (Exception actionDepositBankItemException) {
                System.err.println(endpoint + " | Exception: " + actionDepositBankItemException.getMessage());
                return null;
            }
        }*/

    public static JSONArray getItemsForBankDeposit(String name) {
        var getCharacterResponseObject = getCharacter(name); //getting latest character data for deposit
        var inventory = getCharacterResponseObject.getJSONObject("data").getJSONArray("inventory"); //1). might need to store inventory object in CHARACTER list as im not doing it rn, not sure

//        var itemTypeUtilityData = getAllItems("", "utility");
//        var itemTypeUtilityDataArray = itemTypeUtilityData.getJSONArray("data");
//        List<String> utilityCodes = new ArrayList<>();
//        for (var j = 0; j < itemTypeUtilityDataArray.length(); j++) {
//            var itemCode = itemTypeUtilityDataArray.getJSONObject(j).getString("code");
//            utilityCodes.add(itemCode);
//        }
        var utilityItem = getAllUtilityItems();
        var consumableItem = getAllConsumableItems();
        for (var i = 0; i < inventory.length(); i++) {
            var item = inventory.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            //if (!itemName.isEmpty() && itemQuantity > 0 || itemName.equals("small_health_potion")) { //todo testing small health pot filtering from depositing if have in inventory. if works, change getAllItems type=utility
            //if (!itemName.isEmpty() && itemQuantity > 0 ) {

            //if (!itemName.isEmpty() && itemQuantity > 0 && (name.equals(getChef()) || !consumableItem.contains(itemName)) && (name.equals(getAlchemist()) || !utilityItem.contains(itemName))) { //todo testing
            if (!itemName.isEmpty() && itemQuantity > 0) { //todo testing
                item.remove("slot");
            } else {
                inventory.remove(i);
                i--;
            }
        }
        return inventory;
    }
}