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
public class runApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(runApplication.class, args);
    }

    private static Thread warriorThread;
    private static Thread minerThread;
    private static Thread lumberjackThread;
    private static Thread chefThread;
    private static Thread alchemistThread;


    public static void runWarrior() {
        warriorThread = new Thread(() -> {
            try {
                Login.login();
                System.out.println("\nLogin successful");
                System.out.println("\ninitial Character data is received successfully");
                gather(getWarrior(), "coal_rocks");
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
                Login.login();
                System.out.println("\nLogin successful");
                System.out.println("\ninitial Character data is received successfully");
                gather(getMiner(), "coal_rocks");
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
                Login.login();
                System.out.println("\nLogin successful");
                System.out.println("\ninitial Character data is received successfully");
                gather(getLumberjack(), "birch_tree");
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
                Login.login();
                System.out.println("\nLogin successful");
                System.out.println("\ninitial Character data is received successfully");
                gather(getChef(), "trout_spot");
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
                Login.login();
                System.out.println("\nLogin successful");
                System.out.println("\ninitial Character data is received successfully");
                gather(getAlchemist(), "nettle");
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
