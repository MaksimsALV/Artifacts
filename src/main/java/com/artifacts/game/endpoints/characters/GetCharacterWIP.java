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
    public enum Role {
        WARRIOR(Characters.getWarrior()),
        GATHERER(Characters.getGatherer());

        public final String name;

        Role(String name) {
            this.name = name;
        }

        public static final List<HashMap<String, Object>> CHARACTER_WARRIOR = new ArrayList<>();
        public static final List<HashMap<String, Object>> CHARACTER_GATHERER = new ArrayList<>();

        public static HttpResponse<String> getCharacter(Role role) {
            var name = role.name;
            var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
            var endpoint = baseUrl + "/characters/" + name;
            try {
                HttpResponse<String> response = Send.get(endpoint, false);

                if (response.statusCode() == CODE_SUCCESS) {
                    System.out.println(endpoint + " | " + CODE_SUCCESS);

                    var object = new JSONObject(response.body());
                    var responseDataObject = object.getJSONObject("data");

                    HashMap<String, Object> characterData = new HashMap<>();
                    responseDataObject.keySet().forEach(key -> {
                        var value = responseDataObject.get(key);
                        characterData.put(key, value);
                    });

                    if (role == WARRIOR) {
                        CHARACTER_WARRIOR.clear();
                        CHARACTER_WARRIOR.add(characterData);
                    } else if (role == GATHERER) {
                        CHARACTER_GATHERER.clear();
                        CHARACTER_GATHERER.add(characterData);
                    }

                    var cooldownExpirationMillis = convertCooldownExpirationTimeToMillis();
                    var cooldown = cooldownExpirationMillis - System.currentTimeMillis();
                    var seconds = cooldown / 1000;
                    if (cooldown > 0) {
                        // TODO: Add reason from response
                        System.out.println(name + " is now on a cooldown for: " + seconds + "s");
                        Thread.sleep(cooldown);
                        return response;
                    }

                    return response;
                }

                globalErrorHandler(response, endpoint);
                return response;

            } catch (Exception getCharacterException) {
                System.err.println(endpoint + " | Exception: " + getCharacterException.getMessage());
            }
            return null;
        }
    }
}