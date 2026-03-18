package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserLocale;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
@Component
public class SelectLocaleHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final GetMainMenuHandler getMainMenuHandler;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SelectLocaleHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService,
                               GetMainMenuHandler getMainMenuHandler, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.getMainMenuHandler = getMainMenuHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.LOCALE_SELECT;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user ;
        Locale userLocale;
        SendMessage replyToUser = new SendMessage();


        List<String> localeList = Arrays.stream(UserLocale.values()).map(UserLocale::getLocale).toList();





        if (localeList.contains(message.getText())){
            userService.saveUserLocale(userId,message.getText());
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            user = userService.findUserById(userId);
            userLocale = Locale.forLanguageTag(user.getLocale());
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.localeSelected",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);

        } else {
            user = userService.findUserById(userId);
            if (user.getLocale()==null) {
                userLocale = Locale.forLanguageTag("ru-RU");
            } else {
                userLocale = Locale.forLanguageTag(user.getLocale());
            }

            replyToUser.setText(localeMessageService.getMessage("reply.localeNotSelected",userLocale));
            replyToUser.setChatId(userId);
        }



        return replyToUser;
    }
}
