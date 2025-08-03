package com.artifacts.game.endpoints.mycharacters;

import com.artifacts.api.http.Send;
import com.artifacts.game.config.BaseURL;
import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.tools.Converter;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_IN_COOLDOWN;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;


//todo redo using global logic
public class ActionFight {
    //public static int cooldownTotalSeconds;
    //public static int cooldownRemainingSeconds;
    //public static int hp;
    public static List<HashMap<String, String>> FIGHT = new ArrayList<>();


    public static HttpResponse<String> actionFight() {
        //var name = GetMyCharacters.MY_CHARACTERS.get(0).get("name");
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/fight";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            var responseBody = response.body();

            if (response.statusCode() == CODE_SUCCESS) {
                var object = new JSONObject(responseBody);
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                FIGHT.clear();

                HashMap<String, String> characterData = new HashMap<>();
                for (var key : responseCharacterDataObject.keySet()) {
                    var value = responseCharacterDataObject.get(key).toString();
                    characterData.put(key, value);
                }
                FIGHT.add(characterData);

                HashMap<String, String> cooldownData = new HashMap<>();
                for (var key : responseCooldownDataObject.keySet()) {
                    var value = responseCooldownDataObject.get(key).toString();
                    cooldownData.put(key, value);
                }
                FIGHT.add(cooldownData);

                //cooldownTotalSeconds = responseCooldownDataObject.getInt("total_seconds");
                //cooldownRemainingSeconds = responseCooldownDataObject.getInt("remaining_seconds");
                //hp = responseCharacterDataObject.getInt("hp");
                //return response;
                /*
            } else if (response.statusCode() == 486) {
                System.err.println("actionFight An action is already in progress for this character.");
            } else if (response.statusCode() == 487) {
                System.err.println("actionFight The character's inventory is full.");
            } else if (response.statusCode() == 498) {
                System.err.println("actionFight Character not found.");
            }
                 */

            } else if (response.statusCode() == CODE_CHARACTER_IN_COOLDOWN) {
                var object = new JSONObject(responseBody);
                var responseErrorObject = object.getJSONObject("error");
                var responseErrorMessage = responseErrorObject.getString("message");

                //todo should move whole regex logic somewhere else.
                var errorMessageFinder = Pattern.compile("\\d+(\\.\\d+)?").matcher(responseErrorMessage);
                var seconds = 1.0;
                if (errorMessageFinder.find()) {
                    seconds = Double.parseDouble(errorMessageFinder.group());
                }
                //var time = (long) (seconds * 1000);

                System.err.println("499: actionFight The character is in cooldown: Sleeping for " + seconds + "s and repeating the step again.");
                Thread.sleep(Converter.SecondsToMillisConverter(seconds));
                actionFight();
                /*
            } else if (response.statusCode() == 598) {
                    System.err.println("actionFight Monster not found on this map.");
            } else {
                System.err.println("actionFight unexpected status code: " + response.statusCode() + response.body());
            }
                 */
            }
        } catch (Exception actionFightError) {
            System.err.println("Exception actionFightError: " + actionFightError.getMessage());
        }
        return null;
    }
}
