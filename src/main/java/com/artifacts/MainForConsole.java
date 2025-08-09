package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.MiningCopper;
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
        MiningCopper.miningCopperLoop();
        //L5.runLoop();
    }
}
