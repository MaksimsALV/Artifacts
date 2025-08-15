package com.artifacts.game.library.workshops;

import java.util.Map;

public final class Workshops {
    public static final Map<String, int[]> WORKSHOPS = Map.of(
            "WOODCUTTING", new int[]{-2, -3},
            "COOKING", new int[]{1, 1},
            "WEAPONCRAFTING", new int[]{2, 1},
            "GEARCRAFTING", new int[]{3, 1},
            "JEWELRYCRAFTING", new int[]{1, 3},
            "FORGE", new int[]{1, 5}
            //add more
    );
}
