package com.worldbet.antirisk_bot.calculation_logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


public interface UserFBBetRepository extends JpaRepository<UserFBBetEntity,Long> {

    public ArrayList<UserFBBetEntity> findByChatIdOrderByCreatedAtAsc (String chatId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserFBBetEntity u WHERE u.chatId = :chatId")
    public void deleteByChatId (String chatId);


}
