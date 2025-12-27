//package com.artifacts;
//
//import com.artifacts.game.engine.launcher.Login;
//import com.artifacts.game.logic.activity.crafting.Crafting;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//
//import static com.artifacts.game.library.characters.Characters.getMiner;
//import static com.artifacts.game.library.characters.Characters.getWarrior;
//import static com.artifacts.game.logic.activity.crafting.Crafting.craft;
//import static com.artifacts.game.logic.activity.fighting.Fighting.fight;
//import static com.artifacts.game.logic.activity.gathering.Gathering.gather;
//import static com.artifacts.game.logic.activity.fighting.Fighting.fight;
//
//
//@SpringBootApplication
//public class runMiner {
//    private static Thread minerThread;
//
//    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("server.port", "8081");
//        SpringApplication.run(runMiner.class, args);
//    }
//
//    public static void runMiner() {
//        minerThread = new Thread(() -> {
//            try {
//                Login.login();
//                System.out.println("\nLogin successful");
//                System.out.println("\ninitial Character data is received successfully");
//                gather(getMiner(), "coal_rocks");
//            } catch (InterruptedException ie) {
//                Thread.currentThread().interrupt();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        minerThread.start();
//    }
//
//    public static void stopMiner() {
//        if (minerThread != null) {
//            minerThread.interrupt();
//            System.out.println("\nminer thread stopped");
//        }
//    }
//}
//
//
//    public static void runMiner() throws InterruptedException {
//        /// !!!
//        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
//        /// !!!
//        Login.login();
//        System.out.println("\nLogin successful");
//        System.out.println("\ninitial Character data is received successfully");
//
//        gather(getMiner(), "coal_rocks");
//    }
//
//    public static void stopMiner() throws InterruptedException {
//        System.exit(0);
//    }
//}




        //fight(getMiner(), "", "", "", "cooked_trout", true); //use either one: fightTask tor activityLocation

        //gather(getMiner(), "copper_rocks");
        //gather(getMiner(), "iron_rocks");


        //craft(getMiner(), "mining", "copper_bar", 10);
        //craft(getMiner(), "mining", "iron_bar", 14);
        //craft(getMiner(), "mining", "steel_bar", 10);

        //craft(getMiner(), "jewelrycrafting", "fire_ring", 2);


