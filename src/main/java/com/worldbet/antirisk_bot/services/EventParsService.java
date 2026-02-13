package com.worldbet.antirisk_bot.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.worldbet.antirisk_bot.db.CoefsEntity;
import com.worldbet.antirisk_bot.db.GameEntity;
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

@Service
public class EventParsService {

    private final static Logger log = LoggerFactory.getLogger(EventParsService.class);
    private final JsonParserService jsonParserService;
    private final GamesService gamesService;
    private final CoefsService coefsService;
    @Value("${antiriskbot.mkx-event-url}")
    private String gameUrl;

    GameEntity gameEntity;
    CoefsEntity coefsEntity;

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
            gameEntity = new GameEntity(eventId,timeEv,dateEv,f1,f2);
            gamesService.updateGame(gameEntity);





            coefsEntity = parseCoefsToEntity(gameEntity,eventId,root);
            coefsService.saveCoefs(coefsEntity);



        }







    }



    private void parseNextToEntities (JsonNode root, String eventId, String eventUrl, CoefsEntity coefsEntity, LocalDate dateEv) {

        root = jsonParserService.getJsonFromResp(eventUrl);

        GameEntity game = gamesService.findByEventIdAndDate(eventId,dateEv);

        GameDto gameDto = new GameDto();

        // дописать парсинг статистики

        if (!gamesService.equalsState(game,gameDto)) {
            gamesService.apply(game,gameDto);
        }

        // добавить условие для кэфов



    }


    private CoefsEntity parseCoefsToEntity (GameEntity game, String eventId, JsonNode root ) {

        JsonNode groupsArr = root.path("eventGroups");

        CoefsEntity coefs = new CoefsEntity();

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




        coefs.setCreateDt(LocalDateTime.now());
        coefs.setGame(game);

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
