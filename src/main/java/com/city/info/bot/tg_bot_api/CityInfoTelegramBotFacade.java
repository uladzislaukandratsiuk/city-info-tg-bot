package com.city.info.bot.tg_bot_api;

import com.city.info.bot.cache.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class CityInfoTelegramBotFacade {

    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;

    public CityInfoTelegramBotFacade(BotStateContext botStateContext,
                                     UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

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
            case "/menu":
                botState = BotState.CITY_CRUD_MENU;
                break;
            default:
                botState = userDataCache.getCurrentUserBotState(userId);
                break;
        }

        userDataCache.setCurrentUserBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer;

        switch (buttonQuery.getData()) {
            case "listOfCitiesButton":
                callBackAnswer = botStateContext
                        .processInputMessage(BotState.SHOW_LIST_OF_CITIES, buttonQuery.getMessage());
                userDataCache.setCurrentUserBotState(userId, BotState.GET_INFO_BY_CITY_NAME);
                break;
            case "addNewCityButton":
                callBackAnswer = new SendMessage(chatId,
                        "Введите название города для добавления!");
                userDataCache.setCurrentUserBotState(userId, BotState.ASK_CITY_NAME);
                break;
            case "updateCityButton":
                callBackAnswer = new SendMessage(chatId,
                        "Введите название города для обновления!");
                userDataCache.setCurrentUserBotState(userId, BotState.ASK_CITY_NAME_UPDATE);
                break;
            case "removeCityButton":
                callBackAnswer = new SendMessage(chatId,
                        "Введите название города для удаления!");
                userDataCache.setCurrentUserBotState(userId, BotState.ASK_CITY_NAME_REMOVE);
                break;
            case "nextButton":
                callBackAnswer = new SendMessage(chatId,
                        "Введите название города для получения информации!");
                userDataCache.setCurrentUserBotState(userId, BotState.GET_INFO_BY_CITY_NAME);
                break;
            default:
                callBackAnswer = new SendMessage(chatId, "");
                userDataCache.setCurrentUserBotState(userId,
                        userDataCache.getCurrentUserBotState(userId));
        }

        return callBackAnswer;
    }
}
