package com.artifacts.game.launcher;

import java.io.IOException;

import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.items.GetAllItems.getAllItems;
import static com.artifacts.game.endpoints.items.GetItem.getItem;
import static com.artifacts.game.endpoints.maps.GetAllMaps.getAllMaps;
import static com.artifacts.game.endpoints.monsters.GetAllMonsters.getAllMonsters;
import static com.artifacts.game.endpoints.myaccount.GetBankItems.getBankItems;
import static com.artifacts.game.endpoints.mycharacters.ActionAcceptNewTask.actionAcceptNewTask;
import static com.artifacts.game.endpoints.mycharacters.ActionCompleteTask.actionCompleteTask;
import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionEquipItem.actionEquipItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionGathering.actionGathering;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.endpoints.mycharacters.ActionTaskCancel.actionTaskCancel;
import static com.artifacts.game.endpoints.mycharacters.ActionUseItem.actionUseItem;
import static com.artifacts.game.endpoints.mycharacters.GetCharacterLogs.getCharacterLogs;
import static com.artifacts.game.endpoints.mycharacters.GetMyCharacters.getMyCharacters;
import static com.artifacts.game.endpoints.resources.GetAllResources.getAllResources;
import static com.artifacts.game.endpoints.serverdetails.GetServerDetails.getServerDetails;
import static com.artifacts.game.endpoints.tasks.GetTask.getTask;
import static com.artifacts.game.endpoints.token.Token.getToken;
import static com.artifacts.game.endpoints.token.Token.token;
//import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
//import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;


public class runMethodDebug {
    public static void main(String[] args) throws InterruptedException, IOException {
        getToken();
        System.out.println(getAllResources().toString(2));
    }
}
