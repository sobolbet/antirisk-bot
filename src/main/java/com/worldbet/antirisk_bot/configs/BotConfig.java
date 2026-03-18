package com.worldbet.antirisk_bot.configs;

import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.services.MessageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Configuration
@ConfigurationProperties(prefix = "telegram")
public class BotConfig {


    @Value("${telegram.userName}")
    private String botUserName;
    @Value("${telegram.botToken}")
    private String botToken;
    @Value("${telegram.bot.proxy.host}")
    private String proxyHost;
    @Value("${telegram.bot.proxy.port}")
    private int proxyPort;
    @Value("${telegram.bot.proxy.user}")
    private String proxyUser;
    @Value("${telegram.bot.proxy.pass}")
    private String proxyPass;
    @Value("${telegram.bot.proxy.type}")
    private String proxyType;


    @PostConstruct

    public void init () {
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes" , "");

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxyUser,proxyPass.toCharArray());
            }
        });
    }

   /* @Autowired
    MessageService messageService;*/



    @Bean
    public DefaultBotOptions defaultBotOptions () {
        DefaultBotOptions options = new DefaultBotOptions();
        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);
        options.setProxyType(DefaultBotOptions.ProxyType.valueOf(proxyType));

        return options;
    }


    @Bean
    public TelegramBotsApi telegramBotsApi  (AntiRiskBotCore antiRiskBotCore) throws Exception {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(antiRiskBotCore);

        return telegramBotsApi;

    }



    @Bean
    public AntiRiskBotCore antiRiskBotCore (MessageService messageService, DefaultBotOptions options) {

        AntiRiskBotCore antiRiskBotCore = new AntiRiskBotCore (messageService, options);
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
