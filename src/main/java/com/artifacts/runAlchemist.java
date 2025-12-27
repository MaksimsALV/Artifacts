//package com.artifacts;
//
//import com.artifacts.game.engine.launcher.Login;
//import com.artifacts.game.logic.activity.gathering.Gathering;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import static com.artifacts.game.library.characters.Characters.getAlchemist;
//import static com.artifacts.game.library.characters.Characters.getMiner;
////import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
//import static com.artifacts.game.logic.activity.crafting.Crafting.craft;
//import static com.artifacts.game.logic.activity.gathering.Gathering.gather;
//
//@SpringBootApplication
//public class runAlchemist {
//    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("server.port", "8084");
//        SpringApplication.run(runAlchemist.class, args);
//
//        /// !!!
//        /// IMPORTANT start the game only when no Chars are on cooldown, else it will break. This is intended and part of the design model
//        /// !!!
//
//        Login.login();
//        System.out.println("\nLogin successful");
//        System.out.println("\ninitial Character data is received successfully");
//
//        //gather(getAlchemist(), "sunflower_field");
//        gather(getAlchemist(), "nettle");
//
//        //craft(getAlchemist(), "alchemy", "air_boost_potion", 16);
//        //craft(getAlchemist(), "alchemy", "minor_health_potion", 33);
//        //craft(getAlchemist(), "alchemy", "small_antidote", 33);
//    }
//}
