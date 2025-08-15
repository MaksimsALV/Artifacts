package com.artifacts.game.library.monsters;

import java.util.Map;

public final class Monsters {
    public static final Map<String, int[]> MONSTERS = Map.of(
            "CHICKEN", new int[]{0, 1},
            "GREEN_SLIME", new int[]{0, -1},
            "SHEEP", new int[]{5, 12}
            //add more
    );
}
