package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.library.characters.Characters.getLumberjack;
import static com.artifacts.game.library.characters.Characters.getChef;
import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.logic.activity.crafting.Crafting.craft;
import static com.artifacts.game.logic.activity.fighting.Fighting.fight;
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


    public static void runWarrior(String action, String value, String utilityOne, String utilityTwo) {
        warriorThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getWarrior(), value, utilityOne, utilityTwo, "cooked_trout", false);
                } else if (action.equals("gather")) {
                    gather(getWarrior(), value);
                } else {
                    craft(getWarrior(), "gearcrafting", "tromatising_mask", 1);
                }
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


    public static void runMiner(String action, String value, String utilityOne, String utilityTwo) {
        minerThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMiner(), value, utilityOne, utilityTwo, "cooked_trout", false);
                } else if (action.equals("gather")) {
                    gather(getMiner(), value);
                } else {
                    craft(getMiner(), "gearcrafting", "tromatising_mask", 1);
                }
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


    public static void runLumberjack(String action, String value, String utilityOne, String utilityTwo) {
        lumberjackThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getLumberjack(), value, utilityOne, utilityTwo, "cooked_trout", false);
                } else if (action.equals("gather")) {
                    gather(getLumberjack(), value);
                } else {
                    craft(getLumberjack(), "gearcrafting", "tromatising_mask", 1);
                }
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


    public static void runChef(String action, String value, String utilityOne, String utilityTwo) {
        chefThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getChef(), value, utilityOne, utilityTwo, "cooked_trout", false);
                } else if (action.equals("gather")) {
                    gather(getChef(), value);
                } else {
                    craft(getChef(), "gearcrafting", "tromatising_mask", 1);
                }
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


    public static void runAlchemist(String action, String value, String utilityOne, String utilityTwo) {
        alchemistThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getAlchemist(), value, utilityOne, utilityTwo, "cooked_trout", false);
                } else if (action.equals("gather")) {
                    gather(getAlchemist(), value);
                } else {
                    craft(getAlchemist(), "gearcrafting", "tromatising_mask", 1);
                }
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
