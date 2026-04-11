package com.worldbet.antirisk_bot.timers;

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


    public SenderEvToTGService(ApplicationEventPublisher applicationEventPublisher, GamesService gamesService, UserService userService, UserMessagesService userMessagesService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.gamesService = gamesService;
        this.userService = userService;
        this.userMessagesService = userMessagesService;
    }



    public void sendEventToTG (Long chatId) {

        //Сначала пробегаемся по сообщениям пользака, если они есть , обновляем сообщения , если игра не закончилась или удаляем , если закончилась

        ArrayList <UserMessagesEntity> messageList =  userMessagesService.getUserMessages(chatId.toString());

        log.info("Перед проверкой листа сообщений на отсутствие событий \n" + messageList.size());

        if (!messageList.isEmpty()) {
            log.info("Зашёл если у пользака есть сообщения");
            for (UserMessagesEntity u : messageList) {
                Optional<GameEntity> gameOpt = gamesService.getGameByEventId(u.getEventId());
                GameEntity game = null;
                if (gameOpt.isPresent()){
                    game = gameOpt.get();

                    log.info("Зашёл если есть такое событие");

                if (!u.getRoundNumNow().equals(game.getRoundNumNow()) && !u.getLastUpdateAt().equals(game.getUpdatedAt())){

                    log.info("Зашёл для редактирования и отправки сообщения");

                    u.setRoundNumNow(game.getRoundNumNow());
                    u.setLastUpdateAt(game.getUpdatedAt());
                    userMessagesService.updateUserMessageEntity(u);

                    MessageToSendEvent message = new MessageToSendEvent(this,chatId, u.getMessageId(), "EventId = " + u.getEventId()
                            + "\nПара:" + game.getF1() + game.getF2()
                            + "\nNumEvent: " + game.getGameNum()
                            + "\nНомер раунда сейчас = " + u.getRoundNumNow());
                    applicationEventPublisher.publishEvent(message);

                }
                if (game.getGameWasEnd()) {
                    log.info("Зашёл для удаления сообщения");
                    userMessagesService.deleteEntity(u);
                }

                }
            }
        }


        //Ищем игры соответствующие стратегии , если они есть , парсим и добавляем в таблицу сообщений пользака

        UserEntity user = userService.findUserById(chatId);
        log.info("Получен пользователь = " + user.getChatId());
        StrategyEntity strategy = user.getStrategy();
        log.info("Получена стратегия = " + strategy.getName());
        ArrayList<Pair> pairs = strategy.getStrategyParams().getPairs();
        log.info("Размер списка пар = " + pairs.size());
        ArrayList<GameEntity> games = gamesService.getActiveGames(LocalDate.now());
        log.info("Размер списка активных игр = " + games.size());


        for (GameEntity game : games) {

            if (pairs.stream().anyMatch(p ->
                  game.getF1().equals(p.getF1()) && game.getF2().equals(p.getF2())
            )){


                if (messageList.stream().noneMatch(u -> u.getEventId().equals(game.getEventId()))) {

                    log.info("Зашёл для публикации нового сообщения, если соответствует стратегии и такого сообщения нет в списке сообщений");

                    MessageToSendEvent message = new MessageToSendEvent(this, chatId, "EventId = " + game.getEventId() + "\nПара:" + game.getF1() + game.getF2()
                            + "\nNumEvent: " + game.getGameNum()
                            + "\nНомер раунда сейчас = null");
                    applicationEventPublisher.publishEvent(message);
                    if (message.getMessageIdForEdit() != null) {
                        log.info("message id = " + message.getMessageIdForEdit());
                        UserMessagesEntity userMessagesEntity = new UserMessagesEntity(user, chatId.toString(), message.getMessageIdForEdit(), game.getEventId(), game.getRoundNumNow(), game.getUpdatedAt());
                        userMessagesService.updateUserMessageEntity(userMessagesEntity);
                    } else {
                        throw new RuntimeException("Не удалось получить message id");
                    }

                }

            }
        }




        log.info("Выход из потока");

    }

}
