package com.city.info.bot.tg_bot_api.handler;

import com.city.info.bot.cache.UserDataCache;
import com.city.info.bot.model.City;
import com.city.info.bot.service.ChatReplyMessageService;
import com.city.info.bot.service.CityService;
import com.city.info.bot.tg_bot_api.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Slf4j
@Component
public class GetInfoByCityNameHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ChatReplyMessageService messagesService;
    private final CityService cityService;

    public GetInfoByCityNameHandler(UserDataCache userDataCache,
                                    ChatReplyMessageService messagesService,
                                    CityService cityService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.cityService = cityService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_INFO_BY_CITY_NAME;
    }

    @Override
    public SendMessage handle(Message inputMsg) {

        SendMessage replyToUser;

        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        String cityName = inputMsg.getText();

        Optional<City> cityByName = cityService.getCityByName(cityName);

        if (cityService.getCityByName(cityName).isPresent()) {

            String cityInfo = cityByName.get().getInfo();
            replyToUser = new SendMessage(chatId, cityInfo);

            log.info("chatId {}, city name{}, city info{}", chatId, cityName, cityInfo);

            userDataCache.setCurrentUserBotState(userId, BotState.GET_INFO_BY_CITY_NAME);

        } else {

            replyToUser = messagesService.getReplyMessage(chatId, "bot.city.not.found");

            userDataCache.setCurrentUserBotState(userId, BotState.CITY_NOT_FOUND);
        }

        return replyToUser;
    }


}
