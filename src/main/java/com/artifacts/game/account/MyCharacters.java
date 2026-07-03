package com.artifacts.game.account;

//import org.openapitools.client.model.CharacterSkin;

public enum MyCharacters {
    WARRIOR("Max", "men1"),
    MINER("Bjorn", "men2"),
    LUMBERJACK("Axel", "men3"),
    CHEF("Sushimiko", "women1"),
    ALCHEMIST("Linzy", "women2");

    private final String name;
    private final String skin;
    MyCharacters(String name, String skin) {
        this.name = name;
        this.skin = skin;
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }
}
