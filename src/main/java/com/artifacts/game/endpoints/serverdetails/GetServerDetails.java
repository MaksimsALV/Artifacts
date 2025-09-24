package com.artifacts.game.endpoints.serverdetails;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.tools.Delay.delay;
import static com.artifacts.tools.Retry.retry;

public class GetServerDetails {
    public static boolean serverIsUp;
    public static HashMap<String, Integer> season = new HashMap<>();

//    public static HttpResponse<String> getServerDetails() {
//        //todo should redo the logic similar to other endpoints
//        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
//        var endpoint = baseUrl + "/";
//        var count = 0;
//        var retry = 7;
//
//        while (true) {
//            try {
//                HttpResponse<String> response = Send.get(endpoint, false);
//
//                if (response.statusCode() == 200) {
//                    serverIsUp = true;
//                    return response;
//                } else {
//                    serverIsUp = false;
//                    System.err.println("getServerDetails unexpected status code: " + response.statusCode());
//                }
//            } catch (Exception getServerDetailsError) {
//                serverIsUp = false;
//                System.err.println("Exception getServerDetailsError: " + getServerDetailsError.getMessage());
//                if (++count >= retry) {
//                    return null;
//                }
//                try {
//                    delay(5);
//                } catch (InterruptedException ie) {
//                    throw new RuntimeException(ie);
//                }
//            }
//            return null;
//        }
//    }

    //getServerDetails2.0
    public static JSONObject getServerDetails() {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/";
        var retryCount = 0;
//        var retryAmount = 7;
//        var retryDelay = 10;

        while (true) {
            try {
                HttpResponse<String> response = Send.get(endpoint, false);

                if (response.statusCode() == CODE_SUCCESS) {
                    serverIsUp = true;
                    System.out.println(endpoint + " | " + CODE_SUCCESS);
                    var jsonObject = new JSONObject(response.body());
                    jsonObject.put("statusCode", response.statusCode());
                    var seasonNumber = jsonObject.getJSONObject("data").getJSONObject("season").getInt("number");
                    season.put("season", seasonNumber);
                    return jsonObject;
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
//                if (++count >= retryAmount) {
//                    return null;
//                }
//                delay(retryDelay);
//            }
        }
    }
}
