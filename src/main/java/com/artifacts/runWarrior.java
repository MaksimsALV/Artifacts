package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.crafting.Crafting;
//import com.artifacts.game.logic.activity.fighting.Fighting2;
import com.artifacts.game.logic.activity.fighting.Fighting3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getLumberjack;
import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.logic.activity.crafting.Crafting.craft;
//import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;
import static com.artifacts.game.logic.activity.gathering.Gathering.gather;

@SpringBootApplication
public class runWarrior {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("server.port", "8080");
        SpringApplication.run(runWarrior.class, args);

        /// !!!
        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
        /// !!!

        Login.login();
        System.out.println("\nLogin successful");
        System.out.println("\ninitial Character data is received successfully");

        //fight(getWarrior(), "chicken");
        //fightTask(getWarrior(), "");
        //craft(getWarrior(), "weaponcrafting", "steel_battleaxe", 1);
        //gather(getWarrior(), "iron_rocks");
        //craft(getWarrior(), "gearcrafting", "iron_boots", 10);
        //craft(getWarrior(), "mining", "iron_bar", 15);
        //Fighting2.fight(getWarrior(), "wolf", "", "", "cooked_gudgeon");
        Fighting3.fight(getWarrior(), "skeleton", "", "", "cooked_shrimp", false); //use either one: fightTask tor activityLocation


    }
}
