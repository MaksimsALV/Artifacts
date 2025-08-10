package com.artifacts.game.logic.activity.crafting.resources;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionCrafting;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem;
import org.json.JSONArray;

import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.tools.Delay.delay;

import static com.artifacts.api.errorhandling.ErrorCodes.*;

public class CopperBar {
    public static void craftCopperBar()  throws InterruptedException {
        if (!statusOk()) {
            System.out.println("\nStatus is not okay, fixing....");
            getCharacter();
            if (badResources()) {
                ActionWithdrawBankItem.actionWithdrawBankItem("copper_ore", 100);
            }
            if (badPosition()) {
                ActionMove.actionMove(1, 5);
            } else {
            System.err.println("No resources left to craft, get resources first, then try again.");
            System.exit(999);
        }
        }
        System.out.println("\nAll issues are fixed. Starting crafting");
        //delay(10);
        while (true) {
            var craftingCopperBar = ActionCrafting.actionCrafting("copper_bar", 1);
            if (craftingCopperBar.statusCode() == CODE_SUCCESS) {
                continue;
            }

            if (craftingCopperBar.statusCode() == CODE_MISSING_ITEM) {
                ActionMove.actionMove(4, 1);
                var withdrawingItem = ActionWithdrawBankItem.actionWithdrawBankItem("copper_ore", 100);
                if (withdrawingItem.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                    var depositResponse = ActionDepositBankItem.actionDepositBankItem();
                    if (depositResponse.statusCode() == CODE_SUCCESS) { //if NPE happens, add fightResponse.statusCode() != null &&...
                        //ActionWithdrawBankItem.actionWithdrawBankItem("copper_ore", 100);
                        craftCopperBar();
                        return; //quitting the current loop to start status checking again
                    } else {
                        System.err.println("Fatal error occurred. Exiting..."); //total system killer if I cannot dump inventory and repeat on the loop
                        System.exit(999);
                    }
                }
                craftCopperBar();
                return;
            } else {
                System.err.println("Fatal error occurred. Exiting..."); //total system killer if I cannot dump inventory and repeat on the loop
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
