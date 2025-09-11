package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.crafting.Crafting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.logic.activity.crafting.Crafting.craft;

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
        //craftGear(getWarrior(), "weaponcrafting", "iron_sword", 10);
        //gather(getWarrior(), "iron_rocks");
        craft(getWarrior(), "weaponcrafting", "iron_sword", 10);
        //Fighting2.fight(getWarrior(), "skeleton", "small_health_potion", "");

    }
}
