package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionGatheringWIP {
    public static JSONObject actionGathering(String name) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/gathering";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                return new JSONObject(response.body());
            }
            globalErrorHandler(response, endpoint);
            return null;

        } catch (Exception actionGatheringException) {
            System.err.println(endpoint + " | Exception: " + actionGatheringException.getMessage());
            return null;
        }
    }
}