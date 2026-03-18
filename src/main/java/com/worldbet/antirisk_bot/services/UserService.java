package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.StrategyEntity;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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


    public void saveUserTrial (Long userId, boolean trial){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTrial(trial);
        userRepository.save(user);
    }

    public void saveUserDateTrial (Long userId, LocalDateTime startDt){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTrialStartDt(startDt);
        LocalDateTime endDt = startDt.plusMonths(2);
        user.setTrialEndDt(endDt);
        userRepository.save(user);
    }


    public void saveUserTimezone (Long userId, String timeZone){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTimeZone(timeZone);
        userRepository.save(user);
    }

    public void saveUserLocalTime (Long userId, LocalTime sourceLocalTime, LocalTime mscLocalTime){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setLocalTime(sourceLocalTime);
        user.setMoscowTime(mscLocalTime);
        userRepository.save(user);
    }

    public void saveUserTimeJob (Long userId, String timeJob){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTimeJob(Long.parseLong(timeJob));
        userRepository.save(user);
    }

    public void saveUserBank (Long userId, Double bankStart){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setBankStart(bankStart);
        userRepository.save(user);
    }


    public void saveUserStrategy (Long userId, StrategyEntity strategy){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setStrategy(strategy);
        userRepository.save(user);
    }

    public void updateCurrentUserBank (Long userId, Double bankStart){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setBankNow(bankStart);
        userRepository.save(user);
    }

    public void saveUserTimezoneUtc (Long userId, String timeZoneUtc){
        UserEntity user = userRepository.findUserByChatId(userId.toString());
        user.setTimezoneUtc(timeZoneUtc);
        userRepository.save(user);
    }

    public ArrayList<UserEntity> getUsersByStateEqlTimerAtWork (BotState state) {
        ArrayList<UserEntity> listUsers = userRepository.getUserByBotStateEqlTimerAtWork(state);

        return listUsers;
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
