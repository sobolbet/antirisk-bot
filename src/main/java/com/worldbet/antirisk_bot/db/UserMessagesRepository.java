package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface UserMessagesRepository extends JpaRepository<UserMessagesEntity,Long> {

    ArrayList<UserMessagesEntity> findByChatId(String chatId);

    @Transactional
    void deleteAllByUserId (UUID userId);



}
