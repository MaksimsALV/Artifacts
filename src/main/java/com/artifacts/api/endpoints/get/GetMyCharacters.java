package com.artifacts.api.endpoints.get;

import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.api.http.Client.getRequest;
import static com.artifacts.api.http.Client.send;
import static com.artifacts.api.endpoints.post.Token.token;
import static com.artifacts.tools.Retry.retry;

//GetMyCharacters 2.0
public class GetMyCharacters {
    public final HashMap<String, String> MY_CHARACTERS = new HashMap<>();

    public JSONObject getMyCharacters() {
        var retryCount = 0;
        var endpoint = "/my/characters";
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

    public HashMap<String, String> getMyCharactersLabelAndNameAsHashMap() {
        var response = getMyCharacters().optJSONArray("data");
        MY_CHARACTERS.clear();
        for (var i = 0; i < response.length(); i++) {
            var characterName = response.optJSONObject(i).optString("name");

            if ("Max".equals(characterName)) {
                MY_CHARACTERS.put("WARRIOR", characterName);
            } else if ("Bjorn".equals(characterName)) {
                MY_CHARACTERS.put("MINER", characterName);
            } else if ("Axel".equals(characterName)) {
                MY_CHARACTERS.put("LUMBERJACK", characterName);
            } else if ("Sushimiko".equals(characterName)) {
                MY_CHARACTERS.put("CHEF", characterName);
            } else if ("Linzy".equals(characterName)) {
                MY_CHARACTERS.put("ALCHEMIST", characterName);
            }
        }
        return MY_CHARACTERS;
    }

    public String getWarrior() {
        return MY_CHARACTERS.get("WARRIOR");
    }
    public String getMiner() {
        return MY_CHARACTERS.get("MINER");
    }
    public String getLumberjack() {
        return MY_CHARACTERS.get("LUMBERJACK");
    }
    public String getChef() {
        return MY_CHARACTERS.get("CHEF");
    }
    public String getAlchemist() {
        return MY_CHARACTERS.get("ALCHEMIST");
    }
}