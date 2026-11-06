package com.worldbet.antirisk_bot.handlers;

import com.worldbet.antirisk_bot.db.BotState;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.services.InstructionsService;
import com.worldbet.antirisk_bot.services.KeyboardsService;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class ViewInstructionHandler implements InputMessageHandler{

    private final UserService userService;
    private final LocaleMessageService localeMessageService;
    private final InstructionsService instructionService;



    public ViewInstructionHandler(UserService userService, KeyboardsService keyboardsService, LocaleMessageService localeMessageService, InstructionsService instructionService) {
        this.userService = userService;
        this.localeMessageService = localeMessageService;
        this.instructionService = instructionService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.INSTRUCTION;
    }

    @Override
    public SendMessage handle(Message message)  {

            Long userId = message.getChatId();
            UserEntity user = userService.findUserById(userId);
            SendMessage replyToUser = new SendMessage();
            Locale userLocale = Locale.forLanguageTag(user.getLocale());

            replyToUser.setText(instructionService.getInstruction(userLocale));
            replyToUser.setChatId(userId);

            return replyToUser;





    }
}
