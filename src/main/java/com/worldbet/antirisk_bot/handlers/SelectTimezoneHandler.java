package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.TimeZoneService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class SelectTimezoneHandler implements InputMessageHandler{

    private final LocaleMessageService localeMessageService;
    private final UserService userService;
    private final TimeZoneService timeZoneService;
    private final GetMainMenuHandler getMainMenuHandler;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SelectTimezoneHandler(LocaleMessageService localeMessageService, UserService userService, TimeZoneService timeZoneService,
                                 GetMainMenuHandler getMainMenuHandler, ApplicationEventPublisher applicationEventPublisher) {
        this.localeMessageService = localeMessageService;
        this.userService = userService;
        this.timeZoneService = timeZoneService;
        this.getMainMenuHandler = getMainMenuHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SELECT_TIMEZONE;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user;
        user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());


        if (timeZoneService.getKeySet().contains(message.getText())){
            userService.saveUserTimezone(userId,timeZoneService.getStringTimezone(message.getText()));
            userService.saveUserTimezoneUtc(userId,message.getText());
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.timeZoneSelected",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);

        } else {
            if (user.getTimeZone()==null) {
                userService.saveUserTimezone(userId,timeZoneService.getStringTimezone("UTC+03:00"));
                userService.saveUserTimezoneUtc(userId,"UTC+03:00");
                BotState botState = BotState.GET_MAIN_MENU;
                userService.saveBotState(userId,botState);
            }


            replyToUser = getMainMenuHandler.handle(message);
            replyToUser.setText(localeMessageService.getMessage("reply.timezoneNotSelected",userLocale));
            replyToUser.setChatId(userId);
        }

        return replyToUser;
    }
}
