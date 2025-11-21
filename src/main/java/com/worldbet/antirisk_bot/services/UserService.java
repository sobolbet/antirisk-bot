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

        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setState(botState);
        userRepository.save(user);

    }


    public UserEntity findUserById (Long userId) {
        return userRepository.findUserByChatId(userId.toString());
    }

    public void saveUserLocale (Long userId, String locale){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setLocale(locale);
        userRepository.save(user);
    }


    public void saveUserTimezone (Long userId, String timeZone){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTimeZone(timeZone);
        userRepository.save(user);
    }

    public void saveUserTimezoneUtc (Long userId, String timeZoneUtc){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTimezoneUtc(timeZoneUtc);
        userRepository.save(user);
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
