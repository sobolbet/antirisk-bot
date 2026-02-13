package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface GamesRepository extends JpaRepository<GameEntity, Long> {


    Optional<GameEntity> findByEventIdAndDateEv (String eventId, LocalDate dateEv);

    boolean existsByEventId (String eventId);

}
