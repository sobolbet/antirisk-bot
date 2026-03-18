package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.EventSearchService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import com.worldbet.antirisk_bot.timers.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class StartTimerHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final UserSessionManager manager;

    public StartTimerHandler(UserService userService, LocaleMessageService localeMessageService, UserSessionManager manager) {

        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.manager = manager;
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

        BotState botState = BotState.TIMER_AT_WORK;
        userService.saveBotState(userId,botState);

        manager.scheduleUser(user.getId(),user.getMoscowTime(),user.getTimeJob());



        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.timerAtWork",userLocale));

        return replyToUser;
    }
}
