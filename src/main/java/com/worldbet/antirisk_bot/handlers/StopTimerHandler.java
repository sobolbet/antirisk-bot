package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import com.worldbet.antirisk_bot.timers.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class StopTimerHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final UserSessionManager manager;

    public StopTimerHandler(UserService userService, LocaleMessageService localeMessageService, UserSessionManager manager) {
        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.manager = manager;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.STOP_TIMER;
    }

    @Override
    public SendMessage handle(Message message) {
        Long userId = message.getChatId();
        UserEntity user;
        user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();

        /*BotState botState = BotState.STOP_TIMER;
        userService.saveBotState(userId,botState);*/

        manager.stopSession(user.getId());



        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.stoppedTimer",userLocale));

        return replyToUser;
    }
}
