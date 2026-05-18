package com.worldbet.antirisk_bot.calculation_logic.models;

import com.worldbet.antirisk_bot.calculation_logic.UserFBBetEntity;
import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.GameEntity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FB3RoundMessageModel {

    private Double bankStart;
    private Double bankNow;
    private GameEntity game;
    private CoefsEntity coefs;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    public DecimalFormat balanceDf = new DecimalFormat("### ### ### ###.##");
    public DecimalFormat betDf = new DecimalFormat("### ### ### ###");
    ArrayList<UserFBBetEntity> listLoseBets;


    public FB3RoundMessageModel(Double bankStart, Double bankNow, GameEntity game, CoefsEntity coefs, ArrayList<UserFBBetEntity> listLoseBets) {
        this.bankStart = bankStart;
        this.bankNow = bankNow;
        this.game = game;
        this.coefs = coefs;
        this.listLoseBets = listLoseBets;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Стратегия #FAT_FB_3_ROUNDS \n");
        sb.append("\n\n");

        sb.append("#N" + game.getGameNum() + " " + game.getDateEv() + " " + timeFormat.format(game.getTimeEv()) + "\n");
        sb.append("#" + game.getF1() + "-#" + game.getF2() + "\n");
        sb.append("#" + game.getF1() + "_" + game.getF2() + "\n\n");
        sb.append( "F " + coefs.getFat() + " / ");
        sb.append( "B " + coefs.getBrut() + " / ");
        sb.append( "R " + coefs.getRut() + "\n\n");


        if (game.getR1TypeWinRes() != null){sb.append( "1) " + game.getR1TypeWinRes() + "\n");}
        if (game.getR2TypeWinRes() != null){sb.append( "2) " + game.getR2TypeWinRes() + "\n");}
        if (game.getR3TypeWinRes() != null){sb.append( "3) " + game.getR3TypeWinRes() + "\n");}
        if (game.getR4TypeWinRes() != null){sb.append( "4) " + game.getR4TypeWinRes() + "\n");}
        if (game.getR5TypeWinRes() != null){sb.append( "5) " + game.getR5TypeWinRes() + "\n");}
        if (game.getR6TypeWinRes() != null){sb.append( "6) " + game.getR6TypeWinRes() + "\n");}
        if (game.getR7TypeWinRes() != null){sb.append( "7) " + game.getR7TypeWinRes() + "\n");}
        if (game.getR8TypeWinRes() != null){sb.append( "8) " + game.getR8TypeWinRes() + "\n");}
        if (game.getR9TypeWinRes() != null){sb.append( "9) " + game.getR9TypeWinRes() + "\n");}
        if (game.getGameWasEnd()){sb.append( "\nИгра завершена\n");}


        sb.append("\nСтавка на FAT до 3 раунда \n");
        if (listLoseBets.size()>0 && game.getRoundNumNow()<4) {
            sb.append("Ставка сейчас: ");
            sb.append(betDf.format(listLoseBets.getLast().getBetAmount()));
            sb.append("\n");
        }
        sb.append("Нужно отыграть:\n");
        if (listLoseBets.size()>0) {
            listLoseBets.forEach(u -> sb.append(betDf.format(u.getBetAmount()) + "\n"));
        }
        sb.append("\nБаланс сейчас: ");
        sb.append(balanceDf.format(bankNow));

        return sb.toString();
    }

}
