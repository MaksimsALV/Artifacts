package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.fighting.Fighting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
import static com.artifacts.game.logic.activity.fighting.Fighting.fight;
import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;
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

        //fight(getWarrior(), "skeleton");
        fightTask(getWarrior(), "");
        //craftGear(getWarrior(), "gearcrafting", "copper_helmet", 16);
        //Gathering.gather(getWarrior(), "copper_rocks");
    }
}
