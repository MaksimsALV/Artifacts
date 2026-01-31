package com.artifacts.api.endpoints.get;

import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
//import static com.artifacts.helpers.Delay.delay;
import static com.artifacts.api.http.Client.*;
import static com.artifacts.helpers.Retry.retry;

//GetServerDetails3.0
public class GetServerDetails {
    public static boolean serverIsUp;
    public static HashMap<String, Integer> season = new HashMap<>();

    public static JSONObject getServerDetails() {
        var retryCount = 0;
        var endpoint = "/";
        var request = getRequest(endpoint, null);

        while (true) {
            try {
                HttpResponse<String> response = send(request);

                if (response.statusCode() == CODE_SUCCESS) {
                    serverIsUp = true;
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var responseBody = new JSONObject(response.body());
                    responseBody.put("statusCode", response.statusCode());
                    var seasonNumber = responseBody.getJSONObject("data").getJSONObject("season").getInt("number");
                    season.put("season", seasonNumber);
                    return responseBody;
                }
                globalErrorHandler(response, endpoint);
                serverIsUp = false;
                return new JSONObject().put("statusCode", response.statusCode());

            } catch (Exception e) {
                serverIsUp = false;
                System.err.println("Exception getServerDetailsError: " + e);
                if (!retry(++retryCount)) {
                    return null;
                }
            }
        }
    }
}

    //getServerDetails2.0
//    public static JSONObject getServerDetails() {
//        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
//        var endpoint = baseUrl + "/";
//        var retryCount = 0;
////        var retryAmount = 7;
////        var retryDelay = 10;
//
//        while (true) {
//            try {
//                HttpResponse<String> response = Send.get(endpoint, false);
//
//                if (response.statusCode() == CODE_SUCCESS) {
//                    serverIsUp = true;
//                    System.out.println(endpoint + " | " + CODE_SUCCESS);
//                    var jsonObject = new JSONObject(response.body());
//                    jsonObject.put("statusCode", response.statusCode());
//                    var seasonNumber = jsonObject.getJSONObject("data").getJSONObject("season").getInt("number");
//                    season.put("season", seasonNumber);
//                    return jsonObject;
//                }
//                globalErrorHandler(response, endpoint);
//                serverIsUp = false;
//                return new JSONObject().put("statusCode", response.statusCode());
//
//            } catch (Exception e) {
//                serverIsUp = false;
//                System.err.println("Exception getServerDetailsError: " + e);
//                if (!retry(++retryCount)) {
//                    return null;
//                }
//            }
//        }
//    }
//}
