package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoefsRepository extends JpaRepository<CoefsEntity,Long> {



    Optional<CoefsEntity> findTopByGameIdOrderByCreateDtDesc(UUID gameId);


    List<CoefsEntity> findByGameIdOrderByCreateDtAsc (UUID gameId);

}
