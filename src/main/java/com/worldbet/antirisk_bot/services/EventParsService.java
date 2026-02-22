package com.worldbet.antirisk_bot.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.GameEntity;
import com.worldbet.antirisk_bot.db.models.CoefDto;
import com.worldbet.antirisk_bot.db.models.GameDto;
import com.worldbet.antirisk_bot.db.models.TotalTimeCoef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;

@Service
public class EventParsService {

    private final static Logger log = LoggerFactory.getLogger(EventParsService.class);
    private final JsonParserService jsonParserService;
    private final GamesService gamesService;
    private final CoefsService coefsService;
    @Value("${antiriskbot.mkx-event-url}")
    private String gameUrl;

    //GameEntity gameEntity;
    //CoefsEntity coefsEntity;

    public EventParsService(JsonParserService jsonParserService, GamesService gamesService, CoefsService coefsService) {
        this.jsonParserService = jsonParserService;
        this.gamesService = gamesService;
        this.coefsService = coefsService;
    }


    public void parseEvent(String id) {

        LocalDate dateEv = LocalDate.now();

        String eventUrl = String.format(gameUrl,id);

        JsonNode root = jsonParserService.getJsonFromResp(eventUrl);

        String currentPeriodName = root.path("scores").path("currentPeriodName").asText();

        Integer timeDir = root.path("scores").path ("timer").path("timeDirection").asInt();

        Long timeSec = root.path("scores").path ("timer").path("timeSec").asLong();

        Long fullTimeEvent = (100L * 9L) + (40L * 8L);

        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        Long timeOfStartEpoch = LocalDateTime.now().atZone(zoneId).toInstant().getEpochSecond();

        long timeEndOfEvent = timeOfStartEpoch + timeSec + fullTimeEvent;


        parseFirstToEntities(root,id,timeSec,dateEv);
        // Здесь будет код первого парсинга и добавления события и кэфов в БД

        threadSleep(timeSec * 1000);


        for (long timeNow = LocalDateTime.now().atZone(zoneId).toInstant().getEpochSecond();
             !currentPeriodName.equals("Игра завершена") && timeNow < timeEndOfEvent;
             timeNow = LocalDateTime.now().atZone(zoneId).toInstant().getEpochSecond()) {


            log.info("TimeNowInSec = " + timeNow + "\nTimeEndOfEvent = " +  timeEndOfEvent);

            log.info("Parse event Id = "  + id);



            parseNextToEntities(id,eventUrl,dateEv);

            // Здесь будет код остальных парсингов и обновления события в БД и добавление новых кэфов при изменении

            threadSleep(5000L);

        }


            log.info("Parse event Id = "  + id);



    }


    private void parseFirstToEntities (JsonNode root, String eventId, Long timeSec, LocalDate dateEv) {

        log.info("Timesec = " + timeSec);

        LocalTime timeNow = LocalTime.now();

        LocalTime timeEv = timeNow.plusSeconds(timeSec+3);
        log.info("TimeEv = " + timeEv.toString());


        String f1 = root.path("opponent1").path("fullName").asText();
        String f2 = root.path("opponent2").path("fullName").asText();

        if (!gamesService.existByEventId(eventId)) {
            GameEntity gameEntity = new GameEntity(eventId,timeEv,dateEv,f1,f2);
            gamesService.updateGame(gameEntity);





            CoefsEntity coefsEntity = new CoefsEntity();
            coefsService.apply(coefsEntity, parseCoefsToDto(eventId,root),gameEntity);




        }







    }



    private void parseNextToEntities ( String eventId, String eventUrl, LocalDate dateEv) {

        JsonNode root = jsonParserService.getJsonFromResp(eventUrl);

        GameEntity game = gamesService.findByEventIdAndDate(eventId,dateEv);

        CoefsEntity coefsSource = coefsService.findCoefsByGameId(game.getId());

        CoefDto coefDto = parseCoefsToDto(eventId,root);

        log.info("Перед сравнением");

        log.info(coefsSource.toString());
        log.info(coefDto.toString());

        if (!coefsService.equalsState(coefsSource,coefDto)) {
            CoefsEntity coefsNew = new CoefsEntity();
            coefsService.apply(coefsNew,coefDto,game);
        }

        //todo: добавить условие для сравнения и прасинга кэфов

        GameDto gameDto = new GameDto();

        gameDto.setEventId(root.path("id").asText());
        gameDto.setGameNum(root.path("dopInfo").asText());
        gameDto.setF1(root.path("opponent1").path("fullName").asText());
        gameDto.setF2(root.path("opponent2").path("fullName").asText());
        gameDto.setTotalF1(root.path("scores").path("fullScoreDetail").path("scoreOpp1").asInt());
        gameDto.setTotalF2(root.path("scores").path("fullScoreDetail").path("scoreOpp2").asInt());
        gameDto.setGameWasEnd(!root.path("scores").path("timer").path("timeRun").asBoolean());

        int statArrSize = root.path("scores").path("statisticJson").path("RoundTable").size();

        List<Consumer<Integer>> settersTime = new ArrayList<>();
        List<Consumer<String>> settersTypeWinRes = new ArrayList<>();

        settersTime.add(gameDto::setR1Time);
        settersTime.add(gameDto::setR2Time);
        settersTime.add(gameDto::setR3Time);
        settersTime.add(gameDto::setR4Time);
        settersTime.add(gameDto::setR5Time);
        settersTime.add(gameDto::setR6Time);
        settersTime.add(gameDto::setR7Time);
        settersTime.add(gameDto::setR8Time);
        settersTime.add(gameDto::setR9Time);

        settersTypeWinRes.add(gameDto::setR1TypeWinRes);
        settersTypeWinRes.add(gameDto::setR2TypeWinRes);
        settersTypeWinRes.add(gameDto::setR3TypeWinRes);
        settersTypeWinRes.add(gameDto::setR4TypeWinRes);
        settersTypeWinRes.add(gameDto::setR5TypeWinRes);
        settersTypeWinRes.add(gameDto::setR6TypeWinRes);
        settersTypeWinRes.add(gameDto::setR7TypeWinRes);
        settersTypeWinRes.add(gameDto::setR8TypeWinRes);
        settersTypeWinRes.add(gameDto::setR9TypeWinRes);


        for (int i = 0; i<statArrSize; i++) {
            //todo: добавить логику , которая на сравнении с временем выбирает ТММ,ТББ,ТМ, ТБ
            settersTime.get(i).accept(root.path("scores").path("statisticJson").path("RoundTable").path(i).path("T").asInt());
            settersTypeWinRes.get(i).accept(String.valueOf(root.path("scores").path("statisticJson").path("RoundTable").path(i).path("DI").asText().charAt(0)));
        }


        /*switch (statArrSize) {
            case 1:
                //todo: добавить логику , которая на сравнении с временем выбирает ТММ,ТББ,ТМ, ТБ
                gameDto.setR1Time(root.path("scores").path("statisticJson").path("RoundTable").path(0).path("T").asInt());
                gameDto.setR1TypeWinRes(String.valueOf(root.path("scores").path("statisticJson").path("RoundTable").path(0).path("DI").asText().charAt(0)));
                break;
            case 2:
        }*/

        //:

        // дописать парсинг статистики

        if (!gamesService.equalsState(game,gameDto)) {
            gamesService.apply(game,gameDto);
        }





    }


    private CoefDto parseCoefsToDto ( String eventId, JsonNode root ) {

        JsonNode groupsArr = root.path("eventGroups");

        CoefDto coefs = new CoefDto();

        for (JsonNode group : groupsArr) {

            switch (group.path("groupId").asInt()) {
                case 563 :
                    for (JsonNode eventArr : group.path("events")) {
                        switch (eventArr.path(0).path("type").asInt()) {
                            case 2140:
                                coefs.setP1r(BigDecimal.valueOf(eventArr.path(0).path("cf").asDouble()));
                                break;
                            case 2141:
                                coefs.setP2r(BigDecimal.valueOf(eventArr.path(0).path("cf").asDouble()));
                                break;
                        }
                    }
                    break;
                case 576 :
                    ArrayList<TotalTimeCoef> list2170 = new ArrayList<>();
                    ArrayList<TotalTimeCoef> list2171 = new ArrayList<>();
                    for (JsonNode eventArr : group.path("events")) {
                        for (JsonNode event : eventArr) {
                            switch (event.path("type").asInt()) {
                                case 2170:
                                    list2170.add(new TotalTimeCoef(BigDecimal.valueOf(Double.parseDouble(event.path("eventParams").path("params").path(1).asText())),
                                           BigDecimal.valueOf(event.path("cf").asDouble()) ));
                                    break;
                                case 2171:
                                    list2171.add(new TotalTimeCoef(BigDecimal.valueOf(Double.parseDouble(event.path("eventParams").path("params").path(1).asText())),
                                            BigDecimal.valueOf(event.path("cf").asDouble()) ));
                                    break;
                            }
                        }
                    }
                    coefs.setMinTT(list2170.get(0).getTime());
                    coefs.setMidTT(list2170.get(1).getTime());
                    coefs.setMaxTT(list2170.get(2).getTime());
                    coefs.setTbMinTime(list2170.get(0).getCoef());
                    coefs.setTbMidTime(list2170.get(1).getCoef());
                    coefs.setTbMaxTime(list2170.get(2).getCoef());
                    coefs.setTmMinTime(list2171.get(0).getCoef());
                    coefs.setTmMidTime(list2171.get(1).getCoef());
                    coefs.setTmMaxTime(list2171.get(2).getCoef());

                    break;
                case 1 :
                    for (JsonNode eventArr : group.path("events")) {
                        switch (eventArr.path(0).path("type").asInt()) {
                            case 1:
                                coefs.setP1m(BigDecimal.valueOf(eventArr.path(0).path("cf").asDouble()));
                                break;
                            case 3:
                                coefs.setP2m(BigDecimal.valueOf(eventArr.path(0).path("cf").asDouble()));
                                break;
                        }
                    }
                    break;
                /*case 4 :
                    break;*/
                case 572 :
                    for (JsonNode eventArr : group.path("events")) {
                        for (JsonNode event : eventArr) {
                            switch (event.path("type").asInt()) {
                                case 4057:
                                    coefs.setFat(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                                case 4058:
                                    coefs.setBrut(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                                case 4059:
                                    coefs.setRut(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                            }
                        }
                    }
                    break;
                /*case 5 :
                    break;*/
                /*case 6 :
                    break;*/
                case 1092 :
                    for (JsonNode eventArr : group.path("events")) {
                        for (JsonNode event : eventArr) {
                            switch (event.path("type").asInt()) {
                                case 4055:
                                    coefs.setFw(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                            }
                        }
                    }
                    break;
                case 1442 :
                    for (JsonNode eventArr : group.path("events")) {
                        for (JsonNode event : eventArr) {
                            switch (event.path("type").asInt()) {
                                case 4929:
                                    coefs.setFatYes(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                                case 4930:
                                    coefs.setFatNo(BigDecimal.valueOf(event.path("cf").asDouble()));
                                    break;
                            }
                        }
                    }
                    break;


            }


        }




        //coefs.setCreateDt(LocalDateTime.now());
        //coefs.setGame(game);

        return  coefs;

    }


    private void threadSleep (long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
