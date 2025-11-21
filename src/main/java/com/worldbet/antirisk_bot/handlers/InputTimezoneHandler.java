package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class InputTimezoneHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final KeyboardsService keyboardsService;


    public InputTimezoneHandler(UserService userService, LocaleMessageService localeMessageService, KeyboardsService keyboardsService) {
        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.keyboardsService = keyboardsService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_TIMEZONE;
    }

    @Override
    public SendMessage handle(Message message) {



        Long userId = message.getChatId();
        SendMessage replyToUser = new SendMessage();
        UserEntity user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());



        replyToUser.setText(localeMessageService.getMessage("reply.selectTimezone", userLocale));
        replyToUser.setReplyMarkup(keyboardsService.getTimezoneKeyboard());
        replyToUser.setChatId(userId);

        BotState botState = BotState.SELECT_TIMEZONE;
        userService.saveBotState(userId,botState);

        return replyToUser;
    }
}
