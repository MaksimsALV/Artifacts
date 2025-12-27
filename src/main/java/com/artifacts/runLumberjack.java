//package com.artifacts;
//
//import com.artifacts.game.engine.launcher.Login;
//import com.artifacts.game.logic.activity.crafting.Crafting;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import static com.artifacts.game.library.characters.Characters.getLumberjack;
//import static com.artifacts.game.library.characters.Characters.getMiner;
//import static com.artifacts.game.logic.activity.crafting.Crafting.craft;
//import static com.artifacts.game.logic.activity.fighting.Fighting.fight;
//import static com.artifacts.game.logic.activity.gathering.Gathering.gather;
//
//@SpringBootApplication
//public class runLumberjack {
//    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("server.port", "8082");
//        SpringApplication.run(runLumberjack.class, args);
//
//        /// !!!
//        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
//        /// !!!
//
//        Login.login();
//        System.out.println("\nLogin successful");
//        System.out.println("\ninitial Character data is received successfully");
//
//        //fight(getLumberjack(), "", "", "", "cooked_trout", true); //use either one: fightTask tor activityLocation
//
//        //gather(getLumberjack(), "ash_tree");
//        //gather(getLumberjack(), "spruce_tree");
//        gather(getLumberjack(), "birch_tree");
//
//        //gather(getLumberjack(), "copper_rocks");
//        //gather(getLumberjack(), "iron_rocks");
//
//        //craft(getLumberjack(), "woodcutting", "ash_plank", 10);
//        //craft(getLumberjack(), "woodcutting", "spruce_plank", 10);
//        //craft(getLumberjack(), "woodcutting", "hardwood_plank", 10);
//    }
//}
