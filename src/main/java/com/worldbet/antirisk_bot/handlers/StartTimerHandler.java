package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.services.EventSearchService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartTimerHandler implements InputMessageHandler{

    private final EventSearchService eventSearchService;

    public StartTimerHandler(EventSearchService eventSearchService) {
        this.eventSearchService = eventSearchService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.TIMER_AT_WORK;
    }

    @Override
    public SendMessage handle(Message message) {


        eventSearchService.searchEvents();

        return null;
    }
}
