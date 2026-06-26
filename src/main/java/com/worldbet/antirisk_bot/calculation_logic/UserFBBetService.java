package com.worldbet.antirisk_bot.calculation_logic;


import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserMessagesEntity;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserFBBetService {

    UserFBBetRepository userFBBetRepository;

    public UserFBBetService(UserFBBetRepository userFBBetRepository) {
        this.userFBBetRepository = userFBBetRepository;
    }

    public ArrayList<UserFBBetEntity> getUsersFBBets (String chatId) {

        return userFBBetRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }

    public void save (UserEntity user, Double betAmount) {


        UserFBBetEntity entity = new UserFBBetEntity(user,user.getChatId(),betAmount,LocalDateTime.now());

        userFBBetRepository.save(entity);

    }


    public void deleteEntity (UserFBBetEntity entity) {
        userFBBetRepository.delete(entity);
    }

    public void deleteByChatId (String chatId) {
        userFBBetRepository.deleteByChatId(chatId);
    }



}
