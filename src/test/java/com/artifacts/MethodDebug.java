package com.artifacts;

import com.artifacts.api.endpoints.get.GetBankItems;
import com.artifacts.game.Launcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.artifacts.api.endpoints.get.GetMyCharacters;

import static com.artifacts.api.endpoints.get.GetAllResources.*;
import static com.artifacts.api.endpoints.post.Token.getToken;
import static com.artifacts.game.logic.activity.Gathering.gather;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllConsumableItems;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllUtilityItems;
//import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
//import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;

@SpringBootTest(classes = Launcher.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MethodDebug {
    @Test
    void debug() throws InterruptedException {
        getToken();
//        System.out.println(getAllResourcesAsList("mining"));

//        GetBankItems getBankItems = new GetBankItems();
//        System.out.println(getBankItems.countMiningResourcesFromBankAsJson());
//        System.out.println(getBankItems.countMiningResourcesFromBankAsHashMap());
        gather("Max", null, true);
//        System.out.println(getBankItems.countWoodcuttingResourcesFromBankAsHashMap());
//        System.out.println(getBankItems.countFishingResourcesFromBankAsHashMap());
//        System.out.println(getBankItems.countHerbsFromBankAsHashMap());
//        GetMyCharacters getMyCharacters = new GetMyCharacters();
//        System.out.println(getMyCharacters.getMyCharactersAsList());
//        System.out.println("warrior name: " + getMyCharacters.getWarrior());

    }
}
