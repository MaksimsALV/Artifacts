package com.artifacts.game;

import com.artifacts.api.endpoints.get.GetMyCharacters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.artifacts.api.endpoints.get.GetServerDetails.getServerDetails;
import static com.artifacts.api.endpoints.get.GetServerDetails.serverIsUp;
import static com.artifacts.api.endpoints.post.Token.getToken;
//import static com.artifacts.game.library.characters.Characters.getMiner;
//import static com.artifacts.game.library.characters.Characters.getWarrior;
//import static com.artifacts.game.library.characters.Characters.getLumberjack;
//import static com.artifacts.game.library.characters.Characters.getChef;
//import static com.artifacts.game.library.characters.Characters.getAlchemist;
import static com.artifacts.game.logic.activity.Crafting.craft;
import static com.artifacts.game.logic.activity.Fighting.fight;
import static com.artifacts.game.logic.activity.Gathering.gather;

@SpringBootApplication(scanBasePackages = "com.artifacts")
//Launcher 2.0
public class Launcher {
    static final GetMyCharacters getMyCharacters = new GetMyCharacters();

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
        getServerDetails();
        if (serverIsUp) {
            System.out.println("\nServer is UP. Retrieving token...");
            getToken();
            getMyCharacters.getMyCharactersLabelAndNameAsHashMap();
        } else {
            System.exit(0);
        }
    }

    private Thread warriorThread;
    private Thread minerThread;
    private Thread lumberjackThread;
    private Thread chefThread;
    private Thread alchemistThread;


    public void runWarrior(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll, String craftingItem, Integer craftingQuantity) {
        warriorThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMyCharacters.getWarrior(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMyCharacters.getWarrior(), activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                } else {
                    craft(getMyCharacters.getWarrior(), activityLocation, craftingItem, craftingQuantity);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMyCharacters.getWarrior() + " thread");
        warriorThread.start();
    }
    public void stopWarrior() {
        if (warriorThread != null) {
            warriorThread.interrupt();
            System.out.println(getMyCharacters.getWarrior() + " thread stopped");
        }
    }


    public void runMiner(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll, String craftingItem, Integer craftingQuantity) {
        minerThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMyCharacters.getMiner(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMyCharacters.getMiner(), activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                } else {
                    craft(getMyCharacters.getMiner(), activityLocation, craftingItem, craftingQuantity);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMyCharacters.getMiner() + " thread");
        minerThread.start();
    }
    public void stopMiner() {
        if (minerThread != null) {
            minerThread.interrupt();
            System.out.println(getMyCharacters.getMiner() + " thread stopped");
        }
    }


    public void runLumberjack(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll, String craftingItem, Integer craftingQuantity) {
        lumberjackThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMyCharacters.getLumberjack(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMyCharacters.getLumberjack(), activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                } else {
                    craft(getMyCharacters.getLumberjack(), activityLocation, craftingItem, craftingQuantity);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMyCharacters.getLumberjack() + " thread");
        lumberjackThread.start();
    }
    public void stopLumberjack() {
        if (lumberjackThread != null) {
            lumberjackThread.interrupt();
            System.out.println(getMyCharacters.getLumberjack() + " thread stopped");
        }
    }


    public void runChef(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll, String craftingItem, Integer craftingQuantity) {
        chefThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMyCharacters.getChef(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMyCharacters.getChef(), activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                } else {
                    craft(getMyCharacters.getChef(), activityLocation, craftingItem, craftingQuantity);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMyCharacters.getChef() + " thread");
        chefThread.start();
    }
    public void stopChef() {
        if (chefThread != null) {
            chefThread.interrupt();
            System.out.println(getMyCharacters.getChef() + " thread stopped");
        }
    }


    public void runAlchemist(String action, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll, String craftingItem, Integer craftingQuantity) {
        alchemistThread = new Thread(() -> {
            try {
                if (action.equals("fight")) {
                    fight(getMyCharacters.getAlchemist(), activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                } else if (action.equals("gather")) {
                    gather(getMyCharacters.getAlchemist(), activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                } else {
                    craft(getMyCharacters.getAlchemist(), activityLocation, craftingItem, craftingQuantity);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }, getMyCharacters.getAlchemist() + " thread");
        alchemistThread.start();
    }
    public void stopAlchemist() {
        if (alchemistThread != null) {
            alchemistThread.interrupt();
            System.out.println(getMyCharacters.getAlchemist() + " thread stopped");
        }
    }
}
