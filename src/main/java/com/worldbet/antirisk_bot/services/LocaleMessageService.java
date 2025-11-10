package com.worldbet.antirisk_bot.services;


import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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



}
