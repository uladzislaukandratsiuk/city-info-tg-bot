package com.city.info.bot.tg_bot_api;

import com.city.info.bot.cache.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class CityInfoTelegramBotFacade {

    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;

    public CityInfoTelegramBotFacade(BotStateContext botStateContext, UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public SendMessage handleUpdate(Update update) {

        SendMessage replyMessage = null;
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.BOT_START_REPLY;
                break;
            case "Добавить город":
                botState = BotState.ADD_NEW_CITY;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getCurrentUserBotState(userId);
                break;
        }

        userDataCache.setCurrentUserBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

}
