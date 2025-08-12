package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;

public class ActionMoveWIP {
    public static JSONObject actionMove(String name, int x, int y) {
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/move";
        var body = actionMoveBody(x, y);

        try {
            HttpResponse<String> response = Send.post(endpoint, body, true);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
                    //var responseCharacterDataObject = responseDataObject.getJSONObject("character"); not being currently used here
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");

                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                var reason = responseCooldownDataObject.getString("reason");;
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                }
                return responseDataObject;
            }
            globalErrorHandler(response, endpoint);
            return null;

        } catch (Exception actionMoveException) {
            System.err.println(endpoint + " | Exception: " + actionMoveException.getMessage());
            return null;
        }
    }

    public static String actionMoveBody(int x, int y) {
        var json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json.toString();
    }
}
