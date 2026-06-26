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

    @Query(value = "select g from GameEntity g " +
            "where g.gameWasEnd = false and g.roundNumNow = 0" +
            "  and (" +
            "    (:startDate = :endDate and g.dateEv = :startDate and g.timeEv between :startTime and :endTime)\n" +
            "    or" +
            "    (:startDate != :endDate and (" +
            "        (g.dateEv = :startDate and g.timeEv >= :startTime) or " +
            "        (g.dateEv = :endDate and g.timeEv <= :endTime) or" +
            "        (g.dateEv > :startDate and g.dateEv < :endDate)" +
            "    ))" +
            "  )" +
            "order by g.dateEv desc, g.timeEv desc")
    ArrayList<GameEntity> findActiveGameInTimeRangeAndRoundNumEqlZero(@Param("startDate") LocalDate startDate,
                                                                      @Param("endTime")LocalTime endTime,
                                                                      @Param("startTime") LocalTime startTime,
                                                                      @Param("endDate") LocalDate endDate);

    @Query(value = "select g from GameEntity g " +
            "where g.gameWasEnd = false " +
            "  and (" +
            "    (:startDate = :endDate and g.dateEv = :startDate and g.timeEv between :startTime and :endTime)\n" +
            "    or" +
            "    (:startDate != :endDate and (" +
            "        (g.dateEv = :startDate and g.timeEv >= :startTime) or " +
            "        (g.dateEv = :endDate and g.timeEv <= :endTime) or" +
            "        (g.dateEv > :startDate and g.dateEv < :endDate)" +
            "    ))" +
            "  )" +
            "order by g.dateEv desc, g.timeEv desc")
    ArrayList<GameEntity> findActiveGameInTimeRange(@Param("startDate") LocalDate startDate,
                                                    @Param("endTime")LocalTime endTime,
                                                    @Param("startTime") LocalTime startTime,
                                                    @Param("endDate") LocalDate endDate);

}


/* на развитие
@Query("SELECT g FROM GameEntity g WHERE g.gameWasEnd = false AND g.roundNumNow = 0 " +
        "AND CAST(CONCAT(g.dateEv, ' ', g.timeEv) AS localdatetime) " +
        "BETWEEN CAST(CONCAT(:startDate, ' ', :startTime) AS localdatetime) " +
        "AND CAST(CONCAT(:endDate, ' ', :endTime) AS localdatetime) " +
        "ORDER BY g.dateEv DESC, g.timeEv DESC")*/