package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.TypeToUse;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

@Component
public class GetTrialHandler implements InputMessageHandler{

    private final Logger log = LoggerFactory.getLogger(GetTrialHandler.class);


    private final GetMainMenuHandler getMainMenuHandler;
    private final LocaleMessageService localeMessageService;
    private final UserService userService;
    private final KeyboardsService keyboardsService;
    private  final ApplicationEventPublisher applicationEventPublisher;
    private final ConvertTimeService convertTimeService;
    private final TrialSubscribeService trialSubscribeService;

    public GetTrialHandler (GetMainMenuHandler getMainMenuHandler, LocaleMessageService localeMessageService, UserService userService,
                            KeyboardsService keyboardsService, ApplicationEventPublisher applicationEventPublisher, ConvertTimeService convertTimeService, TrialSubscribeService trialSubscribeService){
        this.getMainMenuHandler = getMainMenuHandler;
        this.localeMessageService = localeMessageService;
        this.userService = userService;
        this.keyboardsService = keyboardsService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.convertTimeService = convertTimeService;
        this.trialSubscribeService = trialSubscribeService;
    }



    @Override
    public BotState getHandlerName() {
        return BotState.GET_TRIAL;
    }

    @Override
    @Transactional
    public SendMessage handle(Message message) {


        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser;
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        if (user.getTypeToUse()==null && user.getTrialStartDt() == null && user.getTrialEndDt() == null) {
            trialSubscribeService.activateTrial(user,LocalDateTime.now());
            user = userService.findUserById(userId);
            Date date = convertTimeService.convertLocalDateTimeToDate(user.getTrialStartDt());
            Object[] args = new Object[] {date};
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,
                    userId,localeMessageService.getMessage("reply.setTrial",userLocale,args)));
        } else if (user.getTypeToUse().equals(TypeToUse.TRIAL) && (LocalDateTime.now().isAfter(user.getTrialStartDt())
                && LocalDateTime.now().isBefore(user.getTrialEndDt()))) {
            user = userService.findUserById(userId);
            Date date = convertTimeService.convertLocalDateTimeToDate(user.getTrialStartDt());
            Object[] args = new Object[] {date};
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,
                    localeMessageService.getMessage("reply.isTrial",userLocale,args)) );
            log.info("Зашёл , если  true");
            //antiRiskBotCore.sendMessage(userId, localeMessageService.getMessage("reply.isTrial",userLocale));
        } else if (LocalDateTime.now().isAfter(user.getTrialEndDt())) {
            user = userService.findUserById(userId);
            Date date = convertTimeService.convertLocalDateTimeToDate(user.getTrialEndDt());
            Object[] args = new Object[] {date};
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,
                    localeMessageService.getMessage("reply.isNotTrial",userLocale,args)));
            //antiRiskBotCore.sendMessage(userId, localeMessageService.getMessage("reply.isNotTrial",userLocale));
            log.info("Зашёл , если  false");
        }

        BotState botState = BotState.GET_MAIN_MENU;
        userService.saveBotState(userId,botState);

        replyToUser = getMainMenuHandler.handle(message);


        return replyToUser;
    }
}
