package com.city.info.bot.tg_bot_api;

public enum BotState {
    BOT_START_REPLY,
    GET_INFO_BY_CITY_NAME,
    CITY_NOT_FOUND,
    ADD_NEW_CITY,
    ASK_CITY_NAME,
    ASK_CITY_INFO,
    FILLING_CITY_DATA,
    CITY_DATA_FILLED,
    SHOW_MAIN_MENU,
    SHOW_HELP_MENU,
}
