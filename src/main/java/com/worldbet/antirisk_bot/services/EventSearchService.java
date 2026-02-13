package com.worldbet.antirisk_bot.services;


import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Service
public class EventSearchService {

    private final static Logger log = LoggerFactory.getLogger(EventSearchService.class);

    private final ExecutorService executorService;
    private final EventParsService eventParsService;
    private final JsonParserService jsonParserService;


    private final Set<String> inProgress = ConcurrentHashMap.newKeySet();
    @Value("${antiriskbot.bk-mkx-url}")
    private String baseUrl;

    public EventSearchService(ExecutorService executorService, EventParsService eventParsService, JsonParserService jsonParserService) {
        this.executorService = executorService;
        this.eventParsService = eventParsService;
        this.jsonParserService = jsonParserService;
    }


    @Scheduled(fixedDelay = 30_000)
    public void searchEvents() {

        JsonNode arrayEvents = getArrayEvents(jsonParserService.getJsonFromResp(baseUrl));

        for (JsonNode event : arrayEvents ) {

            String localRoundNum = event.path("SC").path("CPS").asText();

            int timeDirect = 0;
            timeDirect = event.path("SC").path("TD").asInt();



            if (localRoundNum.isEmpty() && timeDirect==-1) {

                String eventId = event.path("I").asText();

                log.info("EventId " + eventId);

            if (inProgress.add(eventId)) {

                executorService.submit(() -> {

                    try {
                        eventParsService.parseEvent(eventId);
                    } finally {
                        inProgress.remove(eventId);
                    }

                });
            }

            }

        }

    }



    private JsonNode getArrayEvents (JsonNode root) {
        JsonNode arrayEvents = root.path("Value").path(0).path("G");

        log.info("Array = " + arrayEvents);

        if (arrayEvents.isArray()) {
            return arrayEvents;
        }
        return null;


    }



}
