package com.worldbet.antirisk_bot.controllers;

import com.worldbet.antirisk_bot.services.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class AntiRiskBotCore extends TelegramLongPollingBot {

    private String botUserName;
    private String botToken;


    MessageService messageService;

    public AntiRiskBotCore (MessageService messageService) {

        this.messageService = messageService;
    }

    /*@Autowired
    private MessageSource messageSource;
    @Value("${localeTag}") String localeTag;*/





    @Override
    public void onUpdateReceived(Update update) {

        try {

            if (update.hasMessage()) {
                SendMessage reply = messageService.handleUpdate(update);
                if (reply!= null) {
                    execute(reply);
                }

            }


        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }









            /*SendMessage message = new SendMessage();


            message.setChatId(update.getMessage().getChatId());
            message.setText(messageSource.getMessage("reply.hello",null, Locale.forLanguageTag(localeTag)));

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            */
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken(){
        return botToken;
    }


    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
