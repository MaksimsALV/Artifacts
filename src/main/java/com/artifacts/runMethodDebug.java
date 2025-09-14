package com.artifacts;

import com.artifacts.game.engine.launcher.Login;

import static com.artifacts.game.endpoints.items.GetAllItems.getAllItems;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.library.characters.Characters.getMiner;
import static com.artifacts.game.library.characters.Characters.getWarrior;
//import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;
import static com.artifacts.game.logic.activity.tasks.GetFightTask.getFightTask;
import static com.artifacts.service.ConsumableManager.checkInventoryConsumables;

public class runMethodDebug {
    public static void main(String[] args) throws InterruptedException {
        Login.login();
        //var activityLocation = getFightTask("MaxiTheGuy", "monsters");
        //fightTask("MaxiTheGuy", activityLocation);
        //fightTask(getWarrior(), ""); //call this just to start fightTask logic, activityLocation is blank as expected
        //getAllItems("", "utility");
        //actionDepositBankItem(getMiner());
 //       craftGear(getWarrior(), "weaponcrafting", "iron_sword", 16);
        checkInventoryConsumables(getWarrior(), "cooked_gudgeon");

    }
}
