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
        return code;
    }
}
