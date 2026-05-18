package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.CoefsRepository;
import com.worldbet.antirisk_bot.db.GameEntity;
import com.worldbet.antirisk_bot.db.models.CoefDto;
import com.worldbet.antirisk_bot.db.models.GameDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoefsService {

    private CoefsRepository coefsRepository;

    private final static Logger log = LoggerFactory.getLogger(CoefsService.class);

    public CoefsService(CoefsRepository coefsRepository) {
        this.coefsRepository = coefsRepository;
    }


    public CoefsEntity findCoefsByGameId (UUID gameId) {
        Optional<CoefsEntity> coefOpt= coefsRepository.findTopByGameIdOrderByCreateDtDesc(gameId);
        return coefOpt.orElse(null);
    }


    public boolean equalsState (CoefsEntity coefs, CoefDto coefDto) {

        if (coefs == null) return false;
        if (coefDto == null) return false;
        if (coefDto.getFat() == null && coefDto.getBrut()== null && coefDto.getRut()==null) return true;

        return Objects.equals(coefs.getFat(),coefDto.getFat())
                && Objects.equals(coefs.getBrut(),coefDto.getBrut())
                && Objects.equals(coefs.getRut(),coefDto.getRut())
                && Objects.equals(coefs.getFw(),coefDto.getFw())
                && Objects.equals(coefs.getFatNo(),coefDto.getFatNo())
                && Objects.equals(coefs.getFatYes(),coefDto.getFatYes())
                && Objects.equals(coefs.getMaxTT(),coefDto.getMaxTT())
                && Objects.equals(coefs.getMidTT(),coefDto.getMidTT())
                && Objects.equals(coefs.getMinTT(),coefDto.getMinTT())
                && Objects.equals(coefs.getP1m(),coefDto.getP1m())
                && Objects.equals(coefs.getP2m(),coefDto.getP2m())
                && Objects.equals(coefs.getP1r(),coefDto.getP1r())
                && Objects.equals(coefs.getP2r(),coefDto.getP2r())
                && Objects.equals(coefs.getTbMaxTime(),coefDto.getTbMaxTime())
                && Objects.equals(coefs.getTbMidTime(),coefDto.getTbMidTime())
                && Objects.equals(coefs.getTbMinTime(),coefDto.getTbMinTime())
                && Objects.equals(coefs.getTmMaxTime(),coefDto.getTmMaxTime())
                && Objects.equals(coefs.getTmMidTime(),coefDto.getTmMidTime())
                && Objects.equals(coefs.getTmMinTime(),coefDto.getTmMinTime())
                && Objects.equals(coefs.getRoundNum(),coefDto.getRoundNum());





    }


    public void apply (CoefsEntity coefs, CoefDto coefDto, GameEntity game) {
        coefs.setFat(coefDto.getFat());
        coefs.setBrut(coefDto.getBrut());
        coefs.setRut(coefDto.getRut());
        coefs.setFw(coefDto.getFw());
        coefs.setFatNo(coefDto.getFatNo());
        coefs.setFatYes(coefDto.getFatYes());
        coefs.setMaxTT(coefDto.getMaxTT());
        coefs.setMidTT(coefDto.getMidTT());
        coefs.setMinTT(coefDto.getMinTT());
        coefs.setP1m(coefDto.getP1m());
        coefs.setP2m(coefDto.getP2m());
        coefs.setP1r(coefDto.getP1r());
        coefs.setP2r(coefDto.getP2r());
        coefs.setTbMinTime(coefDto.getTbMinTime());
        coefs.setTbMidTime(coefDto.getTbMidTime());
        coefs.setTbMaxTime(coefDto.getTbMaxTime());
        coefs.setTmMinTime(coefDto.getTmMinTime());
        coefs.setTmMidTime(coefDto.getTmMidTime());
        coefs.setTmMaxTime(coefDto.getTmMaxTime());
        coefs.setRoundNum(coefDto.getRoundNum());

        coefs.setCreateDt(LocalDateTime.now());
        coefs.setGame(game);
        coefsRepository.save(coefs);
    }


    public void saveCoefs (CoefsEntity  coefs) {
        //coefs.setGame(game);
        coefsRepository.save(coefs);

    }
}
