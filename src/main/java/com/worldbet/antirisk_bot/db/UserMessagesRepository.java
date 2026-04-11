package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserMessagesRepository extends JpaRepository<UserMessagesEntity,Long> {

    ArrayList<UserMessagesEntity> findByChatId(String chatId);



}
