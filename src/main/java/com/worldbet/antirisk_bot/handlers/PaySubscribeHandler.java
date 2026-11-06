package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.PaymentService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class PaySubscribeHandler implements InputMessageHandler{

    private final static Logger log = LoggerFactory.getLogger(PaySubscribeHandler.class);

    private final UserService userService;
    private final LocaleMessageService localeMessageService;

    public PaySubscribeHandler(UserService userService, LocaleMessageService localeMessageService, KeyboardsService keyboardsService, PaymentService paymentService) {
        this.userService = userService;
        this.localeMessageService = localeMessageService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PAY_SUBSCRIBE;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.inputCountMonths",userLocale));

        BotState botState = BotState.GET_LINK_TO_PAYMENT;
        userService.saveBotState(userId, botState);


        return replyToUser;
    }
}
