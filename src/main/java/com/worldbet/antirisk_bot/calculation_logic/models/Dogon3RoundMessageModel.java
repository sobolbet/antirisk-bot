package com.worldbet.antirisk_bot.calculation_logic.models;

import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.GameEntity;
import com.worldbet.antirisk_bot.services.ConvertTimeService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Dogon3RoundMessageModel {
    private Double bankStart;
    private Double bankNow;
    private GameEntity game;
    private CoefsEntity coefs;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public DecimalFormat balanceDf = new DecimalFormat("### ### ### ###.##");
    public DecimalFormat betDf = new DecimalFormat("### ### ### ###");
    private final LocaleMessageService localeMessageService;
    private final Locale userLocale;
    private final ConvertTimeService convertTimeService;
    private final ZoneId sourceZoneId;
    private final ZoneId targetZoneId;


    public Dogon3RoundMessageModel(Double bankStart, Double bankNow, GameEntity game, CoefsEntity coefs, LocaleMessageService localeMessageService, Locale userLocale, ConvertTimeService convertTimeService, ZoneId sourceZoneId, ZoneId targetZoneId) {
        this.bankStart = bankStart;
        this.bankNow = bankNow;
        this.game = game;
        this.coefs = coefs;
        this.localeMessageService = localeMessageService;
        this.userLocale = userLocale;
        this.convertTimeService = convertTimeService;
        this.sourceZoneId = sourceZoneId;
        this.targetZoneId = targetZoneId;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

       /*
        sb.append("Стратегия #FAT_DOGON_3_ROUNDS \n");
        sb.append("Ставки на раунды на FAT (1,2,3): \n");
        sb.append("1-й " + betDf.format(bankStart * 0.01) + "\n");
        sb.append("2-й " + betDf.format(bankStart * 0.02) + "\n");
        sb.append("3-й " + betDf.format(bankStart * 0.04) + "\n\n" );
        sb.append("\n\n");

        sb.append("#N" + game.getGameNum() + " " + game.getDateEv() + " " + timeFormat.format(game.getTimeEv()) + "\n");
        sb.append("#" + game.getF1() + "-#" + game.getF2() + "\n");
        sb.append("#" + game.getF1() + "_" + game.getF2() + "\n\n");
        sb.append( "F " + coefs.getFat() + " / ");
        sb.append( "B " + coefs.getBrut() + " / ");
        sb.append( "R " + coefs.getRut() + "\n\n");*/


        LocalDateTime targetDateTime = convertTimeService.convertDateTimeFromSourceToTarget(LocalDateTime.of(game.getDateEv(),game.getTimeEv()),sourceZoneId,targetZoneId);

        Object [] args = new Object[] {betDf.format(bankStart * 0.01),betDf.format(bankStart * 0.02),
                betDf.format(bankStart * 0.04),game.getGameNum(),dateTimeFormat.format(targetDateTime),
                game.getF1(),game.getF2(),game.getF1(),game.getF2(),coefs.getFat(),coefs.getBrut(),coefs.getRut()};
        Object [] args2 = new Object[] {balanceDf.format(bankNow)};

        sb.append(localeMessageService.getMessage("pattern.FatDogon3RoundsMes",userLocale,args));
        sb.append("\n\n");



        if (game.getR1TypeWinRes() != null){sb.append( "1) " + game.getR1TypeWinRes() + "\n");}
        if (game.getR2TypeWinRes() != null){sb.append( "2) " + game.getR2TypeWinRes() + "\n");}
        if (game.getR3TypeWinRes() != null){sb.append( "3) " + game.getR3TypeWinRes() + "\n");}
        if (game.getR4TypeWinRes() != null){sb.append( "4) " + game.getR4TypeWinRes() + "\n");}
        if (game.getR5TypeWinRes() != null){sb.append( "5) " + game.getR5TypeWinRes() + "\n");}
        if (game.getR6TypeWinRes() != null){sb.append( "6) " + game.getR6TypeWinRes() + "\n");}
        if (game.getR7TypeWinRes() != null){sb.append( "7) " + game.getR7TypeWinRes() + "\n");}
        if (game.getR8TypeWinRes() != null){sb.append( "8) " + game.getR8TypeWinRes() + "\n");}
        if (game.getR9TypeWinRes() != null){sb.append( "9) " + game.getR9TypeWinRes() + "\n");}
        if (game.getGameWasEnd()){
            sb.append( "\n");
            sb.append( localeMessageService.getMessage("pattern.GameFinished",userLocale));
            sb.append( "\n");
        }


        sb.append("\n");
        sb.append(localeMessageService.getMessage("pattern.BalanceNow",userLocale,args2));

        return sb.toString();
    }
}
