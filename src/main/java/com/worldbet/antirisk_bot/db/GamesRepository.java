package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public interface GamesRepository extends JpaRepository<GameEntity, Long> {


    Optional<GameEntity> findByEventIdAndDateEv (String eventId, LocalDate dateEv);

    boolean existsByEventId (String eventId);

    Optional<GameEntity> findByEventId (String eventId);

    @Query(value = "select g from GameEntity g where (g.f1 = :f1 and g.f2 = :f2 ) and g.gameWasEnd = false and g.roundNumNow is null")
    Optional<GameEntity> findByF1AndF2 (String f1, String f2);

    @Query(value = "select g from GameEntity g where g.dateEv = :date and (g.timeEv between :timeAgo and :timeNow) and g.gameWasEnd = false and g.roundNumNow = 0")
    ArrayList<GameEntity> findActiveGameInTimeRange(@Param("date") LocalDate date, @Param("timeNow")LocalTime timeNow, @Param("timeAgo") LocalTime timeAgo);

}
