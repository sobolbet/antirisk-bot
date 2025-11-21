package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GetTrialHandler implements InputMessageHandler{
    @Override
    public BotState getHandlerName() {
        return null;
    }

    @Override
    public SendMessage handle(Message message) {
        return null;
    }
}
