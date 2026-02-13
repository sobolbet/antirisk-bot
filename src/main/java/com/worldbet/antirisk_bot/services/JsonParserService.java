package com.worldbet.antirisk_bot.services;


import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JsonParserService {

    private final Logger log = LoggerFactory.getLogger(JsonParserService.class);

    private final WebClient webClient = WebClient.create();


    public JsonNode getJsonFromResp (String url) {

        JsonNode root = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        log.info("root = " + root);

        return root;

    }

}
