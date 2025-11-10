package com.worldbet.antirisk_bot.handlers;


import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private static final Logger log = LoggerFactory.getLogger(BotStateContext.class);

    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap <> ();


    public BotStateContext (List<InputMessageHandler> messageHandlers) {

        messageHandlers.forEach( handler -> {
            this.messageHandlers.put(handler.getHandlerName(), handler);
            log.info("Регистрация хэндлера: {}", handler.getHandlerName());
        });



        log.info("Размер листа с хендлерами" +  messageHandlers.size());

    }

    public SendMessage processInputMessage (BotState currentState, Message message) {
        log.info("внутри " +  currentState);

        InputMessageHandler currentMessageHandler = findMessageHandler (currentState);

        log.info("после внутри" +  currentMessageHandler.getHandlerName());

        return currentMessageHandler.handle(message);

    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        return messageHandlers.get(currentState);
    }


}
