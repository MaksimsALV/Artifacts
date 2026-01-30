package com.artifacts.game.logic.activity.tasks;

import static com.artifacts.api.endpoints.get.GetTask.getTask;

public class FightTaskLimitator {
    public static boolean fightTaskIsValidForExecution(String taskCode) throws InterruptedException {
        var taskMonsterLevel = getTask(taskCode).getJSONObject("data").getInt("level");
        if (taskMonsterLevel <= 16) {
            return true;
        } return false;
    }
}
