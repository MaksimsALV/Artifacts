package com.artifacts.game.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.api.endpoints.get.GetServerDetails.getServerDetails;
import static com.artifacts.api.endpoints.get.GetServerDetails.serverIsUp;
import static com.artifacts.api.endpoints.post.Token.getToken;
import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.library.characters.Characters.getWarrior;
import static com.artifacts.game.library.characters.Characters.getLumberjack;
import static com.artifacts.game.library.characters.Characters.getChef;
import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.logic.activity.Crafting.craft;
import static com.artifacts.game.logic.activity.Fighting.fight;
import static com.artifacts.game.logic.activity.Gathering.gather;

@SpringBootApplication(scanBasePackages = "com.artifacts")
public class Launcher {
    public static void main(String[] args) {
          //login 1.0
//        System.setProperty("server.port", "8080");
//        SpringApplication.run(Launcher.class, args);
//        Login.login();
//        System.out.println("\nLogin successful");
//        System.out.println("\ninitial Character data is received successfully");

        //login 2.0
        SpringApplication.run(Launcher.class, args);
        getServerDetails();
        if (serverIsUp) {
            System.out.println("\nServer is UP. Retrieving token...");
            getToken();
        } else {
            System.exit(0);
        }
    }

    private static Thread warriorThread;
    private static Thread minerThread;
    private static Thread lumberjackThread;
    private static Thread chefThread;
    private static Thread alchemistThread;


    public static void runWarrior(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) {
        warriorThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getWarrior(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getWarrior(), activityLocation);
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


    public static void runMiner(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) {
        minerThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMiner(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMiner(), activityLocation);
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


    public static void runLumberjack(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) {
        lumberjackThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getLumberjack(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getLumberjack(), activityLocation);
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


    public static void runChef(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) {
        chefThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getChef(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getChef(), activityLocation);
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


    public static void runAlchemist(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) {
        alchemistThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getAlchemist(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getAlchemist(), activityLocation);
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
