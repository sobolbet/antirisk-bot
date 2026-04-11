package com.worldbet.antirisk_bot.services;


import com.worldbet.antirisk_bot.db.GameEntity;
import com.worldbet.antirisk_bot.db.GamesRepository;
import com.worldbet.antirisk_bot.db.models.GameDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class GamesService {


    private GamesRepository gamesRepository;

    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }



    public boolean existByEventId (String eventId) {
        return gamesRepository.existsByEventId(eventId);
    }

    public GameEntity findByEventIdAndDate (String eventId, LocalDate dateEv) {
        Optional<GameEntity> gameOpt = gamesRepository.findByEventIdAndDateEv(eventId,dateEv);
        return gameOpt.orElse(null);
    }


    public GameEntity getOrCreateGame (String eventId, LocalDate dateEv,String gameNum, LocalTime timeEv, String f1, String f2) {

        return gamesRepository.findByEventIdAndDateEv(eventId,dateEv).orElseGet(() -> gamesRepository.save(new GameEntity(eventId, timeEv,gameNum, dateEv, f1, f2)));
    }

    public boolean equalsState (GameEntity game , GameDto gameDto) {
        if (game == null) return false;
        if (gameDto == null) return false;

        return Objects.equals(game.getEventId(),gameDto.getEventId())
                && Objects.equals(game.getF1(),gameDto.getF1())
                && Objects.equals(game.getF2(),gameDto.getF2())
                && Objects.equals(game.getGameNum(),gameDto.getGameNum())
                && Objects.equals(game.getTotalF1(),gameDto.getTotalF1())
                && Objects.equals(game.getTotalF2(),gameDto.getTotalF2())
                && Objects.equals(game.getR1TypeWinRes(),gameDto.getR1TypeWinRes())
                && Objects.equals(game.getR2TypeWinRes(),gameDto.getR2TypeWinRes())
                && Objects.equals(game.getR3TypeWinRes(),gameDto.getR3TypeWinRes())
                && Objects.equals(game.getR4TypeWinRes(),gameDto.getR4TypeWinRes())
                && Objects.equals(game.getR5TypeWinRes(),gameDto.getR5TypeWinRes())
                && Objects.equals(game.getR6TypeWinRes(),gameDto.getR6TypeWinRes())
                && Objects.equals(game.getR7TypeWinRes(),gameDto.getR7TypeWinRes())
                && Objects.equals(game.getR8TypeWinRes(),gameDto.getR8TypeWinRes())
                && Objects.equals(game.getR9TypeWinRes(),gameDto.getR9TypeWinRes())
                && Objects.equals(game.getR1TimeRes(),gameDto.getR1TimeRes())
                && Objects.equals(game.getR2TimeRes(),gameDto.getR2TimeRes())
                && Objects.equals(game.getR3TimeRes(),gameDto.getR3TimeRes())
                && Objects.equals(game.getR4TimeRes(),gameDto.getR4TimeRes())
                && Objects.equals(game.getR5TimeRes(),gameDto.getR5TimeRes())
                && Objects.equals(game.getR6TimeRes(),gameDto.getR6TimeRes())
                && Objects.equals(game.getR7TimeRes(),gameDto.getR7TimeRes())
                && Objects.equals(game.getR8TimeRes(),gameDto.getR8TimeRes())
                && Objects.equals(game.getR9TimeRes(),gameDto.getR9TimeRes())
                && Objects.equals(game.getR1Time(),gameDto.getR1Time())
                && Objects.equals(game.getR2Time(),gameDto.getR2Time())
                && Objects.equals(game.getR3Time(),gameDto.getR3Time())
                && Objects.equals(game.getR4Time(),gameDto.getR4Time())
                && Objects.equals(game.getR5Time(),gameDto.getR5Time())
                && Objects.equals(game.getR6Time(),gameDto.getR6Time())
                && Objects.equals(game.getR7Time(),gameDto.getR7Time())
                && Objects.equals(game.getR8Time(),gameDto.getR8Time())
                && Objects.equals(game.getR9Time(),gameDto.getR9Time())
                && Objects.equals(game.getRoundNumNow(),gameDto.getRoundNumNow())
                && Objects.equals(game.getGameWasEnd(),gameDto.getGameWasEnd());


    }


    public void apply (GameEntity game, GameDto gameDto) {
                game.setTotalF1(gameDto.getTotalF1());
                game.setTotalF2(gameDto.getTotalF2());
                game.setR1TypeWinRes(gameDto.getR1TypeWinRes());
                game.setR2TypeWinRes(gameDto.getR2TypeWinRes());
                game.setR3TypeWinRes(gameDto.getR3TypeWinRes());
                game.setR4TypeWinRes(gameDto.getR4TypeWinRes());
                game.setR5TypeWinRes(gameDto.getR5TypeWinRes());
                game.setR6TypeWinRes(gameDto.getR6TypeWinRes());
                game.setR7TypeWinRes(gameDto.getR7TypeWinRes());
                game.setR8TypeWinRes(gameDto.getR8TypeWinRes());
                game.setR9TypeWinRes(gameDto.getR9TypeWinRes());
                game.setR1TimeRes(gameDto.getR1TimeRes());
                game.setR2TimeRes(gameDto.getR2TimeRes());
                game.setR3TimeRes(gameDto.getR3TimeRes());
                game.setR4TimeRes(gameDto.getR4TimeRes());
                game.setR5TimeRes(gameDto.getR5TimeRes());
                game.setR6TimeRes(gameDto.getR6TimeRes());
                game.setR7TimeRes(gameDto.getR7TimeRes());
                game.setR8TimeRes(gameDto.getR8TimeRes());
                game.setR9TimeRes(gameDto.getR9TimeRes());
                game.setR1Time(gameDto.getR1Time());
                game.setR2Time(gameDto.getR2Time());
                game.setR3Time(gameDto.getR3Time());
                game.setR4Time(gameDto.getR4Time());
                game.setR5Time(gameDto.getR5Time());
                game.setR6Time(gameDto.getR6Time());
                game.setR7Time(gameDto.getR7Time());
                game.setR8Time(gameDto.getR8Time());
                game.setR9Time(gameDto.getR9Time());
                game.setGameWasEnd(gameDto.getGameWasEnd());
                game.setUpdatedAt(LocalDateTime.now());
                game.setRoundNumNow(gameDto.getRoundNumNow());

                gamesRepository.save(game);
    }

    public void updateGame (GameEntity game) {

        gamesRepository.save(game);

    }

    @Transactional
    public Optional<GameEntity> getGameByEventId (String eventId) {
        return gamesRepository.findByEventId(eventId);
    }

    public ArrayList<GameEntity> getActiveGames (LocalDate date) {
        LocalTime timeNow = LocalTime.now().plusMinutes(10);
        LocalTime timeAgo = timeNow.minusMinutes(40);
        return gamesRepository.findActiveGameInTimeRange(date,timeNow,timeAgo);

    }


}
