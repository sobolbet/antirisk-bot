package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.PaymentEntity;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.PaymentService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Component
public class GetLinkToPayHandler implements InputMessageHandler{

    private final static Logger log = LoggerFactory.getLogger(GetLinkToPayHandler.class);

    private final UserService userService;
    private final PaymentService paymentService;
    private final LocaleMessageService localeMessageService;

    @Value("${cryptopay.amountMonth}")
    private Double amountMonth;

    public GetLinkToPayHandler(UserService userService, PaymentService paymentService, LocaleMessageService localeMessageService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.localeMessageService = localeMessageService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.GET_LINK_TO_PAYMENT;
    }

    @Override
    public SendMessage handle(Message message) {
        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        replyToUser.setChatId(userId);



        try {
            Integer countOfMonths = 0;

            PaymentEntity payment = null;

            BigDecimal sumPay = BigDecimal.valueOf(0);


            countOfMonths = Integer.parseInt(message.getText());

            log.debug("Месяцев подписки {}", countOfMonths);


            if (countOfMonths < 0 || countOfMonths > 12) {
                replyToUser.setText(localeMessageService.getMessage("reply.inputCountMonths", userLocale));
            } else {
                sumPay = BigDecimal.valueOf(amountMonth*countOfMonths);
                payment = paymentService.createSubscriptionPayment(user,countOfMonths,sumPay);


                replyToUser.setText(
                        "Для активации подписки перейдите по ссылке и оплатите счёт в USDT."
                );

                InlineKeyboardButton payButton =
                        new InlineKeyboardButton();

                payButton.setText("💎 Оплатить " + sumPay + " USDT");
                payButton.setUrl(payment.getPaymentUrl());

                InlineKeyboardMarkup markup =
                        new InlineKeyboardMarkup();

                markup.setKeyboard(
                        List.of(
                                List.of(payButton)
                        )
                );

                message.setReplyMarkup(markup);
                replyToUser.setReplyMarkup(markup);

                BotState botState = BotState.GET_MAIN_MENU;
                userService.saveBotState(userId, botState);


            }


        } catch (Exception e) {
            replyToUser.setText(localeMessageService.getMessage("reply.inputCountMonths", userLocale));
            log.info(e.getMessage());
        }

        return replyToUser;
    }
}
