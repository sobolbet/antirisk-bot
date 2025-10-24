package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.BotState;
//import com.worldbet.antirisk_bot.db.PaymentRepository;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;




    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public BotState getCurrentBotState (Long userId) {

        BotState botState;

        botState = userRepository.getStatusByChatId(userId.toString());

        return  botState;

    }

    public void saveBotState (Long userId, BotState botState) {

        userRepository.updateStatus(userId.toString(),botState);

    }


    public void save (String userName, Long chatId) {

        UserEntity user = new UserEntity(userName,chatId.toString());

        userRepository.save(user);

    }

    public Boolean isUserExisted (Long chatId) {

        userRepository.findUserByChatId(chatId.toString());

        return userRepository.findUserByChatId(chatId.toString()) != null;



    }


}
