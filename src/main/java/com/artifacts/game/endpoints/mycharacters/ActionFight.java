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
import static com.artifacts.api.errorhandling.GlobalErrorHandler.globalErrorHandler;


//todo redo using global logic
public class ActionFight {
    //todo need to think if I even need lists for each endpoint like this. Because i work with data directly from response 200 body data
    public static List<HashMap<String, String>> FIGHT = new ArrayList<>();

    public static HttpResponse<String> actionFight() {
        var name = GetCharacter.CHARACTER.get(0).get("name");
        var baseUrl = BaseURL.getBaseUrl("api.baseUrl");
        var endpoint = baseUrl + "/my/" + name + "/action/fight";

        try {
            HttpResponse<String> response = Send.post(endpoint, "", true);
            var responseBody = response.body();

            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);
                var object = new JSONObject(responseBody);
                var responseDataObject = object.getJSONObject("data");
                    var responseCharacterDataObject = responseDataObject.getJSONObject("character");
                    var responseCooldownDataObject = responseDataObject.getJSONObject("cooldown");
                //todo if i will not use lists of actions then maybe i can remove whole bottom logic of putting data into the lists. Because i work with data directly from response 200 body data
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
                var cooldown = responseCooldownDataObject.getInt("remaining_seconds");
                var reason = responseCooldownDataObject.getString("reason");
                var millis = cooldown * 1000;
                if (responseCooldownDataObject.getInt("remaining_seconds") > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(millis);
                } else {
                    globalErrorHandler(response, endpoint);
                }
            }
        } catch (Exception actionFightException) {
            System.err.println(endpoint + " | Exception: " + actionFightException.getMessage());
        }
        return null;
    }
}


//removed functionality

/*
//inventory quantity counter
var responseCharacterInventoryDataObject = responseCharacterDataObject.getJSONArray("inventory");
var numberOfItemsInInventory = 0; //starting point
for (var i = 0; i < responseCharacterInventoryDataObject.length(); i++) {
    var slotObject = responseCharacterInventoryDataObject.getJSONObject(i);
    numberOfItemsInInventory += slotObject.getInt("quantity");
}
HashMap<String, String> inventoryCountMap = new HashMap<>();
inventoryCountMap.put("inventory_count", String.valueOf(numberOfItemsInInventory));
FIGHT.add(inventoryCountMap);
*/