package com.city.info.bot.tg_bot_api.handler;

import com.city.info.bot.cache.UserDataCache;
import com.city.info.bot.service.ChatReplyMessageService;
import com.city.info.bot.tg_bot_api.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CityNotFoundHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ChatReplyMessageService messagesService;

    public CityNotFoundHandler(UserDataCache userDataCache,
                               ChatReplyMessageService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CITY_NOT_FOUND;
    }

    @Override
    public SendMessage handle(Message message) {

        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String userResponse = message.getText();

        SendMessage replyToUser;

        if (userResponse.matches("да|Да")) {

            replyToUser =
                    messagesService.getReplyMessage(chatId, "bot.ask.city.name");

            userDataCache.setCurrentUserBotState(userId, BotState.ADDING_NEW_CITY);

        } else {

            replyToUser =
                    messagesService.getReplyMessage(chatId, "bot.enter.city.name");

            userDataCache.setCurrentUserBotState(userId, BotState.GET_INFO_BY_CITY_NAME);
        }

        return replyToUser;
    }
}
