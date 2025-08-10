package com.artifacts.game.endpoints.myaccount;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class GetBankItems {
    public static List<HashMap<String, String>> BANK_ITEMS = new ArrayList<>();

    public static HttpResponse<String> getBankItems(String itemCode) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/bank/items";

        if (itemCode != null && !itemCode.isBlank()) {
            endpoint += "?item_code=" + itemCode; //adding optional parameter if needed
        }

        try {
            HttpResponse<String> response = Send.get(endpoint, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                var responseDataArray = jsonObject.getJSONArray("data");
                BANK_ITEMS.clear();

                for (var itemObject : responseDataArray) {
                    var eachItem = (JSONObject) itemObject;
                    HashMap<String, String> itemData = new HashMap<>();
                    for (var key : eachItem.keySet()) {
                        var value = eachItem.get(key).toString();
                        itemData.put(key, value);
                    }
                    BANK_ITEMS.add(itemData);
                }
                return response;
            }
            globalErrorHandler(response, endpoint);
            return response;

        } catch (Exception getBankItemsException) {
            System.err.println(endpoint + " | Exception: " + getBankItemsException.getMessage());
        }
        return null;
    }
}
