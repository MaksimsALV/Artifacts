package com.artifacts;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.activity.crafting.CraftingGear;
import com.artifacts.game.logic.activity.fighting.Fighting;
import com.artifacts.game.logic.activity.gathering.Gathering;

import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.library.characters.Characters.*;
import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
import static com.artifacts.game.logic.activity.crafting.CraftingResources.craftResources;

public class MainForConsole {
    public static void main(String[] args) throws Exception {

        /// !!!
        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
        /// !!!

        Login.login();
        System.out.println("\nLogin successful");
        System.out.println("\ninitial Character data is received successfully");

        Thread thread1 = new Thread(() -> { //Warrior
            try {
                Fighting.fight(getWarrior(), "wolf");
                //craftGear(getWarrior(), "weaponcrafting", "copper_dagger", 10);

            } catch (InterruptedException threadException) {
                throw new RuntimeException(threadException);
            }
        });
        Thread thread2 = new Thread(() -> { //Miner
            try {
                Gathering.gather(getMiner(), "copper_rocks");
                //craftResources(getMiner(), "mining", "copper_bar", 10);
                //craftGear(getMiner(), "JEWELRYCRAFTING", "COPPER_RING", "COPPER_BAR");
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
