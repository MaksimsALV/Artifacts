package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.logic.leveling.L5;

public class MainForConsole {
    public static void main(String[] args) throws Exception {
        Login.login();
        GetMyCharacters.getMyCharacters();
        GetCharacter.getCharacter();
        L5.runLoop();
    }
}
