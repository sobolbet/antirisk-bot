package com.worldbet.antirisk_bot.services;


import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class BotMessageSendService {

    private final Logger log = LoggerFactory.getLogger(BotMessageSendService.class);

    private final AntiRiskBotCore antiRiskBotCore;


    public BotMessageSendService(AntiRiskBotCore antiRiskBotCore) {
        this.antiRiskBotCore = antiRiskBotCore;
    }



    @EventListener
    public void handleMessageSendToEvent (MessageToSendEvent event) {

        log.info ("Received MessegToSendEvent for chat {} : {}",event.getChatId(),event.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(event.getText());
        sendMessage.setChatId(event.getChatId());
        try {
            antiRiskBotCore.execute(sendMessage);
            log.info("Message sent to chat {} : {}",event.getChatId(),event.getText() );
        }catch (TelegramApiException e) {
            log.info("Failed to sent message to chat {} : {}",event.getChatId(),event.getText() );
            throw new RuntimeException (e);
        }


    }




    public void sendMessage (Long userId, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(userId);
            sendMessage.setText(text);
            antiRiskBotCore.execute(sendMessage);
        } catch (TelegramApiException ex) {
            throw new RuntimeException(ex);
        }

    }


}
