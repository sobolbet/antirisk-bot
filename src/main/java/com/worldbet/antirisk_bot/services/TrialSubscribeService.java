package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.TypeToUse;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrialSubscribeService {

    private final UserService userService;
    private final UserRepository userRepository;

    public TrialSubscribeService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    public void activateTrial (UserEntity user, LocalDateTime timeNow) {
        user.setTypeToUse(TypeToUse.TRIAL);
        user.setTrialStartDt(timeNow);
        user.setTrialEndDt(timeNow.plusMonths(2));
        userRepository.save(user);
    }

    public Boolean getUseState (UserEntity user) {

        if (user.getTypeToUse() != null) {


       if (user.getTypeToUse().equals(TypeToUse.TRIAL)) {

           return !LocalDateTime.now().isAfter(user.getTrialEndDt());

       } else if (user.getTypeToUse().equals(TypeToUse.SUBSCRIBE)) {

           return !LocalDateTime.now().isAfter(user.getSubscribeEndDt());

       }

       }

        return false;

    }
}
