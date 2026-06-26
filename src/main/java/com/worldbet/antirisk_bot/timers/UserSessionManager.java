package com.worldbet.antirisk_bot.timers;


import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.TypeToUse;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/*@Service
public class UserSessionManager {

    private final TaskScheduler scheduler;
    private final StrategyService strategyService;
    private final UserService userService;
    private final SenderEvToTGService sender;

    private final ScheduledExecutorService workerPool = Executors.newScheduledThreadPool(20);

    private final Map<UUID, ScheduledFuture<?>> userTasks = new ConcurrentHashMap<>();

    private final Logger log = LoggerFactory.getLogger(UserSessionManager.class);

    public UserSessionManager (TaskScheduler scheduler, StrategyService strategyService, UserService userService, SenderEvToTGService sender) {
        this.scheduler = scheduler;
        this.strategyService = strategyService;
        this.userService = userService;


        this.sender = sender;
    }

    public void scheduleUser (UUID userId, Long chatId, LocalTime startTime, long hours){

        String cron = String.format("%d %d %d * * *", startTime.getSecond(),
                startTime.getMinute(),startTime.getHour());

        scheduler.schedule(
                () -> startSession(userId,chatId,hours),
                new CronTrigger(cron)
        );

    }

    private void startSession (UUID userId, Long chatId, long hours) {
        if (userTasks.containsKey(userId))
            return;

        ScheduledFuture<?> future = workerPool.scheduleAtFixedRate(
                () -> {
                    log.info("Делаю апдейт сообщений пользователю: " + userId);
                    sender.sendEventToTG(chatId);
                },
                0,
                10,
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
}*/



@Service
public class UserSessionManager {

    private final TaskScheduler scheduler;
    private final SenderEvToTGService sender;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TrialSubscribeService trialSubscribeService;
    private final UserService userService;
    private final ConvertTimeService convertTimeService;
    private final LocaleMessageService localeMessageService;
    private final Logger log = LoggerFactory.getLogger(UserSessionManager.class);

    // 1. Храним ТРИГГЕРЫ КРОНА (планирование старта)
    private final Map<UUID, ScheduledFuture<?>> scheduledCronTasks = new ConcurrentHashMap<>();

    // 2. Храним ЗАПУЩЕННЫЕ СЕССИИ (отправка сообщений каждые 5 секунд)
    private final Map<UUID, ScheduledFuture<?>> activeSessions = new ConcurrentHashMap<>();

    // 3. Храним ТАСКИ АВТООСТАНОВКИ (чтобы старая остановка не сломала новую сессию)
    private final Map<UUID, ScheduledFuture<?>> autoStopTasks = new ConcurrentHashMap<>();

    public UserSessionManager(TaskScheduler scheduler,
                              SenderEvToTGService sender, ApplicationEventPublisher applicationEventPublisher, TrialSubscribeService trialSubscribeService, UserService userService, ConvertTimeService convertTimeService, LocaleMessageService localeMessageService) {
        this.scheduler = scheduler;
        this.sender = sender;
        this.applicationEventPublisher = applicationEventPublisher;
        this.trialSubscribeService = trialSubscribeService;
        this.userService = userService;
        this.convertTimeService = convertTimeService;
        this.localeMessageService = localeMessageService;
    }

    /**
     * Планируем старт сессии по cron
     */
    public void scheduleUser(UUID userId, Long chatId, LocalTime startTime, long hours) {
        // Отменяем старый Cron, если он был
        ScheduledFuture<?> oldCron = scheduledCronTasks.remove(userId);
        if (oldCron != null) {
            oldCron.cancel(false);
        }

        String cron = String.format("%d %d %d * * *",
                startTime.getSecond(),
                startTime.getMinute(),
                startTime.getHour());

        ScheduledFuture<?> cronFuture = scheduler.schedule(
                () -> startSession(userId, chatId, hours),
                new CronTrigger(cron)
        );

        scheduledCronTasks.put(userId, cronFuture);
        log.info("User {} scheduled with cron {}", userId, cron);
    }

    /**
     * Старт сессии (ОДНА задача на пользователя)
     */
    private void startSession(UUID userId, Long chatId, long hours) {
        // Гарантированно чистим всё: и старую сессию, и старый таймер автоостановки
        resetCurrentSession(userId);

        UserEntity user;
        user = userService.findUserById(chatId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        if (!trialSubscribeService.getUseState(user)) {

            if (user.getTypeToUse()!= null) {
                Date date =  (user.getTypeToUse().equals(TypeToUse.TRIAL)) ? convertTimeService.convertLocalDateTimeToDate(user.getTrialEndDt()) : convertTimeService.convertLocalDateTimeToDate(user.getSubscribeEndDt());

                Object[] args = new Object[] {date};

                applicationEventPublisher.publishEvent(new MessageToSendEvent(this,chatId,localeMessageService.getMessage("reply.isEndUseType",userLocale,args)));

            } else {
                applicationEventPublisher.publishEvent(new MessageToSendEvent(this,chatId,localeMessageService.getMessage("reply.isNotUseType ",userLocale)));
            }

            stopSession(userId);
            return;

        }

        log.info("Starting session for user {}", userId);

        // Запуск спама каждые 5 секунд
        ScheduledFuture<?> sessionFuture = scheduler.scheduleWithFixedDelay(
                () -> {
                    try {
                        processUser(chatId);
                    } catch (Exception e) {
                        log.error("Error processing user {}", chatId, e);
                    }
                },
                Duration.ofSeconds(5)
        );
        activeSessions.put(userId, sessionFuture);

        // Планируем ОДНОРАЗОВУЮ авто-остановку
        Instant stopTime = Instant.now().plus(hours, ChronoUnit.HOURS);
        ScheduledFuture<?> stopFuture = scheduler.schedule(
                () -> autoStopCurrentSession(userId),
                stopTime
        );
        // Запоминаем её, чтобы иметь возможность отменить
        autoStopTasks.put(userId, stopFuture);
    }

    /**
     * Основная бизнес-логика (выполняется последовательно)
     */
    private void processUser(Long chatId) {
        log.info("Processing user chatId={}", chatId);
        sender.sendEventToTG(chatId);
    }

    /**
     * Полная остановка и очистка всех ресурсов пользователя
     */
    public void stopSession(UUID userId) {
        ScheduledFuture<?> sessionFuture = activeSessions.remove(userId);
        if (sessionFuture != null) {
            sessionFuture.cancel(false);
        }

        // 2. Отменяем таску автоостановки
        ScheduledFuture<?> stopFuture = autoStopTasks.remove(userId);
        if (stopFuture != null) {
            stopFuture.cancel(false);
        }

        // НАДО ДОБАВИТЬ: Полностью удаляем ежедневное расписание Cron!
        ScheduledFuture<?> cronFuture = scheduledCronTasks.remove(userId);
        if (cronFuture != null) {
            cronFuture.cancel(false);
            log.info("Daily cron schedule permanently removed for user {}", userId);
        }
    }

    private void resetCurrentSession(UUID userId) {
        ScheduledFuture<?> sessionFuture = activeSessions.remove(userId);
        if (sessionFuture != null) {
            sessionFuture.cancel(false);
        }

        ScheduledFuture<?> stopFuture = autoStopTasks.remove(userId);
        if (stopFuture != null) {
            stopFuture.cancel(false);
        }
    }


    /**
     * Автоостановка текущей сессии по таймеру.
     * Завтра Cron снова запустит задачу.
     */
    public void autoStopCurrentSession(UUID userId) {
        log.info("Auto-stopping current session for user {}. Cron stays active.", userId);

        // 1. Удаляем и останавливаем только текущую активную работу
        ScheduledFuture<?> sessionFuture = activeSessions.remove(userId);
        if (sessionFuture != null) {
            sessionFuture.cancel(false); // false, чтобы дать потоку мягко завершиться
        }

        // 2. Чистим за собой таску автоостановки
        autoStopTasks.remove(userId);
    }




}
