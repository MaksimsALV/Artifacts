package com.artifacts;

import com.artifacts.game.launcher.Launcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.artifacts.api.endpoints.get.GetAllItems.getAllConsumablesAsList;
import static com.artifacts.api.endpoints.post.Token.getToken;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllConsumableItems;
//import static com.artifacts.game.library.items.GetItemsByItemType.getAllUtilityItems;
//import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
//import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;

@SpringBootTest(classes = Launcher.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MethodDebug {
    @Test
    void debug() {
//        getToken();
        System.out.println(getAllConsumablesAsList());
    }
}
