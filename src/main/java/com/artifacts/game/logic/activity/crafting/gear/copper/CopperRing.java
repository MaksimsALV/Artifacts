package com.artifacts.game.logic.activity.crafting.gear.copper;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionCrafting;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem;
import org.json.JSONArray;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;

public class CopperRing {
    public static void craftingCopperRing() throws InterruptedException {
        if (!statusOk()) {
            System.out.println("\nStatus is not okay, fixing....");
            getCharacter();
            if (badResources()) {
                ActionMove.actionMove(4, 1);
                ActionWithdrawBankItem.actionWithdrawBankItem("copper_bar", 6);
            }
            if (badPosition()) {
                ActionMove.actionMove(1, 3);
            } else {
                System.err.println("No resources in inventory to craft, get resources first, then try again.");
                System.exit(999);
            }
        }

        System.out.println("\nAll issues are fixed. Starting crafting");
        var craftingRingResponse = ActionCrafting.actionCrafting("copper_ring", 1);
        if (craftingRingResponse.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
            actionMove(4, 1);
            var depositResponse = ActionDepositBankItem.actionDepositBankItem();
            if (depositResponse.statusCode() == CODE_SUCCESS) {
                craftingCopperRing();
            } else {
                System.err.println("Fatal error occurred. Exiting..."); //total system killer if I cannot dump inventory and repeat on the loop
                System.exit(999);
            }
        }
    }

    public static boolean statusOk() { //todo need to add if enough mats for crafting logic also
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
            if ("copper_bar".equals(itemName) && itemQuantity > 6) {
                resourcesOK = true;
                break;
            }
        }
        var positionOK = (x == 1 && y == 3);
        return positionOK && resourcesOK;
    }

    public static boolean badPosition() {
        getCharacter(); //todo need to stop relying on getCharacter requests so much and should use previous response data instead
        var x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        var y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        return x != 1 || y != 3;
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
            if ("copper_bar".equals(itemName)) {
                copperBarExists = true;
                copperBarQuantity = itemQuantity;
                break;
            }
        }
        return !copperBarExists || copperBarQuantity < 6;
    }
}
