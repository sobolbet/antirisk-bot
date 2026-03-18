package com.worldbet.antirisk_bot.timers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserRepository;
import com.worldbet.antirisk_bot.services.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimerRestorer {

    Logger log = LoggerFactory.getLogger(TimerRestorer.class);

    private final UserService userService;
    private final UserSessionManager manager;

    public TimerRestorer (UserService userService, UserSessionManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    @PostConstruct
    public void restore () {

        List<UserEntity> users = userService.getUsersByStateEqlTimerAtWork(BotState.TIMER_AT_WORK); // дописать из логики

        log.info("Вывожу список юзеров" + users.toString());

        if (!users.isEmpty()) {

            for (UserEntity u : users) {
                manager.scheduleUser(u.getId(), u.getMoscowTime(), u.getTimeJob());
            }

        }

    }

}
