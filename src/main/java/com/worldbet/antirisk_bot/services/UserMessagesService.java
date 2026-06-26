package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.UserMessagesEntity;
import com.worldbet.antirisk_bot.db.UserMessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserMessagesService {

    UserMessagesRepository userMessagesRepository;

    Logger log = LoggerFactory.getLogger(UserMessagesService.class);

    public UserMessagesService(UserMessagesRepository userMessagesRepository) {
        this.userMessagesRepository = userMessagesRepository;
    }



    public ArrayList<UserMessagesEntity> getUserMessages (String chatId) {
         return userMessagesRepository.findByChatId(chatId);
    }


    public void updateUserMessageEntity (UserMessagesEntity userMessagesEntity) {
        log.info("До транзакции");
        userMessagesRepository.save(userMessagesEntity);
        log.info("После транзакции");
    }

    public void deleteEntity (UserMessagesEntity entity) {
        userMessagesRepository.delete(entity);
    }

    public void removeAllMessages (UUID userId) {

        userMessagesRepository.deleteAllByUserId(userId);
    }
}
