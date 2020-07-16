package com.city.info.bot.tg_bot_api.handler.crud;

import com.city.info.bot.cache.UserDataCache;
import com.city.info.bot.model.City;
import com.city.info.bot.service.ChatReplyMessageService;
import com.city.info.bot.service.CityService;
import com.city.info.bot.tg_bot_api.BotState;
import com.city.info.bot.tg_bot_api.handler.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddingNewCityHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ChatReplyMessageService messagesService;
    private final CityService cityService;

    public AddingNewCityHandler(UserDataCache userDataCache,
                                ChatReplyMessageService messagesService,
                                CityService cityService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.cityService = cityService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADDING_NEW_CITY;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getCurrentUserBotState(message.getFrom().getId())
                .equals(BotState.ADDING_NEW_CITY)) {

            userDataCache.setCurrentUserBotState(message.getFrom().getId(),
                    BotState.ASK_CITY_NAME);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message message) {

        String userResponse = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        City cacheCity = userDataCache.getUserCityData(userId);
        BotState botState = userDataCache.getCurrentUserBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_CITY_NAME)) {
            if (cityService.getCityByName(userResponse).isPresent()) {
                replyToUser = messagesService.getReplyMessage(chatId, "bot.city.exist");
                userDataCache.setCurrentUserBotState(userId, BotState.ASK_CITY_NAME);
            } else {
                replyToUser = messagesService.getReplyMessage(chatId, "bot.ask.city.info");
                cacheCity.setName(userResponse);
                userDataCache.setCurrentUserBotState(userId, BotState.ASK_CITY_INFO);
            }
        }

        if (botState.equals(BotState.ASK_CITY_INFO)) {
            cacheCity.setInfo(userResponse);
            replyToUser = new SendMessage(
                    chatId, String.format("Город %s, с инофрмацией: \n%s \nБыл добавлен!",
                    cacheCity.getName(), cacheCity.getInfo()));

            City newCity = new City();
            newCity.setName(cacheCity.getName());
            newCity.setInfo(cacheCity.getInfo());

            cityService.saveCity(newCity);
            replyToUser.setReplyMarkup(getNextButton());
        }

        userDataCache.setUserCityData(userId, cacheCity);

        return replyToUser;
    }

    private InlineKeyboardMarkup getNextButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton next = new InlineKeyboardButton().setText("Продолжить");

        next.setCallbackData("nextButton");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(next);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
