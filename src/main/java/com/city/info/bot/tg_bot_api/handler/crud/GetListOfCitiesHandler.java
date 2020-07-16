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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class GetListOfCitiesHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ChatReplyMessageService messagesService;
    private final CityService cityService;

    public GetListOfCitiesHandler(UserDataCache userDataCache,
                                  ChatReplyMessageService messagesService,
                                  CityService cityService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.cityService = cityService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_LIST_OF_CITIES;
    }

    @Override
    public SendMessage handle(Message message) {

        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        SendMessage replyToUser;

        List<City> cities = cityService.getAllCities();

        if (cities.isEmpty()) {
            replyToUser = messagesService
                    .getReplyMessage(chatId, "bot.cities.not.exist");
            userDataCache.setCurrentUserBotState(userId, BotState.CITY_CRUD_MENU);
        } else {
            StringBuilder citiesNames = new StringBuilder();
            List<String> listOfCitiesNames = new ArrayList<>();
            cities.forEach(city -> listOfCitiesNames.add(city.getName()));
            listOfCitiesNames.sort(Comparator.naturalOrder());
            listOfCitiesNames.forEach(name -> citiesNames.append(name).append("\n"));

            replyToUser = new SendMessage(chatId, String.valueOf(citiesNames));
            userDataCache.setCurrentUserBotState(userId, BotState.GET_INFO_BY_CITY_NAME);
        }

        return replyToUser;
    }
}
