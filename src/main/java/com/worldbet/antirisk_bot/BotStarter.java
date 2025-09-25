package com.worldbet.antirisk_bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class BotStarter {

    TelegramBotsApi telegramBotsApi;

    private AntiRiskBotCore antiRiskBotCore;

    public BotStarter (AntiRiskBotCore antiRiskBotCore) {
        this.antiRiskBotCore = antiRiskBotCore;
    }



    {
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(antiRiskBotCore);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
