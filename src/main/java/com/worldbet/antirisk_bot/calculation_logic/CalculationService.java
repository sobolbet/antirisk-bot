package com.worldbet.antirisk_bot.calculation_logic;

import com.worldbet.antirisk_bot.calculation_logic.models.Dogon3RoundMessageModel;
import com.worldbet.antirisk_bot.calculation_logic.models.FB3RoundMessageModel;
import com.worldbet.antirisk_bot.calculation_logic.models.Flet5RoundMessageModel;
import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.GameEntity;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.UserMessagesEntity;
import com.worldbet.antirisk_bot.services.CoefsService;
import com.worldbet.antirisk_bot.services.UserMessagesService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CalculationService {

    private final CoefsService coefsService;
    private final UserService userService;
    private final UserMessagesService userMessagesService;
    private  final UserFBBetService userFBBetService;
    private final Logger log = LoggerFactory.getLogger(CalculationService.class);




    public CalculationService(CoefsService coefsService, UserService userService, UserMessagesService userMessagesService, UserFBBetService userFBBetService) {
        this.coefsService = coefsService;
        this.userService = userService;
        this.userMessagesService = userMessagesService;
        this.userFBBetService = userFBBetService;
    }


    public String fletCalculation (GameEntity game , Long chatId) {


        CoefsEntity coefs = coefsService.findCoefsByGameId(game.getId());
        UserEntity user = userService.findUserById(chatId);
        Double bankStart = user.getBankStart();
        Double bankNow = user.getBankNow();



        if (game.getRoundNumNow() == 1) {
            if (game.getR1TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR1TypeWinRes().equals("B") || game.getR1TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }
        }
        if (game.getRoundNumNow() == 2) {
            if (game.getR2TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR2TypeWinRes().equals("B") || game.getR2TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }
        }
        if (game.getRoundNumNow() == 3 ) {
            if (game.getR3TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR3TypeWinRes().equals("B") || game.getR3TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }
        }
        if (game.getRoundNumNow() == 4 ) {
            if (game.getR4TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR4TypeWinRes().equals("B") || game.getR4TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }
        }
        if (game.getRoundNumNow() == 5 ) {
            if (game.getR5TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR5TypeWinRes().equals("B") || game.getR5TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }

        }


        userService.updateCurrentUserBank(chatId,bankNow);

        Flet5RoundMessageModel model = new Flet5RoundMessageModel(bankStart,bankNow,game,coefs);

        return model.toString();
    }


    public String dogonCalculation (GameEntity game , Long chatId) {


        CoefsEntity coefs = coefsService.findCoefsByGameId(game.getId());
        UserEntity user = userService.findUserById(chatId);
        Double bankStart = user.getBankStart();
        Double bankNow = user.getBankNow();



        if (game.getRoundNumNow() == 1) {
            if (game.getR1TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.01 * coefs.getFat().doubleValue()) - (bankStart * 0.01);
            }
            if (game.getR1TypeWinRes().equals("B") || game.getR1TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.01);
            }
        }
        if (game.getRoundNumNow() == 2 && (game.getR1TypeWinRes().equals("R") || game.getR1TypeWinRes().equals("B"))) {
            if (game.getR2TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.02 * coefs.getFat().doubleValue()) - (bankStart * 0.02);
            }
            if (game.getR2TypeWinRes().equals("B") || game.getR2TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.02);
            }
        }
        if (game.getRoundNumNow() == 3 && (game.getR1TypeWinRes().equals("R") || game.getR1TypeWinRes().equals("B")) && (game.getR2TypeWinRes().equals("R") || game.getR2TypeWinRes().equals("B"))) {
            if (game.getR3TypeWinRes().equals("F")) {
                bankNow = bankNow + (bankStart * 0.04 * coefs.getFat().doubleValue()) - (bankStart * 0.04);
            }
            if (game.getR3TypeWinRes().equals("B") || game.getR3TypeWinRes().equals("R")) {
                bankNow = bankNow - (bankStart * 0.04);
            }
        }


        userService.updateCurrentUserBank(chatId,bankNow);

        Dogon3RoundMessageModel model = new Dogon3RoundMessageModel(bankStart,bankNow,game,coefs);

        return model.toString();
    }


    public  String fbCalculation (GameEntity game , Long chatId) {

        CoefsEntity coefs = coefsService.findCoefsByGameId(game.getId());
        UserEntity user = userService.findUserById(chatId);
        ArrayList<UserFBBetEntity> listLoseBets = userFBBetService.getUsersFBBets(chatId.toString());
        UserMessagesEntity userMessagesEntity = userMessagesService.getUserMessages(chatId.toString()).stream().
                filter(u -> u.getEventId().equals(game.getEventId())).findFirst().orElse(null);


        Double bankStart = user.getBankStart();
        Double bankNow = user.getBankNow();

        Double desProfit = bankStart * 0.01;

        log.info("Номер раунда =" + game.getRoundNumNow());

        if (game.getR1TypeWinRes()!=null) {
            log.info("Есть результат 1 раунда");
        }
        if (game.getR2TypeWinRes()!=null) {
            log.info("Есть результат 2 раунда");
        }
        if (game.getR3TypeWinRes()!=null) {
            log.info("Есть результат 3 раунда");
        }


        if (listLoseBets.size() > 0) {

            if (listLoseBets.size()>1){ // если размер листа ставок > 1



                if (game.getRoundNumNow()==0) {
                    userFBBetService.save(user,(listLoseBets.get(listLoseBets.size() - 2).getBetAmount() + listLoseBets.get(listLoseBets.size() - 1).getBetAmount()) / (coefs.getFat().doubleValue() - 1));
                }

                if (game.getRoundNumNow() == 1 && game.getR1TypeWinRes()!=null) {
                    if (game.getR1TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        if (listLoseBets.size()>1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            if (listLoseBets.size()>1) {
                                userFBBetService.save(user,((listLoseBets.get(listLoseBets.size()-2).getBetAmount() + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1)));
                            }
                            else if (listLoseBets.size()==1) {
                                userFBBetService.save(user,(desProfit + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                            }
                            else if (listLoseBets.size()==0){
                                userFBBetService.save(user,(desProfit /(coefs.getFat().doubleValue()-1)));
                            }
                        }
                        else if (listLoseBets.size()==1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            userFBBetService.save(user,(desProfit /(coefs.getFat().doubleValue()-1)));}


                    }
                    if (game.getR1TypeWinRes().equals("B") || game.getR1TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                        userFBBetService.save(user,(listLoseBets.get(listLoseBets.size()-2).getBetAmount() + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                    }
                }
                if (game.getRoundNumNow() == 2 && game.getR2TypeWinRes()!=null) {
                    if (game.getR2TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        if (listLoseBets.size()>1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            if (listLoseBets.size()>1) {
                                userFBBetService.save(user,(listLoseBets.get(listLoseBets.size()-2).getBetAmount() + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                            }
                            else if (listLoseBets.size()==1) {
                                userFBBetService.save(user,(desProfit + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                            }
                            else if (listLoseBets.size()==0){
                                userFBBetService.save(user,(desProfit /(coefs.getFat().doubleValue()-1)));
                            }
                        }
                        else if (listLoseBets.size()==1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            userFBBetService.save(user,(desProfit /(coefs.getFat().doubleValue()-1)));}


                    }
                    if (game.getR2TypeWinRes().equals("B") || game.getR2TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                        userFBBetService.save(user,(listLoseBets.get(listLoseBets.size()-2).getBetAmount() + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                    }
                }
                if (game.getRoundNumNow() == 3 && game.getR3TypeWinRes()!=null) {
                    if (game.getR3TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        if (listLoseBets.size()>1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        }

                        else if (listLoseBets.size()==1){
                            userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        }

                    }
                    if (game.getR3TypeWinRes().equals("B") || game.getR3TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                    }

                    if (userMessagesEntity!=null) {
                        userMessagesEntity.setEventFocus(2);
                        userMessagesService.updateUserMessageEntity(userMessagesEntity);
                    }
                }


            }
            else if (listLoseBets.size()==1){ // если размер листа ставок == 1




                if (game.getRoundNumNow()==0) {
                    userFBBetService.save(user,(desProfit + listLoseBets.get(listLoseBets.size() - 1).getBetAmount()) / (coefs.getFat().doubleValue() - 1));
                }

                if (game.getRoundNumNow() == 1 && game.getR1TypeWinRes()!=null) {
                    if (game.getR1TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        if (listLoseBets.size()==0){userFBBetService.save(user,desProfit/(coefs.getFat().doubleValue()-1));}


                    }
                    if (game.getR1TypeWinRes().equals("B") || game.getR1TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                        userFBBetService.save(user,(desProfit + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                    }
                }
                if (game.getRoundNumNow() == 2 && game.getR2TypeWinRes()!=null) {
                    if (game.getR2TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));
                        if (listLoseBets.size()==0){userFBBetService.save(user,desProfit/(coefs.getFat().doubleValue()-1));}


                    }
                    if (game.getR2TypeWinRes().equals("B") || game.getR2TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                        userFBBetService.save(user,(desProfit + listLoseBets.get(listLoseBets.size()-1).getBetAmount())/(coefs.getFat().doubleValue()-1));
                    }
                }
                if (game.getRoundNumNow() == 3 && game.getR3TypeWinRes()!=null) {
                    if (game.getR3TypeWinRes().equals("F")) {
                        bankNow = bankNow + (listLoseBets.get(listLoseBets.size()-1).getBetAmount() * coefs.getFat().doubleValue()) - listLoseBets.get(listLoseBets.size()-1).getBetAmount();

                        userFBBetService.deleteEntity(listLoseBets.get(listLoseBets.size()-1));

                    }
                    if (game.getR3TypeWinRes().equals("B") || game.getR3TypeWinRes().equals("R")) {
                        bankNow = bankNow - listLoseBets.get(listLoseBets.size()-1).getBetAmount();
                    }

                    if (userMessagesEntity!=null) {
                        userMessagesEntity.setEventFocus(2);
                        userMessagesService.updateUserMessageEntity(userMessagesEntity);
                    }
                }

            }

        }

        else if (listLoseBets.size() == 0) { // если размер листа ставок == 0


            if (game.getRoundNumNow()==0) {
                userFBBetService.save(user,desProfit / (coefs.getFat().doubleValue() - 1));
            }

            if (game.getRoundNumNow()==1 || game.getRoundNumNow()==2) {
                userFBBetService.save(user,desProfit/(coefs.getFat().doubleValue()-1));
            }

            if (game.getRoundNumNow() == 1 && game.getR1TypeWinRes()!=null) {

            }
            if (game.getRoundNumNow() == 2 && game.getR2TypeWinRes()!=null) {

            }
            if (game.getRoundNumNow() == 3 && game.getR3TypeWinRes()!=null) {
                if (userMessagesEntity!=null) {
                    userMessagesEntity.setEventFocus(2);
                    userMessagesService.updateUserMessageEntity(userMessagesEntity);
                }
            }


        }



        userService.updateCurrentUserBank(chatId,bankNow);

        listLoseBets = userFBBetService.getUsersFBBets(chatId.toString());

        FB3RoundMessageModel model = new FB3RoundMessageModel(bankStart,bankNow,game,coefs,listLoseBets);

        return  model.toString();


    }


}
