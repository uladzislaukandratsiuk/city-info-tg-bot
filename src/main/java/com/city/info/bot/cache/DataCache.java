package com.city.info.bot.cache;

import com.city.info.bot.model.City;
import com.city.info.bot.tg_bot_api.BotState;

public interface DataCache {

    void setCurrentUserBotState(int userId, BotState botState);

    BotState getCurrentUserBotState(int userId);

    void setUserCityData(int userId, City userCityData);

    City getUserCityData(int userId);
}
