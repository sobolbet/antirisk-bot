package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.StrategyEntity;
import com.worldbet.antirisk_bot.db.StrategyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StrategyService {

    private final static Logger log = LoggerFactory.getLogger(StrategyService.class);

    private final StrategyRepository strategyRepository;


    public StrategyService(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    public void showStrategy (Integer id) {
        StrategyEntity strategy = strategyRepository.findStrategyById(id);

        log.info("Параметры стратегии : " + strategy.getStrategyParams().toString());



    }


    public Optional<StrategyEntity> findStrategyByName (String name) {
        return strategyRepository.findStrategyByName(name);
    }

}
