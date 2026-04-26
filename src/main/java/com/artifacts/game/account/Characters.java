package com.artifacts.game.account;

import org.openapitools.client.model.CharacterSkin;

public enum Characters {
    WARRIOR("Max", CharacterSkin.MEN1),
    MINER("Bjorn", CharacterSkin.MEN2),
    LUMBERJACK("Axel", CharacterSkin.MEN3),
    CHEF("Sushimiko", CharacterSkin.WOMEN1),
    ALCHEMIST("Linzy", CharacterSkin.WOMEN2);

    private final String name;
    private final CharacterSkin skin;
    Characters(String name, CharacterSkin skin) {
        this.name = name;
        this.skin = skin;
    }

    public String getName() {
        return name;
    }

    public CharacterSkin getSkin() {
        return skin;
    }
}
