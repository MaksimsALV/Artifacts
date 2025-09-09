package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.gathering.Gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
import static com.artifacts.game.logic.activity.gathering.Gathering.gather;

@SpringBootApplication
public class runAlchemist {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("server.port", "8084");
        SpringApplication.run(runAlchemist.class, args);

        /// !!!
        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
        /// !!!

        Login.login();
        System.out.println("\nLogin successful");
        System.out.println("\ninitial Character data is received successfully");

        Gathering.gather(getAlchemist(), "sunflower_field");
        //craftGear(getAlchemist(), "alchemy", "small_health_potion", 33);
    }
}
