package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserLocale;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;
@Component
public class InputLocaleHandler implements InputMessageHandler{

    private final KeyboardsService keyboardsService;
    private final LocaleMessageService localeMessageService;
    private final UserService userService;

    public InputLocaleHandler(KeyboardsService keyboardsService, LocaleMessageService localeMessageService, UserService userService) {
        this.keyboardsService = keyboardsService;
        this.localeMessageService = localeMessageService;
        this.userService = userService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_LOCALE;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();

        UserEntity user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(UserLocale.RU.getLocale());
        if (user.getLocale()!=null) {
            userLocale = Locale.forLanguageTag(user.getLocale());
        }

        SendMessage replyToUser = new SendMessage();
        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.selectLocale",userLocale));
        replyToUser.setReplyMarkup(keyboardsService.getLocaleKeyboard());

        BotState botState = BotState.LOCALE_SELECT;
        userService.saveBotState(userId,botState);

       /* Long userId = message.getChatId();

        UserEntity user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(message.getText());

        SendMessage replyToUser = new SendMessage();
        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.hello",userLocale));*/

        return replyToUser;
    }
}
