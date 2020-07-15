package com.city.info.bot.cache;

import com.city.info.bot.model.City;
import com.city.info.bot.tg_bot_api.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {

    private final Map<Integer, BotState> usersBotStates;
    private final Map<Integer, City> userAddedCityData;

    public UserDataCache() {
        this.usersBotStates = new HashMap<>();
        this.userAddedCityData = new HashMap<>();
    }

    @Override
    public void setCurrentUserBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getCurrentUserBotState(int userId) {

        BotState botState = usersBotStates.get(userId);

        if (botState == null) {
            botState = BotState.BOT_START_REPLY;
        }

        return botState;
    }

    @Override
    public void setUserCityData(int userId, City userCityData) {
        userAddedCityData.put(userId, userCityData);
    }

    @Override
    public City getUserCityData(int userId) {
        City userCityData = userAddedCityData.get(userId);
        if (userCityData == null) {
            userCityData = new City();
        }
        return userCityData;
    }
}
