package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionTaskCancel {
    public static JSONObject actionTaskCancel(String name) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/task/cancel";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var jsonObject = new JSONObject(response.body());
                jsonObject.put("statusCode", response.statusCode());
                return jsonObject;
            }
            globalErrorHandler(response, endpoint);
            return new JSONObject().put("statusCode", response.statusCode());

        } catch (Exception actionTaskCancelException) {
            System.err.println(endpoint + " | Exception: " + actionTaskCancelException.getMessage());
            return null;
        }
    }
}
