package com.artifacts.api.errorhandling;

public class ErrorCodes {
    //https://docs.artifactsmmo.com/api_guide/response_codes

    //General Codes
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_INVALID_PAYLOAD = 422;
    public static final int CODE_TOO_MANY_REQUESTS = 429;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_FATAL_ERROR = 500;

    //Email Token Error Codes
    public static final int CODE_INVALID_EMAIL_RESET_TOKEN = 560;
    public static final int CODE_EXPIRED_EMAIL_RESET_TOKEN = 561;
    public static final int CODE_USED_EMAIL_RESET_TOKEN = 562;

    //Account Error Codes
    public static final int CODE_TOKEN_INVALID = 452;
    public static final int CODE_TOKEN_EXPIRED = 453;
    public static final int CODE_TOKEN_MISSING = 454;
    public static final int CODE_TOKEN_GENERATION_FAIL = 455;
    public static final int CODE_USERNAME_ALREADY_USED = 456;
    public static final int CODE_EMAIL_ALREADY_USED = 457;
    public static final int CODE_SAME_PASSWORD = 458;
    public static final int CODE_CURRENT_PASSWORD_INVALID = 459;
    public static final int CODE_ACCOUNT_NOT_MEMBER = 451;
    public static final int CODE_ACCOUNT_SKIN_NOT_OWNED = 550;

    //Character Error Codes
    public static final int CODE_CHARACTER_NOT_ENOUGH_HP = 483;
    public static final int CODE_CHARACTER_MAXIMUM_UTILITIES_EQUIPPED = 484;
    public static final int CODE_CHARACTER_ITEM_ALREADY_EQUIPPED = 485;
    public static final int CODE_CHARACTER_LOCKED = 486;
    public static final int CODE_CHARACTER_NOT_THIS_TASK = 474;
    public static final int CODE_CHARACTER_TOO_MANY_ITEMS_TASK = 475;
    public static final int CODE_CHARACTER_NO_TASK = 487;
    public static final int CODE_CHARACTER_TASK_NOT_COMPLETED = 488;
    public static final int CODE_CHARACTER_ALREADY_TASK = 489;
    public static final int CODE_CHARACTER_ALREADY_MAP = 490;
    public static final int CODE_CHARACTER_SLOT_EQUIPMENT_ERROR = 491;
    public static final int CODE_CHARACTER_GOLD_INSUFFICIENT = 492;
    public static final int CODE_CHARACTER_NOT_SKILL_LEVEL_REQUIRED = 493;
    public static final int CODE_CHARACTER_NAME_ALREADY_USED = 494;
    public static final int CODE_MAX_CHARACTERS_REACHED = 495;
    public static final int CODE_CHARACTER_CONDITION_NOT_MET = 496;
    public static final int CODE_CHARACTER_INVENTORY_FULL = 497;
    public static final int CODE_CHARACTER_NOT_FOUND = 498;
    public static final int CODE_CHARACTER_IN_COOLDOWN = 499;

    //Item Error Codes
    public static final int CODE_ITEM_INSUFFICIENT_QUANTITY = 471;
    public static final int CODE_ITEM_INVALID_EQUIPMENT = 472;
    public static final int CODE_ITEM_RECYCLING_INVALID_ITEM = 473;
    public static final int CODE_ITEM_INVALID_CONSUMABLE = 476;
    public static final int CODE_MISSING_ITEM = 478;

    //Grand Exchange Error Codes
    public static final int CODE_GE_MAX_QUANTITY = 479;
    public static final int CODE_GE_NOT_IN_STOCK = 480;
    public static final int CODE_GE_NOT_THE_PRICE = 482;
    public static final int CODE_GE_TRANSACTION_IN_PROGRESS = 436;
    public static final int CODE_GE_NO_ORDERS = 431;
    public static final int CODE_GE_MAX_ORDERS = 433;
    public static final int CODE_GE_TOO_MANY_ITEMS = 434;
    public static final int CODE_GE_SAME_ACCOUNT = 435;
    public static final int CODE_GE_INVALID_ITEM = 437;
    public static final int CODE_GE_NOT_YOUR_ORDER = 438;

    //Bank Error Codes
    public static final int CODE_BANK_INSUFFICIENT_GOLD = 460;
    public static final int CODE_BANK_TRANSACTION_IN_PROGRESS = 461;
    public static final int CODE_BANK_FULL = 462;

    //Maps Error Codes
    public static final int CODE_MAP_NOT_FOUND = 597;
    public static final int CODE_MAP_CONTENT_NOT_FOUND = 598;

    //NPC Error Codes
    public static final int CODE_NPC_NOT_FOR_SALE = 441;
    public static final int CODE_NPC_NOT_FOR_BUY = 442;
}

