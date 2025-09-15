package com.artifacts.game.logic.activity.tasks;

import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.tasks.GetTask.getTask;

public class FightTaskLimitator {
    public static boolean fightTaskIsValidForExecution(String taskCode) throws InterruptedException {
        var taskMonsterLevel = getTask(taskCode).getJSONObject("data").getInt("level");
        if (taskMonsterLevel <= 19) {
            return true;
        } return false;
    }
}
