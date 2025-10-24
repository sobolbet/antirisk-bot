package com.worldbet.antirisk_bot.services;



import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.handlers.BotStateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;
@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;
    @Value("${localeTag}") String localeTag;

    BotStateContext botStateContext;
    UserService userService;

    public MessageService  (BotStateContext botStateContext, UserService userService) {

        this.botStateContext = botStateContext;
        this.userService = userService;

    }




    public SendMessage handleUpdate (Update update)  throws TelegramApiException {


        SendMessage reply = null;

        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        String inputMessage = message.getText();

        if (!userService.isUserExisted(userId)) {
            userService.save(message.getFrom().getUserName(),userId);
        }

        BotState botState = determineState(inputMessage);


        if (botState != null) {
            userService.saveBotState(userId,botState);
        }

        BotState currentState = userService.getCurrentBotState(userId);

        reply = new SendMessage();
        reply.setChatId(userId);
        reply.setText(currentState.toString());

        return reply;

    }


    private BotState determineState (String inputMessage) {

        BotState botState = null;

        switch (inputMessage) {
            case "/start" : botState = BotState.INIT_STATE;
            break;
            case "Параметры таймера" : botState = BotState.INPUT_TIME;
            break;
            case "Параметры банка" : botState = BotState.INPUT_START_BANK;
            break;
            case "Выбрать стратегию" : botState = BotState.CHOICE_STRATEGY;
            break;
            case "Посмотреть информацию" : botState = BotState.SHOW_INFO;
            break;
            case "Запустить таймер" : botState = BotState.BEFORE_STARTING_TIMER;
            break;
            case "Получить пробный период" : botState = BotState.GET_TRIAL;
            break;
            case "Оплатить подписку" : botState = BotState.PAY_SUBSCRIBE;
            break;
            case "Остановить таймер" : botState = BotState.STOP_TIMER;
            break;
            case "Инструкция к боту" : botState = BotState.INSTRUCTION;
            break;

        }

        return botState;

    }




}
