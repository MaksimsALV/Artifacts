package com.artifacts.game.launcher;

import java.io.IOException;

import static com.artifacts.api.endpoints.get.GetAllResources.getAllResources;
import static com.artifacts.api.endpoints.post.Token.getToken;
//import static com.artifacts.game.logic.activity.crafting.CraftingGear.craftGear;
//import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;


public class runMethodDebug {
    public static void main(String[] args) throws InterruptedException, IOException {
        getToken();
        System.out.println(getAllResources().toString(2));
    }
}
