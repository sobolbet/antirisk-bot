package com.worldbet.antirisk_bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StrategyRepository extends JpaRepository<StrategyEntity, Long> {

    @Query (value = "select s from StrategyEntity s where s.id = :id")
    StrategyEntity findStrategyById (Integer id);

    @Query (value = "select s from StrategyEntity s where s.name = :name")
    Optional<StrategyEntity >findStrategyByName(String name);


}
