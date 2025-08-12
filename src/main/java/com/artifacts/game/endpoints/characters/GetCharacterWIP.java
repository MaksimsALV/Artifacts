package com.artifacts.game.endpoints.characters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.config.Characters;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;
import static com.artifacts.tools.Converter.convertCooldownExpirationTimeToMillis;

public class GetCharacterWIP {
    //public static final List<HashMap<String, Object>> CHARACTER_WARRIOR = new ArrayList<>();
    //public static final List<HashMap<String, Object>> CHARACTER_GATHERER = new ArrayList<>();

    public static JSONObject getCharacter(String name) {
        //var name = role.name;
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/characters/" + name;
        try {
            HttpResponse<String> response = Send.get(endpoint, false);

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);

                var object = new JSONObject(response.body());
                var responseDataObject = object.getJSONObject("data");
/*
                HashMap<String, Object> characterData = new HashMap<>();
                responseDataObject.keySet().forEach(key -> {
                    var value = responseDataObject.get(key);
                    characterData.put(key, value);
                });

                if (name.equals(Characters.getWarrior())) {
                    CHARACTER_WARRIOR.clear();
                    CHARACTER_WARRIOR.add(characterData);
                } else if (name.equals(Characters.getGatherer())) {
                    CHARACTER_GATHERER.clear();
                    CHARACTER_GATHERER.add(characterData);
                }


// totally removing whole block, as getCharacter doesnt return normally cooldown anyway. so i need to launch the app when char initially isnt on cooldown, then in theory I dont need getCharacterWIP at all even
                //var cooldownExpirationMillis = convertCooldownExpirationTimeToMillis();
                //var cooldown = convertCooldownExpirationTimeToMillis() - System.currentTimeMillis();
                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                if (cooldown > 0) {
                    // TODO: Add reason from response
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s");
                    //Thread.sleep(cooldown);
                    //return responseDataObject;
                }
                /*
 */
                return responseDataObject;
            }
            globalErrorHandler(response, endpoint);
            return null;

        } catch (Exception getCharacterException) {
            System.err.println(endpoint + " | Exception: " + getCharacterException.getMessage());
            return null;
        }
    }
}