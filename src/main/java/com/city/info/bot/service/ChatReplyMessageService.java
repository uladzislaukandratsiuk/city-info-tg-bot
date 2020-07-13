package com.city.info.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ChatReplyMessageService {

    private final LocaleMessageService messageService;

    public ChatReplyMessageService(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(chatId, messageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, messageService.getMessage(replyMessage, args));
    }
}
