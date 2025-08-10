package com.artifacts.game.logic.activity.crafting.resources;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionCrafting;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import org.json.JSONArray;

import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.tools.Delay.delay;

import static com.artifacts.api.errorhandling.ErrorCodes.*;

public class CopperBar {
    public static void craftCopperBar()  throws InterruptedException {
        if (!statusOk()) {
            System.out.println("\nStatus is not okay, fixing....");
            getCharacter();
            if (badResources()) { //todo need to add loop to go into the bank to get items and repeat again. I can withdraw just inventory cap items (100) so need to repeat walking 24/7
                System.err.println("No resources in inventory to craft, get resources first, then try again.");
                System.exit(999);
            } else if (badPosition()) {
                ActionMove.actionMove(1, 5);
                }
        }
        System.out.println("\nAll issues are fixed. Starting crafting");
        delay(10);
        while (true) {
            var craftingCopperBar = ActionCrafting.actionCrafting("copper_bar", 1);
            if (craftingCopperBar.statusCode() == CODE_MISSING_ITEM) {
                System.err.println("No resources in inventory to craft, get resources first, then try again.");
                System.exit(999);
            }
        }
    }

    public static boolean statusOk() {
        getCharacter();
        var x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        var y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        var inventory = GetCharacter.CHARACTER.get(0).get("inventory");
        var resourcesOK = false; //default state for not having resources for crafting
        var inventoryJSONArray = new JSONArray(inventory);
        for (var i = 0; i < inventoryJSONArray.length(); i++) {
            var item = inventoryJSONArray.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            if ("copper_ore".equals(itemName) && itemQuantity > 10) {
                resourcesOK = true;
                break;
            }
        }
        var positionOK = (x == 1 && y == 5);
        return positionOK && resourcesOK;
    }

    public static boolean badPosition() {
        getCharacter(); //todo need to stop relying on getCharacter requests so much and should use previous response data instead
        var x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        var y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        return x != 1 || y != 5;
    }

    public static boolean badResources() {
        getCharacter(); //todo need to stop relying on getCharacter requests so much and should use previous response data instead
        var inventory = GetCharacter.CHARACTER.get(0).get("inventory");
        var inventoryJSONArray = new JSONArray(inventory);
        var copperBarExists = false;
        var copperBarQuantity = 0;
        for (var i = 0; i < inventoryJSONArray.length(); i++) {
            var item = inventoryJSONArray.getJSONObject(i);
            var itemName = item.getString("code");
            var itemQuantity = item.getInt("quantity");
            if ("copper_ore".equals(itemName)) {
                copperBarExists = true;
                copperBarQuantity = itemQuantity;
                break;
            }
        }
        return !copperBarExists || copperBarQuantity < 10;
    }
}
