package com.artifacts.api;

// https://docs.artifactsmmo.com/api_guide/response_codes
public final class HttpCodes {
    private HttpCodes() {
    }

    // General Codes
    public static final int SUCCESS = 200;
    public static final int INVALID_PAYLOAD = 422;
    public static final int TOO_MANY_REQUESTS = 429;
    public static final int NOT_FOUND = 404;
    public static final int FATAL_ERROR = 500;

    // Email Token Error Codes
    public static final int INVALID_EMAIL_RESET_TOKEN = 560;
    public static final int EXPIRED_EMAIL_RESET_TOKEN = 561;
    public static final int USED_EMAIL_RESET_TOKEN = 562;

    // Account Error Codes
    public static final int TOKEN_INVALID = 452;
    public static final int TOKEN_EXPIRED = 453;
    public static final int TOKEN_MISSING = 454;
    public static final int TOKEN_GENERATION_FAIL = 455;
    public static final int USERNAME_ALREADY_USED = 456;
    public static final int EMAIL_ALREADY_USED = 457;
    public static final int SAME_PASSWORD = 458;
    public static final int CURRENT_PASSWORD_INVALID = 459;
    public static final int ACCOUNT_NOT_MEMBER = 451;
    public static final int ACCOUNT_SKIN_NOT_OWNED = 550;

    // Character Error Codes
    public static final int CHARACTER_NOT_ENOUGH_HP = 483;
    public static final int CHARACTER_MAXIMUM_UTILITIES_EQUIPPED = 484;
    public static final int CHARACTER_ITEM_ALREADY_EQUIPPED = 485;
    public static final int CHARACTER_LOCKED = 486;
    public static final int CHARACTER_NOT_THIS_TASK = 474;
    public static final int CHARACTER_TOO_MANY_ITEMS_TASK = 475;
    public static final int CHARACTER_NO_TASK = 487;
    public static final int CHARACTER_TASK_NOT_COMPLETED = 488;
    public static final int CHARACTER_ALREADY_TASK = 489;
    public static final int CHARACTER_ALREADY_MAP = 490;
    public static final int CHARACTER_SLOT_EQUIPMENT_ERROR = 491;
    public static final int CHARACTER_GOLD_INSUFFICIENT = 492;
    public static final int CHARACTER_NOT_SKILL_LEVEL_REQUIRED = 493;
    public static final int CHARACTER_NAME_ALREADY_USED = 494;
    public static final int MAX_CHARACTERS_REACHED = 495;
    public static final int CHARACTER_CONDITION_NOT_MET = 496;
    public static final int CHARACTER_INVENTORY_FULL = 497;
    public static final int CHARACTER_NOT_FOUND = 498;
    public static final int CHARACTER_IN_COOLDOWN = 499;

    // Item Error Codes
    public static final int ITEM_INSUFFICIENT_QUANTITY = 471;
    public static final int ITEM_INVALID_EQUIPMENT = 472;
    public static final int ITEM_RECYCLING_INVALID_ITEM = 473;
    public static final int ITEM_INVALID_CONSUMABLE = 476;
    public static final int MISSING_ITEM = 478;

    // Grand Exchange Error Codes
    public static final int GE_MAX_QUANTITY = 479;
    public static final int GE_NOT_IN_STOCK = 480;
    public static final int GE_NOT_THE_PRICE = 482;
    public static final int GE_TRANSACTION_IN_PROGRESS = 436;
    public static final int GE_NO_ORDERS = 431;
    public static final int GE_MAX_ORDERS = 433;
    public static final int GE_TOO_MANY_ITEMS = 434;
    public static final int GE_SAME_ACCOUNT = 435;
    public static final int GE_INVALID_ITEM = 437;
    public static final int GE_NOT_YOUR_ORDER = 438;

    // Bank Error Codes
    public static final int BANK_INSUFFICIENT_GOLD = 460;
    public static final int BANK_TRANSACTION_IN_PROGRESS = 461;
    public static final int BANK_FULL = 462;

    // Maps Error Codes
    public static final int MAP_NOT_FOUND = 597;
    public static final int MAP_CONTENT_NOT_FOUND = 598;

    // NPC Error Codes
    public static final int NPC_NOT_FOR_SALE = 441;
    public static final int NPC_NOT_FOR_BUY = 442;
}