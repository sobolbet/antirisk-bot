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

import java.io.IOException;
import java.util.Locale;

@Component
public class SetBankOptionsHandler implements InputMessageHandler{

    private final static Logger log = LoggerFactory.getLogger(SetBankOptionsHandler.class);

    UserService userService;
    KeyboardsService keyboardsService;
    LocaleMessageService localeMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;


    public SetBankOptionsHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.keyboardsService = keyboardsService;
        this.localeMessageService = localeMessageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_START_BANK;
    }

    @Override
    @Transactional
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user  = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();



        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.setBank",userLocale));

        if (!message.getText().equals(localeMessageService.getMessage("reply.setBank",userLocale))) {

            try {
                Double startBank = 0.0;


                startBank = Double.parseDouble(message.getText());


                if (startBank < 0 || startBank > 1000000000) {
                    replyToUser.setText(localeMessageService.getMessage("reply.errorSetBank", userLocale));
                } else {
                    BotState botState = BotState.UPDATE_BANK;
                    userService.saveBotState(userId, botState);
                    userService.saveUserBank(userId, startBank);
                    user = userService.findUserById(userId);
                    if (user.getBankNow()== null) {
                        userService.updateCurrentUserBank(userId, startBank);
                    }
                    applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.bankStartSet", userLocale)));
                    replyToUser.setText(localeMessageService.getMessage("reply.updateBankQuestion", userLocale));
                    replyToUser.setReplyMarkup(keyboardsService.getRespYesOrNo(userLocale));
                }


            } catch (Exception e) {
                replyToUser.setText(localeMessageService.getMessage("reply.setBank", userLocale));
                log.info(e.getMessage());
            }

        }


        return replyToUser;
    }
}
