/*
package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.fighting.Fighting;
import com.artifacts.game.logic.activity.gathering.Gathering;
import com.artifacts.service.ThreadRegistry;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.*;
import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
import static com.artifacts.game.logic.activity.crafting.CraftingResources.craftResources;
import static com.artifacts.tools.Delay.delay;

@SpringBootApplication
public class ArtifactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtifactsApplication.class, args);

        /// !!!
        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
        /// !!!

        Login.login();
        System.out.println("\nLogin successful");
        System.out.println("\ninitial Character data is received successfully");


        Thread thread1 = new Thread(() -> { //Warrior
            //Runnable warrior = () -> {
                try {
                Fighting.fight(getWarrior(), "highwayman");
                //craftGear(getWarrior(), "gearcrafting", "copper_helmet", 16);
                //Gathering.gather(getWarrior(), "copper_rocks");

            } catch (InterruptedException threadException) {
                //throw new RuntimeException(threadException);
                Thread.currentThread().interrupt();
            }
        });
        //Runnable miner = () -> {
            Thread thread2 = new Thread(() -> { //Miner
            try {
                Gathering.gather(getMiner(), "iron_rocks");
                //Gathering.gather(getMiner(), "iron_rocks");
                //craftResources(getMiner(), "mining", "copper_bar", 10);
                //craftGear(getMiner(), "jewelrycrafting", "copper_ring", 16);
            } catch (InterruptedException threadException) {
                //throw new RuntimeException(threadException);
                Thread.currentThread().interrupt();
            }
        });
        //Runnable alchemist = () -> {
        Thread thread3 = new Thread(() -> { //Alchemist
            try {
                Gathering.gather(getAlchemist(), "sunflower_field");
            } catch (InterruptedException threadException) {
                //throw new RuntimeException(threadException);
                Thread.currentThread().interrupt();
            }
        });
        //Runnable lumberjack = () -> {
            Thread thread4 = new Thread(() -> { //Lumberjack
            try {
                Gathering.gather(getLumberjack(), "spruce_tree");
            } catch (InterruptedException threadException) {
                //throw new RuntimeException(threadException);
                Thread.currentThread().interrupt();
            }
        });
        //Runnable chef = () -> {
            Thread thread5 = new Thread(() -> { //Chef
            try {
                Gathering.gather(getChef(), "shrimp_fishing_spot");
            } catch (InterruptedException threadException) {
                //throw new RuntimeException(threadException);
                Thread.currentThread().interrupt();
            }
        });

//        Thread thread6 = new Thread(() -> {
//            while (true) {
//                try {
//                        DBController.saveCharacterData(GetCharacter.getCharacter(getWarrior().getJSONObject("data")));
//                        delay(10);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });
//
//
//        ThreadRegistry.ThreadTasks.register("warrior", warrior);
//        ThreadRegistry.ThreadTasks.register("miner", miner);
//        ThreadRegistry.ThreadTasks.register("alchemist", alchemist);
//        ThreadRegistry.ThreadTasks.register("lumberjack", lumberjack);
//        ThreadRegistry.ThreadTasks.register("chef", chef);
//
//        new Thread(warrior, "warrior").start();
//        new Thread(miner, "miner").start();
//        new Thread(alchemist, "alchemist").start();
//        new Thread(lumberjack, "lumberjack").start();
//        new Thread(chef, "chef").start();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
//        thread6.start();
    }
}

 */
