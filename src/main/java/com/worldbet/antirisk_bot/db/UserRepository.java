package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query(value = "select state from users.users as u where u.chat_id = :chatId ",nativeQuery = true)
    BotState getStatusByChatId (String chatId);


    /*@Transactional
    @Modifying
    @Query ("UPDATE UserEntity u SET u.state = :state WHERE u.chatId = :chatId")
    int updateStatus (@Param("chatId") String chatId , @Param("state") BotState state);*/

    @Query (value = "select * from users.users as u where u.chat_id = :chatId", nativeQuery = true)
    UserEntity findUserByChatId(String chatId);

    @Query (value = "select u from UserEntity u where u.state = :botState")
    ArrayList<UserEntity> getUserByBotStateEqlTimerAtWork (BotState botState);

   /*@Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.locale = :locale WHERE u.chatId = :chatId")
    int updateLocale (@Param("chatId") String chatId , @Param("locale") String locale);*/

   /* @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.locale = :timezone WHERE u.chatId = :chatId")
    int updateTimezone (@Param("chatId") String chatId , @Param("timezone") String timezone);*/

}
