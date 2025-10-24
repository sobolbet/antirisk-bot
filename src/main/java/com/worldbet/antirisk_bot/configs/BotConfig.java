package com.worldbet.antirisk_bot.configs;

import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.services.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ConfigurationProperties(prefix = "telegram")
public class BotConfig {


    @Value("${telegram.userName}")
    private String botUserName;
    @Value("${telegram.botToken}")
    private String botToken;

   /* @Autowired
    MessageService messageService;*/



    @Bean
    public TelegramBotsApi telegramBotsApi  (AntiRiskBotCore antiRiskBotCore) throws Exception {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(antiRiskBotCore);

        return telegramBotsApi;

    }



    @Bean
    public AntiRiskBotCore antiRiskBotCore (MessageService messageService) {

        AntiRiskBotCore antiRiskBotCore = new AntiRiskBotCore (messageService);
        antiRiskBotCore.setBotUserName(botUserName);
        antiRiskBotCore.setBotToken(botToken);

        return antiRiskBotCore;
    }


    @Bean
    public MessageSource messageSource () {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }



}
