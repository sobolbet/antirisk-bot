package com.worldbet.antirisk_bot.timers;


import com.worldbet.antirisk_bot.services.StrategyService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class UserSessionManager {

    private final TaskScheduler scheduler;
    private final StrategyService strategyService;
    private final UserService userService;

    private final ScheduledExecutorService workerPool = Executors.newScheduledThreadPool(20);

    private final Map<UUID, ScheduledFuture<?>> userTasks = new ConcurrentHashMap<>();

    private final Logger log = LoggerFactory.getLogger(UserSessionManager.class);

    public UserSessionManager (TaskScheduler scheduler, StrategyService strategyService, UserService userService) {
        this.scheduler = scheduler;
        this.strategyService = strategyService;
        this.userService = userService;


    }

    public void scheduleUser (UUID userId, LocalTime startTime, long hours){

        String cron = String.format("%d %d %d * * *", startTime.getSecond(),
                startTime.getMinute(),startTime.getHour());

        scheduler.schedule(
                () -> startSession(userId,hours),
                new CronTrigger(cron)
        );

    }

    private void startSession (UUID userId, long hours) {
        if (userTasks.containsKey(userId))
            return;

        ScheduledFuture<?> future = workerPool.scheduleAtFixedRate(
                () -> {//добавить выполняемую логику либо из strategyService и userService,
                    // либо добавить новый сервис с логикой
                    log.info("Вывожу сообщение пользователю : " + userId);

                },
                0,
                5,
                TimeUnit.SECONDS

        );

        userTasks.put(userId,future);

        Instant stopTime = Instant.now().plus(hours, ChronoUnit.HOURS);

        scheduler.schedule(
                () -> stopSession(userId),
                stopTime
        );
    }

    public void stopSession (UUID userId) {
        ScheduledFuture<?> future = userTasks.remove(userId);

        if (future != null) {
            future.cancel(false);
        }
    }
}
