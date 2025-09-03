package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.gathering.Gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.*;

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
            try {
                //Fighting.fight(getWarrior(), "sheep");
                //craftGear(getWarrior(), "weaponcrafting", "copper_dagger", 16);
                Gathering.gather(getWarrior(), "copper_rocks");

            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread2 = new Thread(() -> { //Miner
            try {
                Gathering.gather(getMiner(), "copper_rocks");
                //craftResources(getMiner(), "mining", "copper_bar", 10);
                //craftGear(getMiner(), "jewelrycrafting", "copper_ring", 16);
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread3 = new Thread(() -> { //Alchemist
            try {
                Gathering.gather(getAlchemist(), "sunflower_field");
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread4 = new Thread(() -> { //Lumberjack
            try {
                Gathering.gather(getLumberjack(), "ash_tree");
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread5 = new Thread(() -> { //Chef
            try {
                Gathering.gather(getChef(), "gudgeon_fishing_spot");
            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
