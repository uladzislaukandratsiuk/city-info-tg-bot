package com.city.info.bot.tg_bot_api.handler.crud;

import com.city.info.bot.cache.UserDataCache;
import com.city.info.bot.service.ChatReplyMessageService;
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
public class CityCrudMenuHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ChatReplyMessageService messagesService;

    public CityCrudMenuHandler(UserDataCache userDataCache,
                           ChatReplyMessageService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CITY_CRUD_MENU;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        SendMessage replyToUser =
                messagesService.getReplyMessage(chatId, "bot.city.crud.menu");
        replyToUser.setReplyMarkup(getCrudMenuButtons());

        userDataCache.setCurrentUserBotState(userId, BotState.CITY_CRUD_MENU);

        return replyToUser;
    }

    private InlineKeyboardMarkup getCrudMenuButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton listOfCities = new InlineKeyboardButton().setText("Список");
        InlineKeyboardButton addNewCity = new InlineKeyboardButton().setText("Добавить");
        InlineKeyboardButton updateCity = new InlineKeyboardButton().setText("Обновить");
        InlineKeyboardButton removeCity = new InlineKeyboardButton().setText("Удалить");

        listOfCities.setCallbackData("listOfCitiesButton");
        addNewCity.setCallbackData("addNewCityButton");
        updateCity.setCallbackData("updateCityButton");
        removeCity.setCallbackData("removeCityButton");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(listOfCities);
        keyboardButtonsRow1.add(addNewCity);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(updateCity);
        keyboardButtonsRow1.add(removeCity);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
