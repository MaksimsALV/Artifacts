package com.artifacts.api.errorhandling;

import org.json.JSONObject;

import java.net.http.HttpResponse;

import static com.artifacts.api.errorhandling.ErrorCodes.*;

public class GlobalErrorHandler {
    public static void globalErrorHandler(HttpResponse<String> response) {
        var object = new JSONObject(response.body());
        var responseErrorMessage = object.getString("message");

        try {
            if (response.statusCode() == CODE_SUCCESS) {
                System.out.println(CODE_SUCCESS + " OK");

            } else if (response.statusCode() == CODE_INVALID_PAYLOAD) {
                System.err.println(CODE_INVALID_PAYLOAD + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOO_MANY_REQUESTS) {
                System.err.println(CODE_TOO_MANY_REQUESTS + responseErrorMessage);
            } else if (response.statusCode() == CODE_NOT_FOUND) {
                System.err.println(CODE_NOT_FOUND + responseErrorMessage);
            } else if (response.statusCode() == CODE_FATAL_ERROR) {
                System.err.println(CODE_FATAL_ERROR + responseErrorMessage);
            } else if (response.statusCode() == CODE_INVALID_EMAIL_RESET_TOKEN) {
                System.err.println(CODE_INVALID_EMAIL_RESET_TOKEN + responseErrorMessage);
            } else if (response.statusCode() == CODE_EXPIRED_EMAIL_RESET_TOKEN) {
                System.err.println(CODE_EXPIRED_EMAIL_RESET_TOKEN + responseErrorMessage);
            } else if (response.statusCode() == CODE_USED_EMAIL_RESET_TOKEN) {
                System.err.println(CODE_USED_EMAIL_RESET_TOKEN + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_INVALID) {
                System.err.println(CODE_TOKEN_INVALID + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_EXPIRED) {
                System.err.println(CODE_TOKEN_EXPIRED + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_MISSING) {
                System.err.println(CODE_TOKEN_MISSING + responseErrorMessage);
            } else if (response.statusCode() == CODE_TOKEN_GENERATION_FAIL) {
                System.err.println(CODE_TOKEN_GENERATION_FAIL + responseErrorMessage);
            } else if (response.statusCode() == CODE_USERNAME_ALREADY_USED) {
                System.err.println(CODE_USERNAME_ALREADY_USED + responseErrorMessage);
            } else if (response.statusCode() == CODE_EMAIL_ALREADY_USED) {
                System.err.println(CODE_EMAIL_ALREADY_USED + responseErrorMessage);
            } else if (response.statusCode() == CODE_SAME_PASSWORD) {
                System.err.println(CODE_SAME_PASSWORD + responseErrorMessage);
            } else if (response.statusCode() == CODE_CURRENT_PASSWORD_INVALID) {
                System.err.println(CODE_CURRENT_PASSWORD_INVALID + responseErrorMessage);
            } else if (response.statusCode() == CODE_ACCOUNT_NOT_MEMBER) {
                System.err.println(CODE_ACCOUNT_NOT_MEMBER + responseErrorMessage);
            } else if (response.statusCode() == CODE_ACCOUNT_SKIN_NOT_OWNED) {
                System.err.println(CODE_ACCOUNT_SKIN_NOT_OWNED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_ENOUGH_HP) {
                System.err.println(CODE_CHARACTER_NOT_ENOUGH_HP + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_MAXIMUM_UTILITIES_EQUIPPED) {
                System.err.println(CODE_CHARACTER_MAXIMUM_UTILITIES_EQUIPPED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ITEM_ALREADY_EQUIPPED) {
                System.err.println(CODE_CHARACTER_ITEM_ALREADY_EQUIPPED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_LOCKED) {
                System.err.println(CODE_CHARACTER_LOCKED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_THIS_TASK) {
                System.err.println(CODE_CHARACTER_NOT_THIS_TASK + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_TOO_MANY_ITEMS_TASK) {
                System.err.println(CODE_CHARACTER_TOO_MANY_ITEMS_TASK + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NO_TASK) {
                System.err.println(CODE_CHARACTER_NO_TASK + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_TASK_NOT_COMPLETED) {
                System.err.println(CODE_CHARACTER_TASK_NOT_COMPLETED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ALREADY_TASK) {
                System.err.println(CODE_CHARACTER_ALREADY_TASK + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_ALREADY_MAP) {
                System.err.println(CODE_CHARACTER_ALREADY_MAP + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_SLOT_EQUIPMENT_ERROR) {
                System.err.println(CODE_CHARACTER_SLOT_EQUIPMENT_ERROR + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_GOLD_INSUFFICIENT) {
                System.err.println(CODE_CHARACTER_GOLD_INSUFFICIENT + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_SKILL_LEVEL_REQUIRED) {
                System.err.println(CODE_CHARACTER_NOT_SKILL_LEVEL_REQUIRED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NAME_ALREADY_USED) {
                System.err.println(CODE_CHARACTER_NAME_ALREADY_USED + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAX_CHARACTERS_REACHED) {
                System.err.println(CODE_MAX_CHARACTERS_REACHED + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_CONDITION_NOT_MET) {
                System.err.println(CODE_CHARACTER_CONDITION_NOT_MET + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                System.err.println(CODE_CHARACTER_INVENTORY_FULL + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_NOT_FOUND) {
                System.err.println(CODE_CHARACTER_NOT_FOUND + responseErrorMessage);
            } else if (response.statusCode() == CODE_CHARACTER_IN_COOLDOWN) {
                System.err.println(CODE_CHARACTER_IN_COOLDOWN + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_INSUFFICIENT_QUANTITY) {
                System.err.println(CODE_ITEM_INSUFFICIENT_QUANTITY + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_INVALID_EQUIPMENT) {
                System.err.println(CODE_ITEM_INVALID_EQUIPMENT + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_RECYCLING_INVALID_ITEM) {
                System.err.println(CODE_ITEM_RECYCLING_INVALID_ITEM + responseErrorMessage);
            } else if (response.statusCode() == CODE_ITEM_INVALID_CONSUMABLE) {
                System.err.println(CODE_ITEM_INVALID_CONSUMABLE + responseErrorMessage);
            } else if (response.statusCode() == CODE_MISSING_ITEM) {
                System.err.println(CODE_MISSING_ITEM + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_MAX_QUANTITY) {
                System.err.println(CODE_GE_MAX_QUANTITY + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_IN_STOCK) {
                System.err.println(CODE_GE_NOT_IN_STOCK + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_THE_PRICE) {
                System.err.println(CODE_GE_NOT_THE_PRICE + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_TRANSACTION_IN_PROGRESS) {
                System.err.println(CODE_GE_TRANSACTION_IN_PROGRESS + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NO_ORDERS) {
                System.err.println(CODE_GE_NO_ORDERS + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_MAX_ORDERS) {
                System.err.println(CODE_GE_MAX_ORDERS + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_TOO_MANY_ITEMS) {
                System.err.println(CODE_GE_TOO_MANY_ITEMS + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_SAME_ACCOUNT) {
                System.err.println(CODE_GE_SAME_ACCOUNT + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_INVALID_ITEM) {
                System.err.println(CODE_GE_INVALID_ITEM + responseErrorMessage);
            } else if (response.statusCode() == CODE_GE_NOT_YOUR_ORDER) {
                System.err.println(CODE_GE_NOT_YOUR_ORDER + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_INSUFFICIENT_GOLD) {
                System.err.println(CODE_BANK_INSUFFICIENT_GOLD + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_TRANSACTION_IN_PROGRESS) {
                System.err.println(CODE_BANK_TRANSACTION_IN_PROGRESS + responseErrorMessage);
            } else if (response.statusCode() == CODE_BANK_FULL) {
                System.err.println(CODE_BANK_FULL + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAP_NOT_FOUND) {
                System.err.println(CODE_MAP_NOT_FOUND + responseErrorMessage);
            } else if (response.statusCode() == CODE_MAP_CONTENT_NOT_FOUND) {
                System.err.println(CODE_MAP_CONTENT_NOT_FOUND + responseErrorMessage);
            } else if (response.statusCode() == CODE_NPC_NOT_FOR_SALE) {
                System.err.println(CODE_NPC_NOT_FOR_SALE + responseErrorMessage);
            } else if (response.statusCode() == CODE_NPC_NOT_FOR_BUY) {
                System.err.println(CODE_NPC_NOT_FOR_BUY + responseErrorMessage);
            } else {
                System.err.println("Undefined error code in globalErrorHandler" + response.statusCode() + response.body());
            }
        } catch (Exception GlobalErrorHandlerError) {
            System.err.println("Unknown GlobalErrorHandler error: " + GlobalErrorHandlerError);
        }
    }
}
