package com.worldbet.antirisk_bot.timers;

import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SenderEventsToTGService {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Logger log = LoggerFactory.getLogger(SenderEventsToTGService.class);


    private ConcurrentHashMap<Long,Integer> messages = new ConcurrentHashMap<>();

    public SenderEventsToTGService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public void sendMessageToUser (Long userId) {

        for (int i = 0; true; i++ ) {

            log.info("Зашёл в for");

            MessageToSendEvent message = null;

            if (!messages.isEmpty()) {
                log.info("Зашёл если !messages.isEmpty()");
                message  = new MessageToSendEvent(this,userId,messages.get(userId),"Редактируемое сообщение" + i);
                applicationEventPublisher.publishEvent(message);
            } else {
                log.info("Зашёл если messages.isEmpty()");
                message  = new MessageToSendEvent(this,userId,"Редактируемое сообщение" + i);
                applicationEventPublisher.publishEvent(message);
                if (message.getMessageIdForEdit()!= null) {
                log.info("message id = " + message.getMessageIdForEdit());
                messages.put(userId,message.getMessageIdForEdit());
                } else {
                    throw  new RuntimeException("Не удалось получить message id");
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }


    }


}
