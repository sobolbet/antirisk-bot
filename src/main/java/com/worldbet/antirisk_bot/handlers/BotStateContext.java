package com.worldbet.antirisk_bot.handlers;


import com.worldbet.antirisk_bot.db.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap <> ();


    public BotStateContext (List<InputMessageHandler> messageHandlers) {

        messageHandlers.forEach( handler -> this.messageHandlers.put(handler.getHandlerName(), handler));

    }

    public SendMessage processInputMessage (BotState currentState, Message message) {

        InputMessageHandler currentMessageHandler = findMessageHandler (currentState);
        return currentMessageHandler.handle(message);

    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        return messageHandlers.get(currentState);
    }


}
