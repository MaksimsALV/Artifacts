package com.artifacts.api.endpoints.get;

import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
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

/*    //GetAllResources 2.0
    public class GetAllResources {
        public static JSONObject getAllResources() {
            var retryCount = 0;
            var endpoint = "/resources";
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
        }*/

/*    //GetAllResources 1.0
    public class GetAllResources {
        public static JSONObject getAllResources() {
            var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
            var endpoint = baseUrl + "/resources";

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
        }*/

    public static List<String> getAllResourcesAsList() {
        var response = getAllResources(null).getJSONArray("data");
        List<String> listOfResourceCodes = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            listOfResourceCodes.add(response.getJSONObject(i).getString("code"));
        }
        return listOfResourceCodes;
    }

    public static List<String> getAllMiningResourcesAsList() {
        var response = getAllResources("mining").getJSONArray("data");
        List<String> listOfResourceCodes = new ArrayList<>();
        response.forEach(object -> {
            var item = (JSONObject) object;
            var code = item.getJSONArray("drops").getJSONObject(0).getString("code");
            listOfResourceCodes.add(code);
        });
        return listOfResourceCodes;
    }
}
