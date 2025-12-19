package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputTimeHandler2 implements InputMessageHandler{


private final UserService userService;
private final LocaleMessageService localeMessageService;
private final GetMainMenuHandler getMainMenuHandler;
private final ApplicationEventPublisher applicationEventPublisher;

private final static Logger log = LoggerFactory.getLogger(InputTimeHandler2.class);

public InputTimeHandler2(UserService userService, LocaleMessageService localeMessageService,
                         GetMainMenuHandler getMainMenuHandler, ApplicationEventPublisher applicationEventPublisher) {

    this.userService = userService;
    this.localeMessageService = localeMessageService;
    this.getMainMenuHandler = getMainMenuHandler;
    this.applicationEventPublisher = applicationEventPublisher;
}


    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_TIME_2;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();




        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.inputTime2",userLocale));

        Pattern patternTime = Pattern.compile("^(?:[01]\\d|2[0-4])$");
        Matcher matcher = patternTime.matcher(message.getText());

        log.info("Сообщение " + message.getText());

        if (matcher.find()) {
            log.info("Зашёл если есть совпадение");
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveUserTimeJob(userId,message.getText());
            userService.saveBotState(userId,botState);
            //replyToUser.setText(localeMessageService.getMessage("reply.time1Selected",userLocale));
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId ,
                    localeMessageService.getMessage("reply.timeWorkSet",userLocale) ));
            replyToUser = getMainMenuHandler.handle(message);
            log.info("Конец IF");
        }

        return replyToUser;
    }




}
