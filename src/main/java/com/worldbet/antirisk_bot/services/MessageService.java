package com.worldbet.antirisk_bot.services;



import com.worldbet.antirisk_bot.configs.BotCommand;
import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserLocale;
import com.worldbet.antirisk_bot.handlers.BotStateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

import static com.worldbet.antirisk_bot.configs.BotCommand.*;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);


    @Autowired
    private MessageSource messageSource;
    /*@Value("${localeTag}") */String localeTag;

    BotStateContext botStateContext;
    UserService userService;
    LocaleMessageService localeMessageService;
    CommandLocalizationService commandLocalizationService;


    public MessageService  (BotStateContext botStateContext, UserService userService, LocaleMessageService localeMessageService,
                            CommandLocalizationService commandLocalizationService) {

        this.botStateContext = botStateContext;
        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.commandLocalizationService = commandLocalizationService;

    }




    public SendMessage handleUpdate (Update update)  throws TelegramApiException {


        SendMessage reply = null;

        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        String inputMessage = message.getText();

        if (!userService.isUserExisted(userId)) {
            userService.save(message.getFrom().getUserName(),userId);
        }

        BotState botState = determineState(inputMessage,userId);

        log.info("до " +  botState);


        if (botState != null) {

            if (botState.equals(BotState.INIT_STATE) && userService.findUserById(userId).getLocale()==null) {
                botState = BotState.INPUT_LOCALE;
            } else if (botState.equals(BotState.INIT_STATE) && userService.findUserById(userId).getLocale()!=null){
                botState = BotState.GET_MAIN_MENU;
            }
            userService.saveBotState(userId,botState);
        }


        BotState currentState = userService.getCurrentBotState(userId);

        log.info("после " +  currentState);




        reply = botStateContext.processInputMessage(currentState,message);

        /*reply = new SendMessage();
        reply.setChatId(userId);
        reply.setText(currentState.toString() + " ");*/

        return reply;

    }


    private BotState determineState (String inputMessage,Long userId) {


        Locale userLocale = Locale.forLanguageTag(UserLocale.RU.getLocale());

        BotCommand command = commandLocalizationService.resolveCommand(inputMessage,userLocale);


        BotState botState = null;

        if (command != null) {

            switch (command) {
                case INIT_STATE:
                    botState = BotState.INIT_STATE;
                    break;
                case TIMER_OPTIONS:
                    botState = BotState.INPUT_TIME;
                    break;
                case BANK_OPTIONS:
                    botState = BotState.INPUT_START_BANK;
                    break;
                case SELECT_STRATEGY:
                    botState = BotState.CHOICE_STRATEGY;
                    break;
                case SHOW_INFO:
                    botState = BotState.SHOW_INFO;
                    break;
                case START_TIMER:
                    botState = BotState.BEFORE_STARTING_TIMER;
                    break;
                case GET_TRIAL:
                    botState = BotState.GET_TRIAL;
                    break;
                case PAY_SUBSCRIBE:
                    botState = BotState.PAY_SUBSCRIBE;
                    break;
                case STOP_TIMER:
                    botState = BotState.STOP_TIMER;
                    break;
                case BOTS_INSTRUCTION:
                    botState = BotState.INSTRUCTION;
                    break;
                case SELECT_LANG:
                    botState = BotState.INPUT_LOCALE;
                    break;
                case SELECT_TIME_ZONE:
                    botState = BotState.INPUT_TIMEZONE;
                    break;

            }

        }

        return botState;

    }




}
