package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.ConvertTimeService;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputTimeHandler implements InputMessageHandler{


private final UserService userService;
private final LocaleMessageService localeMessageService;
private final InputTimeHandler2 inputTimeHandler2;
private final ConvertTimeService convertTimeService;
private final ApplicationEventPublisher applicationEventPublisher;

private final static Logger log = LoggerFactory.getLogger(InputTimeHandler.class);

public InputTimeHandler (UserService userService, LocaleMessageService localeMessageService, InputTimeHandler2 inputTimeHandler2, ConvertTimeService convertTimeService, ApplicationEventPublisher applicationEventPublisher) {

    this.userService = userService;
    this.localeMessageService = localeMessageService;
    this.inputTimeHandler2 = inputTimeHandler2;
    this.convertTimeService = convertTimeService;
    this.applicationEventPublisher = applicationEventPublisher;
}


    @Override
    public BotState getHandlerName() {
        return BotState.INPUT_TIME;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();


        if (user.getTimeZone()== null) {

            replyToUser.setText(localeMessageService.getMessage("reply.setTimezoneForStart",userLocale));

        } else {




        replyToUser.setText(localeMessageService.getMessage("reply.inputTime1",userLocale));

        Pattern patternTime = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])");
        Matcher matcher = patternTime.matcher(message.getText());

        log.info("Сообщение " + message.getText());



            if (matcher.find()) {
                log.info("Зашёл если есть совпадение");
                BotState botState = BotState.INPUT_TIME_2;
                LocalTime sourceTime = LocalTime.parse(message.getText());
                ZoneId sourceZoneId = ZoneId.of(user.getTimeZone());
                ZoneId mscZoneId = ZoneId.of("Europe/Moscow");
                LocalDate today = LocalDate.now();
                LocalTime mscLocalTime = convertTimeService.convertTimeFromSourceToTarget(sourceTime, sourceZoneId, mscZoneId, today);
                userService.saveUserLocalTime(userId, sourceTime, mscLocalTime);
                userService.saveBotState(userId, botState);
                replyToUser = inputTimeHandler2.handle(message);
                //replyToUser.setText(localeMessageService.getMessage("reply.time1Selected",userLocale));
                applicationEventPublisher.publishEvent(new MessageToSendEvent(this, userId, localeMessageService.getMessage("reply.timeStartSet", userLocale)));
                log.info("Конец IF");
            }

        }

        replyToUser.setChatId(userId);

        return replyToUser;
    }




}
