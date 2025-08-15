package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionCrafting {
    public static JSONObject actionCrafting(String name, String item, int quantity) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/crafting";
        var requestBody = actionCraftingBody(item, quantity);

        try {
            HttpResponse<String> response = Send.post(endpoint, requestBody, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                return new JSONObject(response.body());
            }
            globalErrorHandler(response, endpoint);
            return null;

        } catch (Exception actionCraftingException) {
            System.err.println(endpoint + " | Exception: " + actionCraftingException.getMessage());
            return null;
        }
    }

    public static String actionCraftingBody(String item, int quantity) {
        var jsonObject = new JSONObject();
        jsonObject.put("code", item);
        jsonObject.put("quantity", quantity);
        return jsonObject.toString();
    }
}
