package com.city.info.bot.tg_bot_api;

import com.city.info.bot.tg_bot_api.handler.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private final Map<BotState, InputMessageHandler> messageHandlers;

    public BotStateContext(List<InputMessageHandler> listOfMessageHandlers) {
        this.messageHandlers = new HashMap<>();

        listOfMessageHandlers.forEach(handler ->
                this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isFillingCityDataState(currentState)) {
            return messageHandlers.get(BotState.FILLING_CITY_DATA);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isFillingCityDataState(BotState currentState) {
        switch (currentState) {
            case ASK_CITY_NAME:
            case ASK_CITY_INFO:
            case FILLING_CITY_DATA:
            case CITY_DATA_FILLED:
                return true;
            default:
                return false;
        }
    }
}
