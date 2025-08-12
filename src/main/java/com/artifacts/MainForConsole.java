package com.artifacts;

import com.artifacts.game.config.Characters;
import com.artifacts.game.endpoints.characters.GetCharacter;
//import com.artifacts.game.endpoints.characters.GetCharacterWIP;
//import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.fighting.L1_10.GreenSlime;
import com.artifacts.game.logic.activity.gathering.mining.Mining;

import static com.artifacts.game.config.Characters.*;

public class MainForConsole {
    public static void main(String[] args) throws Exception {
        Login.login();
        System.out.println("\nLogin successful");
        //delay(5);

        //GetMyCharacters.getMyCharacters();
        //GetCharacter.getCharacter();
        //GetCharacterWIP.getCharacter(Characters.getWarrior());
        System.out.println("\ninitial Character data is received successfully");
        //delay(5);
        //ActionDepositBankItem.actionDepositBankItem();

        //crafting
        //CopperDagger.craftingCopperDagger();
        //CopperBoots.craftingCopperBoots();
        //CopperHelmet.craftingCopperHelmet();
        //CopperLegs.craftingCopperLegs();
        //CopperRing.craftingCopperRing();
        //WoodenShield.craftingWoodenShield();

        //resources
        //CopperBar.craftCopperBar();
        //AshWood.craftAshPlank();

        //gathering
        Mining.miningCopper(getWarrior());
        //MiningCopper.miningCopperLoop();
        //WoodcuttingAshTree.woodcuttingAshTreeLoop();

        //fighting
        //GreenSlime.runLoop();
    }
}
