package com.worldbet.antirisk_bot.calculation_logic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface UserFBBetRepository extends JpaRepository<UserFBBetEntity,Long> {

    public ArrayList<UserFBBetEntity> findByChatIdOrderByCreatedAtAsc (String chatId);


}
