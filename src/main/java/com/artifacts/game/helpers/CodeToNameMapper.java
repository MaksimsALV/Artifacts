package com.artifacts.game.helpers;

public class CodeToNameMapper {
    public String codeToNameMap(String code) {
        //add more mappers if needed for other needs.

        if ("coal".equals(code)) return "coal_rocks";
        if (code != null && code.endsWith("_ore")) {
            return code.replace("_ore", "_rocks");
        }

        if (code != null && code.endsWith("_wood")) {
            return code.replace("_wood", "_tree");
        }

        if (code != null && code.equals("gudgeon")) {
            return code.replace("gudgeon", "gudgeon_spot");
        } else if (code != null && code.equals("shrimp")) {
            return code.replace("shrimp", "shrimp_spot");
        } else if (code != null && code.equals("trout")) {
            return code.replace("trout", "trout_spot");
        } else if (code != null && code.equals("bass")) {
            return code.replace("bass", "bass_spot");
        } else if (code != null && code.equals("salmon")) {
            return code.replace("salmon", "salmon_spot");
        } else if (code != null && code.equals("swordfish")) {
            return code.replace("swordfish", "swordfish_spot");
        }

        if (code != null && code.equals("sunflower")) {
            return code.replace("sunflower", "sunflower_field");
        } else if (code != null && code.equals("nettle_leaf")) {
            return code.replace("nettle_leaf", "nettle");
        } else if (code != null && code.equals("glowstem_leaf")) {
            return code.replace("glowstem_leaf", "glowstem");
        } else if (code != null && code.equals("torch_cactus_flower")) {
            return code.replace("torch_cactus_flower", "torch_cactus");
        }
        return code;
    }
}
