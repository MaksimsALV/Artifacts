package com.artifacts.api.endpoints.get;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.tools.Retry.retry;

//GetAllResources 3.0
public class GetAllResources {
    public static JSONObject getAllResources(String skill) {
        var retryCount = 0;
        var size = 100;
        var skillParameter = (skill == null || skill.isBlank()) ? "" : "&skill=" + skill;
        var endpoint = "/resources?size=" + size + skillParameter;
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

    public static List<String> getAllResourcesAsList(String skill) { //todo later replace this with getAllResourcesAsJson
        var response = getAllResources(skill).getJSONArray("data");

        List<JSONObject> listOfItems = new ArrayList<>();
        response.forEach(object -> listOfItems.add((JSONObject) object));
        listOfItems.sort(Comparator.comparingInt(item -> item.getInt("level")));

        List<String> listOfResourceCodes = new ArrayList<>();
        listOfItems.forEach(item -> {
            var drops = item.getJSONArray("drops");
            drops.forEach(dropsObject -> {
                var drop = (JSONObject) dropsObject;
                var code = drop.getString("code");
                listOfResourceCodes.add(code);
            });
        });
        return listOfResourceCodes;
    }

    public static JSONArray getAllResourcesAsJson(String skill) {
        var response = getAllResources(skill).getJSONArray("data");
        var resources = new JSONArray();
        response.forEach(object -> {
            var itemObject = (JSONObject) object;
            resources.put(itemObject);
        });
        return resources;
    }

    public static List<String> getAllResourceCodesAsList() {
        var response = getAllResources(null).getJSONArray("data");
        List<String> listOfResourceCodes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            listOfResourceCodes.add(response.getJSONObject(i).getString("code"));
        }
        return listOfResourceCodes;
    }
}
