package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.StrategyEntity;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.StrategyService;
import com.worldbet.antirisk_bot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;
import java.util.Optional;

@Component
public class SelectedStrategyHandler implements InputMessageHandler{

    private static final Logger log = LoggerFactory.getLogger(SelectedStrategyHandler.class);
    private final UserService userService;
    private final StrategyService strategyService;
    private final LocaleMessageService localeMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GetMainMenuHandler getMainMenuHandler;

    public SelectedStrategyHandler(UserService userService, StrategyService strategyService, LocaleMessageService localeMessageService, ApplicationEventPublisher applicationEventPublisher, GetMainMenuHandler getMainMenuHandler) {
        this.userService = userService;
        this.strategyService = strategyService;
        this.localeMessageService = localeMessageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.getMainMenuHandler = getMainMenuHandler;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.CHOICE_STRATEGY;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        Optional<StrategyEntity> strategy = strategyService.findStrategyByName(message.getText());

        log.info("Вывожу состояние опшионал "  + strategy.isEmpty());

        if (strategy.isEmpty()) {
          replyToUser.setText(localeMessageService.getMessage("reply.wrongChoice",userLocale));
        } else {
            userService.saveUserStrategy(userId,strategy.get());
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.strategySelected",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);
        }


        replyToUser.setChatId(userId);

        return replyToUser;
    }
}
