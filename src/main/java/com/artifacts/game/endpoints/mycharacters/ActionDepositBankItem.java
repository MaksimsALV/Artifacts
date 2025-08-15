package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;

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
    }

    public static JSONArray getItemsForBankDeposit(String name) {
        var getCharacterResponseObject = getCharacter(name); //getting latest character data for deposit
        var inventory = getCharacterResponseObject.getJSONObject("data").getJSONArray("inventory"); //1). might need to store inventory object in CHARACTER list as im not doing it rn, not sure
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