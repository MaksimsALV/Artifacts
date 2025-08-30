package com.artifacts.game.library.recources;

import java.util.Map;

public final class Resources {
    public static final Map<String, int[]> RESOURCE_LOCATION = Map.of(
            "COPPER", new int[]{2, 0},
            "IRON", new int[]{1, 7},
            "ASH_TREE", new int[]{-1,0},
            "GUDGEON_LAKE", new int[]{4,2},
            "SUNFLOWER_FIELD", new int[]{2,2}
            //add more
    );

    //todo need to merge crafting_resources with crafting_resource_ingredients because its the damn same thing
    public static final Map<String, String> CRAFTING_RESOURCES = Map.of(
            "COPPER_BAR", "copper_bar",
            "IRON_BAR", "iron_bar",
            "ASH_WOOD", "ash_wood"
    );

    public static final Map<String, String> CRAFTING_RESOURCE_INGREDIENTS = Map.of(
            "COPPER_ORE", "copper_ore",
            "IRON_ORE", "iron_ore",
            "WOOL", "wool",
            "FEATHER", "feather",
            "COWHIDE", "cowhide"
            //"ASH_WOOD", "ash_tree_log" //wrong key, look at copper ore
    );
}
