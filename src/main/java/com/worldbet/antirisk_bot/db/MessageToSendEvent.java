package com.worldbet.antirisk_bot.db;

import org.springframework.context.ApplicationEvent;


public class MessageToSendEvent extends ApplicationEvent{


    private final Long chatId;
    private Integer messageId;
    private final String text;
    private Integer messageIdForEdit;


    public MessageToSendEvent(Object source, Long chatId, String text) {
        super(source);
        this.chatId = chatId;
        this.text = text;
    }

    public MessageToSendEvent(Object source, Long chatId, Integer messageId, String text) {
        super(source);
        this.chatId = chatId;
        this.text = text;
        this.messageId = messageId;
    }


    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public Integer getMessageId () {
        return messageId;
    }

    public Integer getMessageIdForEdit() {
        return messageIdForEdit;
    }

    public void setMessageIdForEdit(Integer messageIdForEdit) {
        this.messageIdForEdit = messageIdForEdit;
    }
}
