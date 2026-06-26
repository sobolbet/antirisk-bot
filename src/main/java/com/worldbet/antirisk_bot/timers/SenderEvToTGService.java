package com.worldbet.antirisk_bot.timers;

import com.worldbet.antirisk_bot.calculation_logic.CalculationService;
import com.worldbet.antirisk_bot.db.*;
import com.worldbet.antirisk_bot.db.models.Pair;
import com.worldbet.antirisk_bot.services.GamesService;
import com.worldbet.antirisk_bot.services.UserMessagesService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class SenderEvToTGService {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Logger log = LoggerFactory.getLogger(SenderEvToTGService.class);

    private final GamesService gamesService;
    private final UserService userService;
    private final UserMessagesService userMessagesService;
    private final CalculationService calculationService;


    public SenderEvToTGService(ApplicationEventPublisher applicationEventPublisher, GamesService gamesService, UserService userService, UserMessagesService userMessagesService, CalculationService calculationService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.gamesService = gamesService;
        this.userService = userService;
        this.userMessagesService = userMessagesService;
        this.calculationService = calculationService;
    }


    //todo пока не знаю где нужно сделать , что бы события рядом не брались для FB +- нужно проверить
    //todo исправить проблему с пустое сообщение , что бы не было ошибки , если сообщение не изменено +


    public void sendEventToTG (Long chatId) {

        //Сначала пробегаемся по сообщениям пользака, если они есть , обновляем сообщения , если игра не закончилась или удаляем , если закончилась

        ArrayList<UserMessagesEntity> messageList = userMessagesService.getUserMessages(chatId.toString());

        UserEntity user = userService.findUserById(chatId);
        log.info("Получен пользователь = {}", user.getChatId());
        StrategyEntity strategy = user.getStrategy();
        log.info("Получена стратегия = {}", strategy.getName());



        /*Блок проверочного кода , убрать позже


        if (messageList.isEmpty()) {
            //UserEntity user = userService.findUserById(chatId);

            MessageToSendEvent message = new MessageToSendEvent(this, chatId, "Проверочное сообщение");
            applicationEventPublisher.publishEvent(message);


            if (message.getMessageIdForEdit() != null) {
                log.info("message id = " + message.getMessageIdForEdit());
                UserMessagesEntity userMessagesEntity = new UserMessagesEntity(user, chatId.toString(), message.getMessageIdForEdit(), "777", 1, LocalDateTime.now(),0);
                userMessagesService.updateUserMessageEntity(userMessagesEntity);
            } else {
                throw new RuntimeException("Не удалось получить message id");
            }

        } else {

            for (UserMessagesEntity u : messageList) {
                if (u.getEventId().equals("777")) {
                    u.setLastUpdateAt(LocalDateTime.now());
                    userMessagesService.updateUserMessageEntity(u);

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");

                    String messageText = "Проверочное сообщение \n" + "Текущее дата/время " + LocalDateTime.now().format(format);

                    MessageToSendEvent message = new MessageToSendEvent(this,chatId, u.getMessageId(),  messageText);
                    applicationEventPublisher.publishEvent(message);
                }

            }
        }
 */
        // закрывается блок проверочного кода
        log.info("Перед проверкой листа сообщений на отсутствие событий {}", messageList.size());

        try {


            if (!messageList.isEmpty()) {
                log.info("Зашёл если у пользака есть сообщения");
                for (UserMessagesEntity u : messageList) {
                    Optional<GameEntity> gameOpt = gamesService.getGameByEventId(u.getEventId());
                    GameEntity game = null;
                    if (gameOpt.isPresent()) {
                        game = gameOpt.get();

                        log.info("Зашёл если есть такое событие");

                        if ((/*!u.getRoundNumNow().equals(game.getRoundNumNow()) &&*/ !u.getLastUpdateAt().equals(game.getUpdatedAt())) || game.getGameWasEnd()) {

                            log.info("Зашёл для редактирования и отправки сообщения");

                            log.info("EventID(mess) = {} До обновления  время сообщения : {} \n Время игры {}", u.getEventId(), u.getLastUpdateAt(), game.getUpdatedAt());


                            u.setRoundNumNow(game.getRoundNumNow());
                            u.setLastUpdateAt(game.getUpdatedAt());
                            userMessagesService.updateUserMessageEntity(u);


                            String messageText = "пустое сообщение";

                            switch (strategy.getName()) {
                                case "FAT_FLET_5_ROUNDS":
                                    messageText = calculationService.fletCalculation(game, chatId);
                                    break;
                                case "FAT_DOGON_3_ROUNDS":
                                    messageText = calculationService.dogonCalculation(game, chatId);
                                    break;
                                case "FAT_FB_3_ROUNDS":

                                    if (u.getEventFocus() == 1 || u.getEventFocus() == 2) {
                                        messageText = calculationService.fbCalculation(game, chatId);
                                    }
                                    break;
                            }

                            //String messageText = calculationService.fletCalculation(game,chatId);

                            MessageToSendEvent message = new MessageToSendEvent(this, chatId, u.getMessageId(), /*"EventId = " + u.getEventId()
                            + "\nПара:" + game.getF1() + game.getF2()
                            + "\nNumEvent: " + game.getGameNum()
                            + "\nНомер раунда сейчас = " + u.getRoundNumNow()*/ messageText);
                            applicationEventPublisher.publishEvent(message);

                        }
                        if (game.getGameWasEnd()) {
                            log.info("Зашёл для удаления сообщения");
                            userMessagesService.deleteEntity(u);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            log.error("Ошибка runtime при отправке в ТГ", ex);
        }


        LocalTime timeEndTimer = user.getMoscowTime().plusHours(user.getTimeJob());
        LocalTime timeNow = LocalTime.now();

        long diff = ChronoUnit.MINUTES.between(timeNow,timeEndTimer);

        if (diff<0) {
            diff += 24 * 60;
        }

        if (diff>18) {

        //Ищем игры соответствующие стратегии , если они есть , парсим и добавляем в таблицу сообщений пользака

        /*UserEntity user = userService.findUserById(chatId);
        log.info("Получен пользователь = " + user.getChatId());
        StrategyEntity strategy = user.getStrategy();
        log.info("Получена стратегия = " + strategy.getName());*/
        ArrayList<Pair> pairs = strategy.getStrategyParams().getPairs();
        log.info("Размер списка пар = {}", pairs.size());
        LocalDateTime now = LocalDateTime.now();
        ArrayList<GameEntity> games = gamesService.getActiveGamesWithRoundNumZero(now.plusMinutes(10).toLocalDate(), now.minusMinutes(30).toLocalDate());
        ArrayList<GameEntity> allActiveGames = gamesService.getActiveGames(now.plusMinutes(10).toLocalDate(), now.minusMinutes(30).toLocalDate());
        log.info("Размер списка всех активных игр = {}", allActiveGames.size());
        log.info("Размер списка активных игр до начала = {}", games.size());


        for (GameEntity game : games) {

            Integer eventFocus = 0;

            if (pairs.stream().anyMatch(p ->
                    game.getF1().equals(p.getF1()) && game.getF2().equals(p.getF2())
            )) {

                log.info("Зашёл если пара совпала GameEventId = {}", game.getEventId());

                if (messageList.stream().noneMatch(u -> u.getEventId().equals(game.getEventId()))) {

                    log.info("Зашёл для публикации нового сообщения, если соответствует стратегии и такого сообщения нет в списке сообщений");


                    String messageText = "пустое сообщение";

                    switch (strategy.getName()) {
                        case "FAT_FLET_5_ROUNDS":
                            messageText = calculationService.fletCalculation(game, chatId);
                            break;
                        case "FAT_DOGON_3_ROUNDS":
                            messageText = calculationService.dogonCalculation(game, chatId);
                            break;
                        case "FAT_FB_3_ROUNDS":

                            //todo дописать логику присвоения фокуса

                            if (messageList.stream().noneMatch(u -> u.getEventFocus() == 1)) {
                                eventFocus = 1;
                                messageText = calculationService.fbCalculation(game, chatId);
                            } else {
                                continue;
                            }
                            break;
                    }


                    MessageToSendEvent message = new MessageToSendEvent(this, chatId, /*"EventId = " + game.getEventId() + "\nПара:" + game.getF1() + game.getF2()
                            + "\nNumEvent: " + game.getGameNum()
                            + "\nНомер раунда сейчас = null"*/ messageText);
                    applicationEventPublisher.publishEvent(message);
                    if (message.getMessageIdForEdit() != null) {
                        log.info("message id = {}", message.getMessageIdForEdit());
                        UserMessagesEntity userMessagesEntity = new UserMessagesEntity(user, chatId.toString(), message.getMessageIdForEdit(), game.getEventId(), game.getRoundNumNow(), game.getUpdatedAt(), eventFocus);
                        userMessagesService.updateUserMessageEntity(userMessagesEntity);
                    } else {
                        throw new RuntimeException("Не удалось получить message id");
                    }

                }

            }
        }

    }




        log.info("Выход из потока");

    }

}
