package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.StrategyEntity;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import jakarta.validation.spi.BootstrapState;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
public class ViewInfoHandler implements InputMessageHandler {

    private final LocaleMessageService localeMessageService;
    private final UserService userService;

    public ViewInfoHandler(LocaleMessageService localeMessageService, UserService userService) {
        this.localeMessageService = localeMessageService;
        this.userService = userService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_INFO;
    }

    @Override
    public SendMessage handle(Message message) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        Long userId = message.getChatId();
        UserEntity user  = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();

        Optional<LocalTime> optionalTime = Optional.ofNullable(user.getLocalTime());

        String timeStr = optionalTime.map(time -> time.format(formatter)).orElse("null");

        Optional<StrategyEntity> userStrategy = Optional.ofNullable(user.getStrategy());

        String strategyName = userStrategy.map(StrategyEntity::getName).orElse("-");

        Object[] args = new Object[] {user.getChatId(), strategyName, timeStr ,
                user.getTimeJob(), user.getBankStart(), user.getBankNow()};

        replyToUser.setChatId(userId);
        replyToUser.setText(localeMessageService.getMessage("reply.showInfo", userLocale , args));


        return replyToUser;
    }
}
