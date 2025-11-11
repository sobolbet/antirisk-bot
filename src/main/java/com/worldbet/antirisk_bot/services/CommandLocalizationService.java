package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.configs.BotCommand;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CommandLocalizationService {
    private final LocaleMessageService localeMessageService;


    public CommandLocalizationService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }


    public BotCommand resolveCommand (String messageText, Locale userLocale) {
        for (BotCommand command : BotCommand.values()){
            String localized = localeMessageService.getMessage("menu."+ command.name().toLowerCase(),userLocale);

            if (localized.equalsIgnoreCase(messageText.trim())) {
                return command;
            }
        }

        return null;
    }

}
