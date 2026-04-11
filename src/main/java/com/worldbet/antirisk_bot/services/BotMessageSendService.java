package com.worldbet.antirisk_bot.services;


import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.swing.text.html.HTML;

@Service
public class BotMessageSendService {

    private final Logger log = LoggerFactory.getLogger(BotMessageSendService.class);

    private final AntiRiskBotCore antiRiskBotCore;


    public BotMessageSendService(AntiRiskBotCore antiRiskBotCore) {
        this.antiRiskBotCore = antiRiskBotCore;
    }



    @EventListener
    //@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMessageSendToEvent (MessageToSendEvent event) {

        if (event.getMessageId() == null) {
            log.info ("Received MessageToSendEvent for chat {} : {}",event.getChatId(),event.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(event.getText());
            sendMessage.setChatId(event.getChatId());
            sendMessage.setParseMode("HTML");
            try {
                Message message = antiRiskBotCore.execute(sendMessage);
                log.info("Message sent to chat {} : {}",event.getChatId(),event.getText() );
                Integer messageId = message.getMessageId();
                log.info ("id сообщения = " + messageId);
                event.setMessageIdForEdit(messageId);
            }catch (TelegramApiException e) {
                log.info("Failed to sent message to chat {} : {}",event.getChatId(),event.getText() );
                throw new RuntimeException (e);
            }
        } else {
            log.info ("Received MessageToSendEvent with id {} for chat {} : {}",event.getMessageId(),event.getChatId(),event.getText());
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(event.getMessageId());
            editMessage.setText(event.getText());
            editMessage.setChatId(event.getChatId());
            editMessage.setParseMode("HTML");
            try {
                log.info("Message with id {} sent to chat {} : {}",event.getMessageId(),event.getChatId(),event.getText() );
                antiRiskBotCore.execute(editMessage);
            }catch (TelegramApiException e) {
                log.info("Failed to sent message wth id {} to chat {} : {}",event.getMessageId(),event.getChatId(),event.getText() );
                throw new RuntimeException (e);
            }
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
