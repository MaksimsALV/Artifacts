package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionCrafting {
    public static List<HashMap<String, String>> CRAFTING = new ArrayList<>();

    public static HttpResponse<String> actionCrafting(String item, int quantity) {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/crafting";
        var requestBody = actionCraftingBody(item, quantity);

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

        } catch (Exception actionCraftingException) {
            System.err.println(endpoint + " | Exception: " + actionCraftingException.getMessage());
        }
        return null;
    }

    public static String actionCraftingBody(String item, int quantity) {
        var jsonObject = new JSONObject();
        jsonObject.put("code", item);
        jsonObject.put("quantity", quantity);
        return jsonObject.toString();
    }
}
