package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.TypeToUse;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.*;
import com.worldbet.antirisk_bot.timers.UserSessionManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Date;
import java.util.Locale;

@Component
public class StartTimerHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final UserSessionManager manager;
    private final TrialSubscribeService trialSubscribeService;
    private final ConvertTimeService convertTimeService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GetMainMenuHandler getMainMenuHandler;

    public StartTimerHandler(UserService userService, LocaleMessageService localeMessageService, UserSessionManager manager, TrialSubscribeService trialSubscribeService, ConvertTimeService convertTimeService, ApplicationEventPublisher applicationEventPublisher, GetMainMenuHandler getMainMenuHandler) {

        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.manager = manager;
        this.trialSubscribeService = trialSubscribeService;
        this.convertTimeService = convertTimeService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.getMainMenuHandler = getMainMenuHandler;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.BEFORE_STARTING_TIMER;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user;
        user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();

        if (!trialSubscribeService.getUseState(user)) {

            if (user.getTypeToUse()!= null) {
            Date date =  (user.getTypeToUse().equals(TypeToUse.TRIAL)) ? convertTimeService.convertLocalDateTimeToDate(user.getTrialEndDt()) : convertTimeService.convertLocalDateTimeToDate(user.getSubscribeEndDt());

            Object[] args = new Object[] {date};

                replyToUser.setText(localeMessageService.getMessage("reply.isEndUseType",userLocale,args));

            } else {
                replyToUser.setText(localeMessageService.getMessage("reply.isNotUseType",userLocale));
            }



            replyToUser.setChatId(userId);
            return replyToUser;
        }

        if (user.getId()==null || user.getChatId()== null || user.getMoscowTime() == null || user.getTimeJob() == null
                || user.getStrategy()== null || user.getBankStart() == null || user.getTimezoneUtc() == null) {

            replyToUser = getMainMenuHandler.handle(message);

        } else {
            BotState botState = BotState.TIMER_AT_WORK;
            userService.saveBotState(userId,botState);

            manager.scheduleUser(user.getId(),Long.valueOf(user.getChatId()),user.getMoscowTime(),user.getTimeJob());



            replyToUser.setChatId(userId);
            replyToUser.setText(localeMessageService.getMessage("reply.timerAtWork",userLocale));


        }

        return replyToUser;

    }
}
