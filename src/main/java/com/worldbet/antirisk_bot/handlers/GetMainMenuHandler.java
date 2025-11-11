package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;
@Component
public class GetMainMenuHandler implements InputMessageHandler{

    private final UserService userService;
    private final KeyboardsService keyboardsService;
    private final LocaleMessageService localeMessageService;

    public GetMainMenuHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService) {
        this.userService = userService;
        this.keyboardsService = keyboardsService;
        this.localeMessageService = localeMessageService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.GET_MAIN_MENU;
    }

    @Override
    public SendMessage handle(Message message) {
        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        replyToUser.setText(localeMessageService.getMessage("reply.getMainMenu",userLocale));
        replyToUser.setReplyMarkup(keyboardsService.getMainMenu(userLocale));
        replyToUser.setChatId(userId);

        return replyToUser;
    }
}
