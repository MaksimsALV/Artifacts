package com.artifacts.game.account;

//import org.openapitools.client.model.CharacterSkin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MyCharacters {
    WARRIOR("Max", "men1"),
    MINER("Bjorn", "men2"),
    LUMBERJACK("Axel", "men3"),
    CHEF("Sushimiko", "women1"),
    ALCHEMIST("Linzy", "women2");

    private final String name;
    private final String skin;

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }
}
