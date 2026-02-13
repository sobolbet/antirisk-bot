package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.CoefsRepository;
import com.worldbet.antirisk_bot.db.GameEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoefsService {

    private CoefsRepository coefsRepository;

    public CoefsService(CoefsRepository coefsRepository) {
        this.coefsRepository = coefsRepository;
    }


    public Optional<CoefsEntity> findCoefsByGameId (UUID gameId) {
        return coefsRepository.findTopByGameIdOrderByCreateDtDesc(gameId);
    }


    public void saveCoefs (CoefsEntity  coefs) {
        //coefs.setGame(game);
        coefsRepository.save(coefs);

    }
}
