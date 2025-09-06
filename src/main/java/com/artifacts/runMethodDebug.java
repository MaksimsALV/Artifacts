package com.artifacts;

import com.artifacts.game.engine.launcher.Login;

import static com.artifacts.game.logic.activity.fighting.FightingTask.fightTask;
import static com.artifacts.game.logic.activity.tasks.GetFightTask.getFightTask;

public class runMethodDebug {
    public static void main(String[] args) throws InterruptedException {
        Login.login();
        var activityLocation = getFightTask("MaxiTheGuy", "monsters");
        fightTask("MaxiTheGuy", activityLocation);
    }
}
