package com.worldbet.antirisk_bot.services;


import com.worldbet.antirisk_bot.controllers.AntiRiskBotCore;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

@Service
public class LocaleMessageService {

    private final MessageSource messageSource;


    public LocaleMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

    public String getMessage (String code, Locale locale) {
       return  messageSource.getMessage(code,null, locale);
    }

    public String getMessage (String code, Locale locale, Object[] args) {
        return  messageSource.getMessage(code,args,locale);
    }



}
