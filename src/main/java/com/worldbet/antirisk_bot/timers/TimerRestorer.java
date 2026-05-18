package com.worldbet.antirisk_bot.timers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserRepository;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class TimerRestorer {

    Logger log = LoggerFactory.getLogger(TimerRestorer.class);

    private final UserService userService;
    private final UserSessionManager manager;
    private final LocaleMessageService localeMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TimerRestorer (UserService userService, UserSessionManager manager, LocaleMessageService localeMessageService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.manager = manager;
        this.localeMessageService = localeMessageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //@PostConstruct
    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void restore () {

        log.info("Starting restoration of active user timers...");

        List<UserEntity> users = userService.getUsersByStateEqlTimerAtWork(BotState.TIMER_AT_WORK); // дописать из логики


        log.info("Вывожу список юзеров: {}", users);


        if (users == null || users.isEmpty()) {
            log.info("No active users found to restore.");
            return;
        }



            for (UserEntity u : users) {
                try {
                manager.scheduleUser(u.getId(), Long.valueOf(u.getChatId()), u.getMoscowTime(), u.getTimeJob());
                Locale userLocale = Locale.forLanguageTag(u.getLocale());
                String messageText = localeMessageService.getMessage("reply.restoreMessage", userLocale);
                MessageToSendEvent message = new MessageToSendEvent(this, Long.valueOf(u.getChatId()), messageText);
                applicationEventPublisher.publishEvent(message);
                } catch (Exception e ) {
                    log.error("Failed to restore timer/message for user ID: {}", u.getId(), e);
                }
            }


        log.info("Successfully requested restoration for {} users.", users.size());

    }


}
