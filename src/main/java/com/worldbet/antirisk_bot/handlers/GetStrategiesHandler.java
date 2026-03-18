package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.StrategyService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class GetStrategiesHandler implements InputMessageHandler{

    private final StrategyService strategyService;
    private final KeyboardsService keyboardsService;
    private final UserService userService;
    private final LocaleMessageService localeMessageService;

    public GetStrategiesHandler(StrategyService strategyService, KeyboardsService keyboardsService, UserService userService, LocaleMessageService localeMessageService) {
        this.strategyService = strategyService;
        this.keyboardsService = keyboardsService;
        this.userService = userService;
        this.localeMessageService = localeMessageService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.GET_STRATEGIES;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user = userService.findUserById(userId);
        SendMessage replyToUser = new SendMessage();
        Locale userLocale = Locale.forLanguageTag(user.getLocale());

        replyToUser.setText(localeMessageService.getMessage("reply.choiceStrategy",userLocale));
        replyToUser.setReplyMarkup(keyboardsService.getListStartegy());
        replyToUser.setChatId(userId);

        BotState botState = BotState.CHOICE_STRATEGY;
        userService.saveBotState(userId,botState);
        //strategyService.showStrategy(1);


        return replyToUser;
    }
}
