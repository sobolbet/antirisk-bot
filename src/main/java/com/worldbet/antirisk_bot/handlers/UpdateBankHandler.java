package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class UpdateBankHandler implements InputMessageHandler{

    private final static Logger log = LoggerFactory.getLogger(UpdateBankHandler.class);

    UserService userService;
    KeyboardsService keyboardsService;
    LocaleMessageService localeMessageService;
    GetMainMenuHandler getMainMenuHandler;
    private final ApplicationEventPublisher applicationEventPublisher;


    public UpdateBankHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService, GetMainMenuHandler getMainMenuHandler, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.keyboardsService = keyboardsService;
        this.localeMessageService = localeMessageService;
        this.getMainMenuHandler = getMainMenuHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.UPDATE_BANK;
    }

    @Override
    @Transactional
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user  = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();




        replyToUser.setChatId(userId);

        if (message.getText().equals(localeMessageService.getMessage("menu.update_bank",userLocale))) {
            userService.updateCurrentUserBank(userId, user.getBankStart());
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.bankNowUpdated",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);


                }

        else if (message.getText().equals(localeMessageService.getMessage("menu.not_update_bank",userLocale))) {

            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.bankNowNotUpdated",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);

        } else {
            replyToUser.setText(localeMessageService.getMessage("reply.bankOptionNotSelected",userLocale));
            replyToUser.setReplyMarkup(keyboardsService.getRespYesOrNo(userLocale));

        }




        return replyToUser;
    }
}
