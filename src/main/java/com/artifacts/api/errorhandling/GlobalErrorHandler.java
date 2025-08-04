package com.artifacts.api.errorhandling;

import com.artifacts.tools.Converter;
//import com.artifacts.tools.Regex;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.*;

public class GlobalErrorHandler {
    public static String extractErrorMessage(JSONObject object) {
        if (object.has("error")) {
            var responseErrorObject = object.getJSONObject("error");
            if (responseErrorObject.has("message")) {
                return responseErrorObject.getString("message");
            }
        }
        return null;
    }

    //todo I dont think i need it anymore, because i am no longer relying on error code specific handling
    public static Double extractCooldownSecondsFromErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            var matcher = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s+seconds?\\s+remaining")
                    .matcher(errorMessage);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        }
        return null;
    }



    public static void globalErrorHandler(HttpResponse<String> response, String endpoint) {
        var object = new JSONObject(response.body());
        var responseErrorMessage = extractErrorMessage(object);

        try {
            //todo I dont think i need code 200 here, because 200 is not an error, and im handling each 200 separatelly in each own endpoint class
            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(endpoint + " | " + CODE_SUCCESS);

            } else if (response.statusCode() == CODE_INVALID_PAYLOAD) {
                System.err.println(endpoint + " | " + CODE_INVALID_PAYLOAD + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOO_MANY_REQUESTS) {
                System.err.println(endpoint + " | " + CODE_TOO_MANY_REQUESTS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_NOT_FOUND) {
                System.err.println(endpoint + " | " + CODE_NOT_FOUND + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_FATAL_ERROR) {
                System.err.println(endpoint + " | " + CODE_FATAL_ERROR + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_INVALID_EMAIL_RESET_TOKEN) {
                System.err.println(endpoint + " | " + CODE_INVALID_EMAIL_RESET_TOKEN + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_EXPIRED_EMAIL_RESET_TOKEN) {
                System.err.println(endpoint + " | " + CODE_EXPIRED_EMAIL_RESET_TOKEN + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_USED_EMAIL_RESET_TOKEN) {
                System.err.println(endpoint + " | " + CODE_USED_EMAIL_RESET_TOKEN + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_INVALID) {
                System.err.println(endpoint + " | " + CODE_TOKEN_INVALID + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_EXPIRED) {
                System.err.println(endpoint + " | " + CODE_TOKEN_EXPIRED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_MISSING) {
                System.err.println(endpoint + " | " + CODE_TOKEN_MISSING + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_GENERATION_FAIL) {
                System.err.println(endpoint + " | " + CODE_TOKEN_GENERATION_FAIL + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_USERNAME_ALREADY_USED) {
                System.err.println(endpoint + " | " + CODE_USERNAME_ALREADY_USED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_EMAIL_ALREADY_USED) {
                System.err.println(endpoint + " | " + CODE_EMAIL_ALREADY_USED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_SAME_PASSWORD) {
                System.err.println(endpoint + " | " + CODE_SAME_PASSWORD + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CURRENT_PASSWORD_INVALID) {
                System.err.println(endpoint + " | " + CODE_CURRENT_PASSWORD_INVALID + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_ACCOUNT_NOT_MEMBER) {
                System.err.println(endpoint + " | " + CODE_ACCOUNT_NOT_MEMBER + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_ACCOUNT_SKIN_NOT_OWNED) {
                System.err.println(endpoint + " | " + CODE_ACCOUNT_SKIN_NOT_OWNED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_ENOUGH_HP) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NOT_ENOUGH_HP + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_MAXIMUM_UTILITIES_EQUIPPED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_MAXIMUM_UTILITIES_EQUIPPED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ITEM_ALREADY_EQUIPPED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_ITEM_ALREADY_EQUIPPED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_LOCKED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_LOCKED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_THIS_TASK) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NOT_THIS_TASK + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_TOO_MANY_ITEMS_TASK) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_TOO_MANY_ITEMS_TASK + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NO_TASK) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NO_TASK + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_TASK_NOT_COMPLETED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_TASK_NOT_COMPLETED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ALREADY_TASK) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_ALREADY_TASK + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ALREADY_MAP) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_ALREADY_MAP + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_SLOT_EQUIPMENT_ERROR) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_SLOT_EQUIPMENT_ERROR + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_GOLD_INSUFFICIENT) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_GOLD_INSUFFICIENT + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_SKILL_LEVEL_REQUIRED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NOT_SKILL_LEVEL_REQUIRED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NAME_ALREADY_USED) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NAME_ALREADY_USED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAX_CHARACTERS_REACHED) {
                System.err.println(endpoint + " | " + CODE_MAX_CHARACTERS_REACHED + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_CONDITION_NOT_MET) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_CONDITION_NOT_MET + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_INVENTORY_FULL + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_FOUND) {
                System.err.println(endpoint + " | " + CODE_CHARACTER_NOT_FOUND + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_IN_COOLDOWN) {
                //todo I think i need to totally remove this part from here, because i am not relying on bad error handling anymore. I take cooldown data from 200 response body
                System.err.println(CODE_CHARACTER_IN_COOLDOWN + responseErrorMessage);
                var cooldownSeconds = extractCooldownSecondsFromErrorMessage(responseErrorMessage);
                try {
                    if (cooldownSeconds != null && cooldownSeconds > 0) {
                        Thread.sleep(Converter.SecondsToMillisConverter(cooldownSeconds));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("CODE_CHARACTER_IN_COOLDOWN Sleep interrupted: " + e.getMessage());
                }
            } else if (response.statusCode() == CODE_ITEM_INSUFFICIENT_QUANTITY) {
                System.err.println(endpoint + " | " + CODE_ITEM_INSUFFICIENT_QUANTITY + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_INVALID_EQUIPMENT) {
                System.err.println(endpoint + " | " + CODE_ITEM_INVALID_EQUIPMENT + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_RECYCLING_INVALID_ITEM) {
                System.err.println(endpoint + " | " + CODE_ITEM_RECYCLING_INVALID_ITEM + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_INVALID_CONSUMABLE) {
                System.err.println(endpoint + " | " + CODE_ITEM_INVALID_CONSUMABLE + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_MISSING_ITEM) {
                System.err.println(endpoint + " | " + CODE_MISSING_ITEM + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_MAX_QUANTITY) {
                System.err.println(endpoint + " | " + CODE_GE_MAX_QUANTITY + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_IN_STOCK) {
                System.err.println(endpoint + " | " + CODE_GE_NOT_IN_STOCK + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_THE_PRICE) {
                System.err.println(endpoint + " | " + CODE_GE_NOT_THE_PRICE + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_TRANSACTION_IN_PROGRESS) {
                System.err.println(endpoint + " | " + CODE_GE_TRANSACTION_IN_PROGRESS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NO_ORDERS) {
                System.err.println(endpoint + " | " + CODE_GE_NO_ORDERS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_MAX_ORDERS) {
                System.err.println(endpoint + " | " + CODE_GE_MAX_ORDERS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_TOO_MANY_ITEMS) {
                System.err.println(endpoint + " | " + CODE_GE_TOO_MANY_ITEMS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_SAME_ACCOUNT) {
                System.err.println(endpoint + " | " + CODE_GE_SAME_ACCOUNT + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_INVALID_ITEM) {
                System.err.println(endpoint + " | " + CODE_GE_INVALID_ITEM + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_YOUR_ORDER) {
                System.err.println(endpoint + " | " + CODE_GE_NOT_YOUR_ORDER + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_INSUFFICIENT_GOLD) {
                System.err.println(endpoint + " | " + CODE_BANK_INSUFFICIENT_GOLD + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_TRANSACTION_IN_PROGRESS) {
                System.err.println(endpoint + " | " + CODE_BANK_TRANSACTION_IN_PROGRESS + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_FULL) {
                System.err.println(endpoint + " | " + CODE_BANK_FULL + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAP_NOT_FOUND) {
                System.err.println(endpoint + " | " + CODE_MAP_NOT_FOUND + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAP_CONTENT_NOT_FOUND) {
                System.err.println(endpoint + " | " + CODE_MAP_CONTENT_NOT_FOUND + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_NPC_NOT_FOR_SALE) {
                System.err.println(endpoint + " | " + CODE_NPC_NOT_FOR_SALE + " " + responseErrorMessage);
            } else if (response.statusCode() == CODE_NPC_NOT_FOR_BUY) {
                System.err.println(endpoint + " | " + CODE_NPC_NOT_FOR_BUY + " " + responseErrorMessage);
            } else {
                System.err.println(endpoint + " | Undefined error code in globalErrorHandler: " + response.statusCode() + responseErrorMessage);
            }
        } catch (Exception globalErrorHandlerException) {
            System.err.println(endpoint + " | Exception in globalErrorHandler: " + globalErrorHandlerException);
        }
    }
}
