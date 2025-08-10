package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.crafting.gear.copper.CopperLegs;
import com.artifacts.game.logic.activity.crafting.shields.WoodenShield;

public class MainForConsole {
    public static void main(String[] args) throws Exception {
        Login.login();
        System.out.println("\nLogin successful");
        //delay(5);

        GetMyCharacters.getMyCharacters();
        GetCharacter.getCharacter();
        System.out.println("\ninitial Character data is received successfully");
        //delay(5);
        //ActionDepositBankItem.actionDepositBankItem();

        //crafting
        //CopperDagger.craftingCopperDagger();
        //CopperBoots.craftingCopperBoots();
        //CopperHelmet.craftingCopperHelmet();
        CopperLegs.craftingCopperLegs();
        //CopperRing.craftingCopperRing();
        //WoodenShield.craftingWoodenShield();

        //resources
        //CopperBar.craftCopperBar();
        //AshWood.craftAshPlank();

        //gathering
        //MiningCopper.miningCopperLoop();
        //WoodcuttingAshTree.woodcuttingAshTreeLoop();

        //fighting
        //L5.runLoop();
    }
}
