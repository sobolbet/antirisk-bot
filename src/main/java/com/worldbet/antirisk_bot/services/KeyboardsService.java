package com.worldbet.antirisk_bot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class KeyboardsService {

    private final LocaleMessageService localeMessageService;


    public KeyboardsService (LocaleMessageService localeMessageService) {

        this.localeMessageService = localeMessageService;
    }



    public ReplyKeyboardMarkup getMainMenu (Locale userLocale) {



        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        KeyboardRow row6 = new KeyboardRow();
        KeyboardRow row7 = new KeyboardRow();
        KeyboardRow row8 = new KeyboardRow();
        KeyboardRow row9 = new KeyboardRow();
        KeyboardRow row10 = new KeyboardRow();
        KeyboardRow row11 = new KeyboardRow();
        row10.add(new KeyboardButton(localeMessageService.getMessage("menu.select_lang",userLocale)));
        row11.add(new KeyboardButton(localeMessageService.getMessage("menu.select_time_zone",userLocale)));
        row6.add(new KeyboardButton(localeMessageService.getMessage("menu.get_trial",userLocale)));
        row7.add(new KeyboardButton(localeMessageService.getMessage("menu.pay_subscribe",userLocale)));
        row1.add(new KeyboardButton(localeMessageService.getMessage("menu.timer_options",userLocale)));
        row5.add(new KeyboardButton(localeMessageService.getMessage("menu.bank_options",userLocale)));
        row9.add(new KeyboardButton(localeMessageService.getMessage("menu.select_strategy",userLocale)));
        row2.add(new KeyboardButton(localeMessageService.getMessage("menu.show_info",userLocale)));
        row3.add(new KeyboardButton(localeMessageService.getMessage("menu.start_timer",userLocale)));
        row4.add(new KeyboardButton(localeMessageService.getMessage("menu.stop_timer",userLocale)));
        row8.add(new KeyboardButton(localeMessageService.getMessage("menu.bots_instruction",userLocale)));
        //row4.add(new KeyboardButton(""));
        keyBoard.add(row10);
        keyBoard.add(row11);
        keyBoard.add(row6);
        keyBoard.add(row7);
        keyBoard.add(row1);
        keyBoard.add(row5);
        keyBoard.add(row9);
        keyBoard.add(row2);
        keyBoard.add(row3);
        keyBoard.add(row4);
        keyBoard.add(row8);
        //keyBoard.add(row5);
        //keyBoard.add(row6);
        replyKeyboard.setKeyboard(keyBoard);
        /*List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Принять участие в розыгрыше");
        inlineKeyboardButton2.setCallbackData("XMASDRAW");
        rowInLine2.add(inlineKeyboardButton2);*/
        //rowsInLine.add(rowInLine2);



        return replyKeyboard;
    }



    public ReplyKeyboardMarkup getRespYesOrNo (Locale userLocale) {


        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(true);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row1.add(new KeyboardButton(localeMessageService.getMessage("menu.update_bank",userLocale)));
        row2.add(new KeyboardButton(localeMessageService.getMessage("menu.not_update_bank",userLocale)));
        //row4.add(new KeyboardButton(""));
        keyBoard.add(row1);
        keyBoard.add(row2);
        //keyBoard.add(row5);
        //keyBoard.add(row6);
        replyKeyboard.setKeyboard(keyBoard);
        /*List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Принять участие в розыгрыше");
        inlineKeyboardButton2.setCallbackData("XMASDRAW");
        rowInLine2.add(inlineKeyboardButton2);*/
        //rowsInLine.add(rowInLine2);


        return replyKeyboard;
    }



    public ReplyKeyboardMarkup getLocaleKeyboard () {


        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(true);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        //KeyboardRow row2 = new KeyboardRow();

        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row1.add(new KeyboardButton("ru-RU"));
        row1.add(new KeyboardButton("en-US"));
        row1.add(new KeyboardButton("uz-UZ"));
        //row4.add(new KeyboardButton(""));
        keyBoard.add(row1);
        //keyBoard.add(row2);
        //keyBoard.add(row5);
        //keyBoard.add(row6);
        replyKeyboard.setKeyboard(keyBoard);



        return replyKeyboard;
    }



    public ReplyKeyboardMarkup getTimezoneKeyboard () {


        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(true);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        KeyboardRow row6 = new KeyboardRow();
        KeyboardRow row7 = new KeyboardRow();
        KeyboardRow row8 = new KeyboardRow();
        KeyboardRow row9 = new KeyboardRow();
        KeyboardRow row10 = new KeyboardRow();
        KeyboardRow row11 = new KeyboardRow();
        KeyboardRow row12 = new KeyboardRow();
        KeyboardRow row13 = new KeyboardRow();

        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row1.add(new KeyboardButton("UTC-12:00"));
        row1.add(new KeyboardButton("UTC−11:00"));
        row1.add(new KeyboardButton("UTC−10:00"));
        row2.add(new KeyboardButton("UTC−09:30"));
        row2.add(new KeyboardButton("UTC−09:00"));
        row2.add(new KeyboardButton("UTC−08:00"));
        row3.add(new KeyboardButton("UTC−07:00"));
        row3.add(new KeyboardButton("UTC−06:00"));
        row3.add(new KeyboardButton("UTC−05:00"));
        row4.add(new KeyboardButton("UTC−04:00"));
        row4.add(new KeyboardButton("UTC−03:30"));
        row4.add(new KeyboardButton("UTC−03:00"));
        row5.add(new KeyboardButton("UTC−02:00"));
        row5.add(new KeyboardButton("UTC−01:00"));
        row5.add(new KeyboardButton("UTC−00:00"));
        row6.add(new KeyboardButton("UTC+01:00"));
        row6.add(new KeyboardButton("UTC+02:00"));
        row6.add(new KeyboardButton("UTC+03:00"));
        row7.add(new KeyboardButton("UTC+03:30"));
        row7.add(new KeyboardButton("UTC+04:00"));
        row7.add(new KeyboardButton("UTC+04:30"));
        row8.add(new KeyboardButton("UTC+05:00"));
        row8.add(new KeyboardButton("UTC+05:30"));
        row8.add(new KeyboardButton("UTC+05:45"));
        row9.add(new KeyboardButton("UTC+06:00"));
        row9.add(new KeyboardButton("UTC+06:30"));
        row9.add(new KeyboardButton("UTC+07:00"));
        row10.add(new KeyboardButton("UTC+08:00"));
        row10.add(new KeyboardButton("UTC+08:45"));
        row10.add(new KeyboardButton("UTC+09:00"));
        row11.add(new KeyboardButton("UTC+09:30"));
        row11.add(new KeyboardButton("UTC+10:00"));
        row11.add(new KeyboardButton("UTC+10:30"));
        row12.add(new KeyboardButton("UTC+11:00"));
        row12.add(new KeyboardButton("UTC+12:00"));
        row12.add(new KeyboardButton("UTC+12:45"));
        row13.add(new KeyboardButton("UTC+13:00"));
        row13.add(new KeyboardButton("UTC+14:00"));
        //row4.add(new KeyboardButton(""));
        keyBoard.add(row1);
        keyBoard.add(row2);
        keyBoard.add(row3);
        keyBoard.add(row4);
        keyBoard.add(row5);
        keyBoard.add(row6);
        keyBoard.add(row7);
        keyBoard.add(row8);
        keyBoard.add(row9);
        keyBoard.add(row10);
        keyBoard.add(row11);
        keyBoard.add(row12);
        keyBoard.add(row13);
        replyKeyboard.setKeyboard(keyBoard);



        return replyKeyboard;
    }



    public ReplyKeyboardMarkup getListStartegy () {


        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(true);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row1.add(new KeyboardButton("FAT_DOGON_3_ROUNDS"));
        row2.add(new KeyboardButton("FAT_FLET_5_ROUNDS"));
        row3.add(new KeyboardButton("FAT_FB_3_ROUNDS"));
        //row4.add(new KeyboardButton(""));
        keyBoard.add(row1);
        keyBoard.add(row2);
        keyBoard.add(row3);
        //keyBoard.add(row5);
        //keyBoard.add(row6);
        replyKeyboard.setKeyboard(keyBoard);
        /*List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Принять участие в розыгрыше");
        inlineKeyboardButton2.setCallbackData("XMASDRAW");
        rowInLine2.add(inlineKeyboardButton2);*/
        //rowsInLine.add(rowInLine2);


        return replyKeyboard;
    }


}
