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

    public static List<String> getAllConsumableItems() {
        var itemTypeConsumableData = getAllItems("", "consumable");
        var itemTypeConsumableDataArray = itemTypeConsumableData.getJSONArray("data");
        List<String> consumableCodes = new ArrayList<>();
        for (var i = 0; i < itemTypeConsumableDataArray.length(); i++) {
            var itemCode = itemTypeConsumableDataArray.getJSONObject(i).getString("code");
            consumableCodes.add(itemCode);
        }
        return consumableCodes;
    }
}
