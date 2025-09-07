package com.artifacts.game.library.items;

import java.util.ArrayList;
import java.util.List;

import static com.artifacts.game.endpoints.items.GetAllItems.getAllItems;

public class GetItemsByItemType {
    public static List<String> getAllUtilityItems() {
        var itemTypeUtilityData = getAllItems("", "utility");
        var itemTypeUtilityDataArray = itemTypeUtilityData.getJSONArray("data");
        List<String> utilityCodes = new ArrayList<>();
        for (var i = 0; i < itemTypeUtilityDataArray.length(); i++) {
            var itemCode = itemTypeUtilityDataArray.getJSONObject(i).getString("code");
            utilityCodes.add(itemCode);
        }
        return utilityCodes;
    }
}
