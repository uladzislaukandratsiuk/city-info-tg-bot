package com.city.info.bot.cache;

import com.city.info.bot.tg_bot_api.BotState;

import java.util.HashMap;
import java.util.Map;

public class UserDataCache implements DataCache {

    private final Map<Integer, BotState> usersBotStates;

    public UserDataCache() {
        this.usersBotStates = new HashMap<>();
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
}
