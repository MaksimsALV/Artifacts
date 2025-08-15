package com.artifacts;

//import com.artifacts.game.endpoints.characters.GetCharacter;
//import com.artifacts.game.endpoints.characters.GetCharacterWIP;
//import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.fighting.Fighting;
import com.artifacts.game.logic.activity.gathering.Gathering;

import static com.artifacts.game.library.characters.Characters.*;

public class MainForConsole {
    public static void main(String[] args) throws Exception {

        /// !!!
        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
        /// !!!

        Login.login();
        System.out.println("\nLogin successful");
        //delay(5);

        System.out.println("\ninitial Character data is received successfully");
        //delay(5);

        //crafting
        //Crafting.craft();

        //gathering
        Gathering.gather(getGatherer(), "COPPER");

        //fighting
        //Fighting.fight(getWarrior(), "GREEN_SLIME");

        //todo
        Thread thread1 = new Thread(() -> {
            try {
                Gathering.gather(getGatherer(), "COPPER");
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                Gathering.gather(getGatherer(), "COPPER");
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        //thread1.start();
        //thread2.start();
    }
}
