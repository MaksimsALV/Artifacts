package com.artifacts.game.endpoints.items;

import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.tools.Retry.retry;

//GetAllItems 2.0
public class GetAllItems {
    public static JSONObject getAllItems(String craftMaterial, String type) {
        var retryCount = 0;
        var craftMaterialParameter = (craftMaterial == null || craftMaterial.isBlank()) ? "" : "?craft_material=" + craftMaterial; //if null or blank, put nothing (empty string), else put parameter+value
        var typeParameter = (type == null || type.isBlank()) ? "" : "?type=" + type;
        var endpoint = "/items" + craftMaterialParameter + typeParameter;
        var request = getRequest(endpoint, null);

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

    //GetAllItems 1.0
    /*
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
     */

    public static List<String> getAllUtilityItemsAsList() {
        var response = getAllItems("", "utility").getJSONArray("data");
        List<String> listOfUtilityItemCodes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            listOfUtilityItemCodes.add(response.getJSONObject(i).getString("code"));
        }
        return listOfUtilityItemCodes;
    }

    public static List<String> getAllConsumablesAsList() {
        var response = getAllItems("", "consumable").getJSONArray("data");
        List<String> listOfConsumableCodes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            listOfConsumableCodes.add(response.getJSONObject(i).getString("code"));
        }
        return listOfConsumableCodes;
    }
}
