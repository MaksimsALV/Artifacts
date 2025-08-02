package com.artifacts;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;
import com.artifacts.game.engine.launcher.Login;
import com.artifacts.game.engine.logic.strategy.level.Level1;

public class MainForConsole {
    public static void main(String[] args) throws Exception {
        Login.login();
        GetMyCharacters.getMyCharacters();
        GetCharacter.getCharacter();
        Level1.runLevel1();
    }
}
