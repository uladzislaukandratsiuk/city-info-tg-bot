package com.city.info.bot.config;

import com.city.info.bot.tg_bot.CityInfoTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class CityInfoBotConfig {

    private String botUsername;
    private String botPath;
    private String botToken;

    @Bean
    public CityInfoTelegramBot cityInfoTelegramBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        CityInfoTelegramBot telegramBot = new CityInfoTelegramBot(options);
        telegramBot.setBotUsername(botUsername);
        telegramBot.setBotPath(botPath);
        telegramBot.setBotToken(botToken);

        return telegramBot;
    }
}
