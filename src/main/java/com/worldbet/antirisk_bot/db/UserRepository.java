package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query(value = "select state from users.users as u where u.chat_id = :chatId ",nativeQuery = true)
    BotState getStatusByChatId (String chatId);


    @Transactional
    @Modifying
    @Query ("UPDATE UserEntity u SET u.state = :state WHERE u.chatId = :chatId")
    int updateStatus (@Param("chatId") String chatId , @Param("state") BotState state);

    @Query (value = "select * from users.users as u where u.chat_id = :chatId", nativeQuery = true)
    UserEntity findUserByChatId(String chatId);

}
