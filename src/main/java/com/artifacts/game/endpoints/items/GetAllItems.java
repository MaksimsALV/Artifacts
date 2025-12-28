package com.artifacts.game.endpoints.items;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class GetAllItems {
    public static JSONObject getAllItems(String craftMaterial, String type) {
        var craftMaterialParameter = (craftMaterial == null || craftMaterial.isBlank()) ? "" : "?craft_material=" + craftMaterial; //if null or blank, put nothing (empty string), else put parameter+value
        var typeParameter = (type == null || type.isBlank()) ? "" : "?type=" + type;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/items" + craftMaterialParameter + typeParameter;
        //var endpoint = baseUrl + "/items" + "?craft_material=" + craftMaterial;

        try {
            HttpResponse<String> response = Send.get(endpoint, false);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                jsonObject.put("statusCode", response.statusCode());
                return jsonObject;
            }
            globalErrorHandler(response, endpoint);
            return new JSONObject().put("statusCode", response.statusCode());

        } catch (Exception getCharacterException) {
            System.err.println(endpoint + " | Exception: " + getCharacterException.getMessage());
            return null;
        }
    }

    public static List<String> getAllUtilityItemsAsList() {
        var response = getAllItems("", "utility").getJSONArray("data");
        List<String> listOfUtilityItemCodes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            listOfUtilityItemCodes.add(response.getJSONObject(i).getString("code"));
        }
        return listOfUtilityItemCodes;
    }
}
