package com.artifacts.game.logic.activity;

import com.artifacts.api.endpoints.get.GetAllMaps;
import com.artifacts.api.endpoints.get.GetBankItems;
import com.artifacts.game.helpers.CodeToNameMapper;

import static com.artifacts.api.endpoints.get.GetAllMaps.getAllMaps;
import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.endpoints.post.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.api.endpoints.post.ActionGathering.actionGathering;
import static com.artifacts.api.endpoints.post.ActionMove.actionMove;
//import static com.artifacts.game.library.locations.GatheringZones.RESOURCE_FIELDS;
//import static com.artifacts.game.library.recources.Resources.RESOURCE_LOCATION;
import static com.artifacts.game.helpers.GlobalCooldownManager.globalCooldownManager;
import static com.artifacts.game.logic.activity.Fighting.fight;
import static com.artifacts.tools.Delay.delay;

//Gathering 2.0
public class Gathering {
    public static void gather(String name, String activityLocation, boolean miningAll, boolean woodcuttingAll, boolean fishingAll, boolean herbsAll) throws InterruptedException {
        GetBankItems getBankItems = new GetBankItems();
        CodeToNameMapper codeToNameMapper = new CodeToNameMapper();
        var resourceThreshold = 75000;
        if (miningAll) {
            var resources = getBankItems.countMiningResourcesFromBankAsHashMap();
            var resourceIsNotAtCap = resources.entrySet().stream()
                    .filter(entry ->
                            !(entry.getKey().equals("strange_ore") || entry.getKey().equals("mithril_ore") || entry.getKey().equals("adamantite_ore")))
                    .filter(entry -> {
                        var resourceValue = entry.getValue();
                        return resourceValue == null || resourceValue < resourceThreshold;
                    })
                    .findFirst();
            //todo need to check bank for gathering tools and equip them

            if (resourceIsNotAtCap.isPresent()) {
                var missingResourceCode = resourceIsNotAtCap.get().getKey();
                activityLocation = codeToNameMapper.codeToNameMap(missingResourceCode);
                System.out.println("resource is not at cap: " + missingResourceCode + " | executing gathering loop for that resource.");
            } else { //fallback mechanism when resourceThreshold is reached for all resources
                activityLocation = "yellow_slime";
                fight(name, activityLocation, "", "", "", false);
                System.out.println("all resources ar at cap | executing fighting fallback mechanism.");
                return;
            }
        }
        //todo woodcuttingAll
        if (woodcuttingAll) {
            var resources = getBankItems.countWoodcuttingResourcesFromBankAsHashMap();
            var resourceIsNotAtCap = resources.entrySet().stream()
                    .filter(entry ->
                            !(entry.getKey().equals("magic_wood") || entry.getKey().equals("palm_wood")))
                    .filter(entry -> {
                        var resourceValue = entry.getValue();
                        return resourceValue == null || resourceValue < resourceThreshold;
                    })
                    .findFirst();
            //todo need to check bank for gathering tools and equip them

            if (resourceIsNotAtCap.isPresent()) {
                var missingResourceCode = resourceIsNotAtCap.get().getKey();
                activityLocation = codeToNameMapper.codeToNameMap(missingResourceCode);
                System.out.println("resource is not at cap: " + missingResourceCode + " | executing gathering loop for that resource.");
            } else { //fallback mechanism when resourceThreshold is reached for all resources
                activityLocation = "yellow_slime";
                fight(name, activityLocation, "", "", "", false);
                System.out.println("all resources ar at cap | executing fighting fallback mechanism.");
                return;
            }
        }
        //todo fishingAll
        if (fishingAll) {

        }
        //todo herbsAll
        if (herbsAll) {

        }

        var coordinates = getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        while (true) {
            response = actionGathering(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                continue;
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                if (activityLocation.equals("nettle") || activityLocation.equals("glowstem") || activityLocation.equals("trout_fishing_spot") || activityLocation.equals("bass_fishing_spot")) {
                    response = actionMove(name, 7, 13);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    } else {
                        response = actionMove(name, 4, 1);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                        }
                    }
                } else {
                    response = actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
                gather(name, activityLocation, miningAll, woodcuttingAll, fishingAll, herbsAll);
                return;
            } else if (statusCode == CODE_CHARACTER_LOCKED) {
                System.err.println("486 happened! Starting delay, then continue");
                delay(5);
                continue;
            }
            return;
        }
    }
}

/*//Gathering 1.0
public class Gathering {
    public static void gather(String name, String activityLocation) throws InterruptedException {
        //if (Thread.currentThread().isInterrupted()) throw new InterruptedException("cancelled");
        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        while (true) {
            response = actionGathering(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                continue;
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                if (activityLocation.equals("nettle") || activityLocation.equals("glowstem") || activityLocation.equals("trout_fishing_spot") || activityLocation.equals("bass_fishing_spot")) {
                    response = actionMove(name, 7, 13);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    } else {
                        response = actionMove(name, 4, 1);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                        }
                    }
                } else {
                    response = actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
                gather(name, activityLocation);
                return;
            } else if (statusCode == CODE_CHARACTER_LOCKED) {
                System.err.println("486 happened! Starting delay, then continue");
                delay(5);
                continue;
            }
            return;
        }
    }
}*/
