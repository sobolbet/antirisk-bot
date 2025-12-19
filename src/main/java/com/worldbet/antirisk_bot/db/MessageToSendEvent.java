package com.worldbet.antirisk_bot.db;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MessageToSendEvent extends ApplicationEvent{


    private final Long chatId;
    private final String text;


    public MessageToSendEvent(Object source, Long chatId, String text) {
        super(source);
        this.chatId = chatId;
        this.text = text;
    }


    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }
}
