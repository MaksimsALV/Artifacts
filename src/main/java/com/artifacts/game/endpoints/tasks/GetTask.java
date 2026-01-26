package com.artifacts.game.endpoints.tasks;

import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.tools.Retry.retry;

//GetTask 2.0
public class GetTask {
    public static JSONObject getTask(String taskCode) {
        var retryCount = 0;
        var endpoint = "/tasks/list/" + taskCode;
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
}

/*
//GetTask 1.0
public class GetTask {
    public static JSONObject getTask(String taskCode) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/tasks/list/" + taskCode;

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

        } catch (Exception getTaskException) {
            System.err.println(endpoint + " | Exception: " + getTaskException.getMessage());
            return null;
        }
    }
}*/
