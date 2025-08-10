package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.MiningCopper;
import com.artifacts.game.logic.activity.crafting.gear.copper.CopperBoots;
import com.artifacts.game.logic.activity.crafting.gear.copper.CopperHelmet;
import com.artifacts.game.logic.activity.crafting.gear.copper.CopperRing;
import com.artifacts.game.logic.activity.crafting.resources.CopperBar;
import com.artifacts.game.logic.activity.crafting.weapons.CopperDagger;
import com.artifacts.game.logic.leveling.L5;

import static com.artifacts.tools.Delay.delay;

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
        //CopperBar.craftCopperBar();
        //CopperDagger.craftingCopperDagger();
        //CopperBoots.craftingCopperBoots();
        //CopperHelmet.craftingCopperHelmet();
        CopperRing.craftingCopperRing();

        //gathering
        //MiningCopper.miningCopperLoop();

        //fighting
        //L5.runLoop();
    }
}
