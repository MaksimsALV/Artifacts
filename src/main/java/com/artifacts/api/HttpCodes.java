package com.artifacts.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

// https://docs.artifactsmmo.com/api_guide/response_codes
public final class HttpCodes {
    private HttpCodes() {
    }

    // General Codes
    public static final HttpStatusCode SUCCESS = HttpStatus.OK;
    public static final HttpStatusCode INVALID_PAYLOAD = HttpStatusCode.valueOf(422);
    public static final HttpStatusCode TOO_MANY_REQUESTS = HttpStatus.TOO_MANY_REQUESTS;
    public static final HttpStatusCode NOT_FOUND = HttpStatus.NOT_FOUND;
    public static final HttpStatusCode FATAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    // Email Token Error Codes
    public static final HttpStatusCode INVALID_EMAIL_RESET_TOKEN = HttpStatusCode.valueOf(560);
    public static final HttpStatusCode EXPIRED_EMAIL_RESET_TOKEN = HttpStatusCode.valueOf(561);
    public static final HttpStatusCode USED_EMAIL_RESET_TOKEN = HttpStatusCode.valueOf(562);

    // Account Error Codes
    public static final HttpStatusCode TOKEN_INVALID = HttpStatusCode.valueOf(452);
    public static final HttpStatusCode TOKEN_EXPIRED = HttpStatusCode.valueOf(453);
    public static final HttpStatusCode TOKEN_MISSING = HttpStatusCode.valueOf(454);
    public static final HttpStatusCode TOKEN_GENERATION_FAIL = HttpStatusCode.valueOf(455);
    public static final HttpStatusCode USERNAME_ALREADY_USED = HttpStatusCode.valueOf(456);
    public static final HttpStatusCode EMAIL_ALREADY_USED = HttpStatusCode.valueOf(457);
    public static final HttpStatusCode SAME_PASSWORD = HttpStatusCode.valueOf(458);
    public static final HttpStatusCode CURRENT_PASSWORD_INVALID = HttpStatusCode.valueOf(459);
    public static final HttpStatusCode ACCOUNT_NOT_MEMBER = HttpStatusCode.valueOf(451);
    public static final HttpStatusCode ACCOUNT_SKIN_NOT_OWNED = HttpStatusCode.valueOf(550);

    // Character Error Codes
    public static final HttpStatusCode CHARACTER_NOT_ENOUGH_HP = HttpStatusCode.valueOf(483);
    public static final HttpStatusCode CHARACTER_MAXIMUM_UTILITIES_EQUIPPED = HttpStatusCode.valueOf(484);
    public static final HttpStatusCode CHARACTER_ITEM_ALREADY_EQUIPPED = HttpStatusCode.valueOf(485);
    public static final HttpStatusCode CHARACTER_LOCKED = HttpStatusCode.valueOf(486);
    public static final HttpStatusCode CHARACTER_NOT_THIS_TASK = HttpStatusCode.valueOf(474);
    public static final HttpStatusCode CHARACTER_TOO_MANY_ITEMS_TASK = HttpStatusCode.valueOf(475);
    public static final HttpStatusCode CHARACTER_NO_TASK = HttpStatusCode.valueOf(487);
    public static final HttpStatusCode CHARACTER_TASK_NOT_COMPLETED = HttpStatusCode.valueOf(488);
    public static final HttpStatusCode CHARACTER_ALREADY_TASK = HttpStatusCode.valueOf(489);
    public static final HttpStatusCode CHARACTER_ALREADY_MAP = HttpStatusCode.valueOf(490);
    public static final HttpStatusCode CHARACTER_SLOT_EQUIPMENT_ERROR = HttpStatusCode.valueOf(491);
    public static final HttpStatusCode CHARACTER_GOLD_INSUFFICIENT = HttpStatusCode.valueOf(492);
    public static final HttpStatusCode CHARACTER_NOT_SKILL_LEVEL_REQUIRED = HttpStatusCode.valueOf(493);
    public static final HttpStatusCode CHARACTER_NAME_ALREADY_USED = HttpStatusCode.valueOf(494);
    public static final HttpStatusCode MAX_CHARACTERS_REACHED = HttpStatusCode.valueOf(495);
    public static final HttpStatusCode CHARACTER_CONDITION_NOT_MET = HttpStatusCode.valueOf(496);
    public static final HttpStatusCode CHARACTER_INVENTORY_FULL = HttpStatusCode.valueOf(497);
    public static final HttpStatusCode CHARACTER_NOT_FOUND = HttpStatusCode.valueOf(498);
    public static final HttpStatusCode CHARACTER_IN_COOLDOWN = HttpStatusCode.valueOf(499);

    // Item Error Codes
    public static final HttpStatusCode ITEM_INSUFFICIENT_QUANTITY = HttpStatusCode.valueOf(471);
    public static final HttpStatusCode ITEM_INVALID_EQUIPMENT = HttpStatusCode.valueOf(472);
    public static final HttpStatusCode ITEM_RECYCLING_INVALID_ITEM = HttpStatusCode.valueOf(473);
    public static final HttpStatusCode ITEM_INVALID_CONSUMABLE = HttpStatusCode.valueOf(476);
    public static final HttpStatusCode MISSING_ITEM = HttpStatusCode.valueOf(478);

    // Grand Exchange Error Codes
    public static final HttpStatusCode GE_MAX_QUANTITY = HttpStatusCode.valueOf(479);
    public static final HttpStatusCode GE_NOT_IN_STOCK = HttpStatusCode.valueOf(480);
    public static final HttpStatusCode GE_NOT_THE_PRICE = HttpStatusCode.valueOf(482);
    public static final HttpStatusCode GE_TRANSACTION_IN_PROGRESS = HttpStatusCode.valueOf(436);
    public static final HttpStatusCode GE_NO_ORDERS = HttpStatusCode.valueOf(431);
    public static final HttpStatusCode GE_MAX_ORDERS = HttpStatusCode.valueOf(433);
    public static final HttpStatusCode GE_TOO_MANY_ITEMS = HttpStatusCode.valueOf(434);
    public static final HttpStatusCode GE_SAME_ACCOUNT = HttpStatusCode.valueOf(435);
    public static final HttpStatusCode GE_INVALID_ITEM = HttpStatusCode.valueOf(437);
    public static final HttpStatusCode GE_NOT_YOUR_ORDER = HttpStatusCode.valueOf(438);

    // Bank Error Codes
    public static final HttpStatusCode BANK_INSUFFICIENT_GOLD = HttpStatusCode.valueOf(460);
    public static final HttpStatusCode BANK_TRANSACTION_IN_PROGRESS = HttpStatusCode.valueOf(461);
    public static final HttpStatusCode BANK_FULL = HttpStatusCode.valueOf(462);

    // Maps Error Codes
    public static final HttpStatusCode MAP_NOT_FOUND = HttpStatusCode.valueOf(597);
    public static final HttpStatusCode MAP_CONTENT_NOT_FOUND = HttpStatusCode.valueOf(598);

    // NPC Error Codes
    public static final HttpStatusCode NPC_NOT_FOR_SALE = HttpStatusCode.valueOf(441);
    public static final HttpStatusCode NPC_NOT_FOR_BUY = HttpStatusCode.valueOf(442);
}