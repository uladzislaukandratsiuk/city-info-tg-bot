package com.city.info.bot.tg_bot_api.handler;

import com.city.info.bot.tg_bot_api.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {

    BotState getHandlerName();

    SendMessage handle(Message message);
}
