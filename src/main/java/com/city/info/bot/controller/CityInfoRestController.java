package com.city.info.bot.controller;

import com.city.info.bot.tg_bot.CityInfoTelegramBot;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class CityInfoRestController {

    private final CityInfoTelegramBot telegramBot;

    public CityInfoRestController(CityInfoTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
