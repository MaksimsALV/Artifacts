package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.library.characters.Characters.getLumberjack;
import static com.artifacts.game.library.characters.Characters.getChef;
import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.logic.activity.gathering.Gathering.gather;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(Launcher.class, args);
        Login.login();
        System.out.println("\nLogin successful");
        System.out.println("\ninitial Character data is received successfully");
    }

    private static Thread warriorThread;
    private static Thread minerThread;
    private static Thread lumberjackThread;
    private static Thread chefThread;
    private static Thread alchemistThread;


    public static void runWarrior() {
        warriorThread = new Thread(() -> {
            try {
                gather(getWarrior(), "coal_rocks");
//                fight(getWarrior(), "ogre", "minor_health_potion", "", "cooked_trout", false); //use either one: fightTask tor activityLocation
//                craft(getWarrior(), "gearcrafting", "tromatising_mask", 1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getWarrior() + " thread");
        warriorThread.start();
    }
    public static void stopWarrior() {
        if (warriorThread != null) {
            warriorThread.interrupt();
            System.out.println(getWarrior() + " thread stopped");
        }
    }


    public static void runMiner() {
        minerThread = new Thread(() -> {
            try {
                gather(getMiner(), "coal_rocks");
//                fight(getMiner(), "", "", "", "cooked_trout", true); //use either one: fightTask tor activityLocation
//                craft(getMiner(), "mining", "copper_bar", 10);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMiner() + " thread");
        minerThread.start();
    }
    public static void stopMiner() {
        if (minerThread != null) {
            minerThread.interrupt();
            System.out.println(getMiner() + " thread stopped");
        }
    }


    public static void runLumberjack() {
        lumberjackThread = new Thread(() -> {
            try {
                gather(getLumberjack(), "birch_tree");
//                fight(getLumberjack(), "", "", "", "cooked_trout", true); //use either one: fightTask tor activityLocation
//                craft(getLumberjack(), "woodcutting", "ash_plank", 10);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getLumberjack() + " thread");
        lumberjackThread.start();
    }
    public static void stopLumberjack() {
        if (lumberjackThread != null) {
            lumberjackThread.interrupt();
            System.out.println(getLumberjack() + " thread stopped");
        }
    }


    public static void runChef() {
        chefThread = new Thread(() -> {
            try {
                gather(getChef(), "trout_spot");
//                craft(getChef(), "cooking", "cooked_trout", 100);
//                fight(getChef(), "chicken");
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getChef() + " thread");
        chefThread.start();
    }
    public static void stopChef() {
        if (chefThread != null) {
            chefThread.interrupt();
            System.out.println(getChef() + " thread stopped");
        }
    }


    public static void runAlchemist() {
        alchemistThread = new Thread(() -> {
            try {
                gather(getAlchemist(), "nettle");
//                craft(getAlchemist(), "alchemy", "air_boost_potion", 16);

            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getAlchemist() + " thread");
        alchemistThread.start();
    }
    public static void stopAlchemist() {
        if (alchemistThread != null) {
            alchemistThread.interrupt();
            System.out.println(getAlchemist() + " thread stopped");
        }
    }


}
