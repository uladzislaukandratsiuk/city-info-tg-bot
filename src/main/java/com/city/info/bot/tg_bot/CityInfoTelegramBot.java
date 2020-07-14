package com.city.info.bot.tg_bot;

import com.city.info.bot.tg_bot_api.CityInfoTelegramBotFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "telegrambot")
public class CityInfoTelegramBot extends TelegramWebhookBot {

    private String botUsername;
    private String botPath;
    private String botToken;

    private final CityInfoTelegramBotFacade botFacade;

    public CityInfoTelegramBot(CityInfoTelegramBotFacade botFacade) {
        this.botFacade = botFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return botFacade.handleUpdate(update);
    }
}
