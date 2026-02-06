package com.artifacts.api.endpoints.get;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.artifacts.api.endpoints.get.GetAllItems.getAllItems;
//import static com.artifacts.api.endpoints.get.GetAllResources.getAllGatheringResourcesAsList;
import static com.artifacts.api.endpoints.get.GetAllResources.getAllResourcesAsList;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.tools.Retry.retry;

//GetBankItems 3.0
public class GetBankItems {
    public final HashMap<String, Integer> ALL_BANK_ITEMS = new HashMap();

    public JSONObject getBankItems() {
        var retryCount = 0;
        var size = 100;
        var page = 1;
        var endpoint = "/my/bank/items?size=" + size + "&page=" + page;
        var request = getRequest(endpoint, token);

        while (true) {
            try {
                HttpResponse<String> response = send(request);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var responseBody = new JSONObject(response.body());
                    var currentPage = responseBody.getInt("page");
                    var totalPages = responseBody.getInt("pages");

                    while (true) {
                        if (currentPage == totalPages) {
                            responseBody.put("statusCode", response.statusCode());
                            var responseBodyData = responseBody.getJSONArray("data");
                            responseBodyData.forEach(object -> {
                                var item = (JSONObject) object;
                                var code = item.getString("code");
                                var quantity = item.getInt("quantity");
                                ALL_BANK_ITEMS.put(code, quantity);
                            });
                            return responseBody;
                        } else if (currentPage < totalPages) {
                            responseBody.put("statusCode", response.statusCode());
                            var responseBodyData = responseBody.getJSONArray("data");
                            responseBodyData.forEach(object -> {
                                var item = (JSONObject) object;
                                var code = item.getString("code");
                                var quantity = item.getInt("quantity");
                                ALL_BANK_ITEMS.put(code, quantity);
                            });
                            endpoint = "/my/bank/items?size=" + size + "&page=" + (currentPage + 1);
                            request = getRequest(endpoint, token);
                            HttpResponse<String> nextPageResponse = send(request);

                            if (nextPageResponse.statusCode() == CODE_SUCCESS) {
                                System.out.println(endpoint + " | " + CODE_SUCCESS);
                                responseBody = new JSONObject(nextPageResponse.body());
                                currentPage = responseBody.getInt("page");
                                totalPages = responseBody.getInt("pages");
                                continue;
                            }
                            globalErrorHandler(nextPageResponse, endpoint);
                            return new JSONObject().put("statusCode", nextPageResponse.statusCode());
                        }
                        return null;
                    }

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

    public HashMap<String, Integer> countMiningResourcesFromBankAsHashMap() {
        getBankItems();
        var codes = getAllResourcesAsList("mining");
        var resources = new HashMap<String, Integer>();

        codes.stream()
                .filter(code -> code.endsWith("_ore") || code.equals("coal"))
                .distinct()
                .forEach(code -> resources.put(code, ALL_BANK_ITEMS.getOrDefault(code, 0)));
        return resources;
    }

    public HashMap<String, Integer> countWoodcuttingResourcesFromBankAsHashMap() {
        getBankItems();
        var codes = getAllResourcesAsList("woodcutting");
        var resources = new HashMap<String, Integer>();

        codes.stream()
                .filter(code -> code.endsWith("_wood"))
                .distinct()
                .forEach(code -> resources.put(code, ALL_BANK_ITEMS.getOrDefault(code, 0)));
        return resources;
    }

    public HashMap<String, Integer> countFishingResourcesFromBankAsHashMap() {
        getBankItems();
        var resources = new HashMap<String, Integer>();
        resources.put("gudgeon", ALL_BANK_ITEMS.get("gudgeon"));
        resources.put("shrimp", ALL_BANK_ITEMS.get("shrimp"));
        resources.put("trout", ALL_BANK_ITEMS.get("trout"));
        resources.put("bass", ALL_BANK_ITEMS.get("bass"));
        resources.put("salmon", ALL_BANK_ITEMS.get("salmon"));
        resources.put("swordfish", ALL_BANK_ITEMS.get("swordfish"));
        return resources;
    }

    public HashMap<String, Integer> countHerbsFromBankAsHashMap() {
        getBankItems();
        var resources = new HashMap<String, Integer>();
        resources.put("sunflower", ALL_BANK_ITEMS.get("sunflower"));
        resources.put("nettle_leaf", ALL_BANK_ITEMS.get("nettle_leaf"));
        resources.put("glowstem_leaf", ALL_BANK_ITEMS.get("glowstem_leaf"));
        resources.put("torch_cactus_flower", ALL_BANK_ITEMS.get("torch_cactus_flower"));
        return resources;
    }
}

/*//GetBankItems 2.0
public class GetBankItems {

    public static JSONObject getBankItems() {
        var retryCount = 0;
        var sizeParam = "?size=" + 100; //todo make it differently, for more than 1 param in endpoint
        var endpoint = "/my/bank/items" + sizeParam;
        var request = getRequest(endpoint, token);

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
}*/

/*//GetBankItems 1.0
public class GetBankItems {

    public static JSONObject getBankItems(String name) {
        var sizeParam = "?size=" + 100; //todo make it differently, for more than 1 param in endpoint
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/bank/items" + sizeParam;

        try {
            HttpResponse<String> response = Send.get(endpoint, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                jsonObject.put("statusCode", response.statusCode());
                return jsonObject;
            }
            globalErrorHandler(response, endpoint);
            return new JSONObject().put("statusCode", response.statusCode());

        } catch (Exception getBankItemsException) {
            System.err.println(endpoint + " | Exception: " + getBankItemsException.getMessage());
            return null;
        }
    }
}*/
