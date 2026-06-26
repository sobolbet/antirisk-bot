package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.calculation_logic.UserFBBetService;
import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.MessageToSendEvent;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class DeleteHistoryBetsHandler implements InputMessageHandler{

    private final UserService userService;
    private final KeyboardsService keyboardsService;
    private final LocaleMessageService localeMessageService;
    private final GetMainMenuHandler getMainMenuHandler;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserFBBetService userFBBetService;

    public DeleteHistoryBetsHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService, GetMainMenuHandler getMainMenuHandler, ApplicationEventPublisher applicationEventPublisher, UserFBBetService userFBBetService) {
        this.userService = userService;
        this.keyboardsService = keyboardsService;
        this.localeMessageService = localeMessageService;
        this.getMainMenuHandler = getMainMenuHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userFBBetService = userFBBetService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.DELETE_HISTORY_BETS;
    }

    @Override
    public SendMessage handle(Message message) {

        Long userId = message.getChatId();
        UserEntity user  = userService.findUserById(userId);
        Locale userLocale = Locale.forLanguageTag(user.getLocale());
        SendMessage replyToUser = new SendMessage();


        if (message.getText().equals(localeMessageService.getMessage("menu.delete_bets_story",userLocale))) {
            userFBBetService.deleteByChatId(userId.toString());
            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.deletedBetsStory",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);


        }

        else if (message.getText().equals(localeMessageService.getMessage("menu.not_delete_bets_story",userLocale))) {

            BotState botState = BotState.GET_MAIN_MENU;
            userService.saveBotState(userId,botState);
            applicationEventPublisher.publishEvent(new MessageToSendEvent(this,userId,localeMessageService.getMessage("reply.notDeletedBetsStory",userLocale)));
            replyToUser = getMainMenuHandler.handle(message);

        } else {
            replyToUser.setText(localeMessageService.getMessage("reply.bankOptionNotSelected",userLocale));
            replyToUser.setReplyMarkup(keyboardsService.getRespYesOrNo(userLocale));

        }




        replyToUser.setChatId(userId);




        return replyToUser;
    }
}
