package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class InputTimeHandler implements InputMessageHandler{

private final MessageSource messageSource;

public InputTimeHandler (MessageSource messageSource) {
    this.messageSource = messageSource;
}


    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_TIME;
    }

    @Override
    public SendMessage handle(Message message) {
        return null;
    }




}
