package com.worldbet.antirisk_bot.services;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardsService {


    public static SendMessage getMainMenu (String chat_id) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Выберите нужную кнопку");

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
        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row6.add(new KeyboardButton("Получить триал"));
        row7.add(new KeyboardButton("Оплатить подписку"));
        row1.add(new KeyboardButton("Параметры таймера"));
        row5.add(new KeyboardButton("Параметры банка"));
        row9.add(new KeyboardButton("Выбрать стратегию"));
        row2.add(new KeyboardButton("Посмотреть информацию"));
        row3.add(new KeyboardButton("Запустить бота"));
        row4.add(new KeyboardButton("Остановить бота"));
        row8.add(new KeyboardButton("Инструкция к боту"));
        //row4.add(new KeyboardButton(""));
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

        message.enableMarkdown(true);
        message.setChatId(chat_id);
        message.setReplyMarkup(replyKeyboard);


        return message;
    }



    public static SendMessage getRespYesOrNo (String chat_id) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Хотите ли вы обновить основной (текущий) банк? " +
                "(если обновить, то расчёт прибыли начнётся от начального банка, который вы ввели только что)");

        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(false);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(true);

        List<KeyboardRow> keyBoard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        //KeyboardRow row5 = new KeyboardRow();
        //KeyboardRow row6 = new KeyboardRow();
        row1.add(new KeyboardButton("Обновить банк"));
        row2.add(new KeyboardButton("Не обновлять банк"));
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

        message.enableMarkdown(true);
        message.setChatId(chat_id);
        message.setReplyMarkup(replyKeyboard);


        return message;
    }


    public static SendMessage getListStartegy (String chat_id) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Выберите стратегию для продолжения");

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
        row1.add(new KeyboardButton("Догон до 3-го (№1)"));
        row2.add(new KeyboardButton("Флет до 5-го (№1)"));
        row3.add(new KeyboardButton("ФБ до 3-го (№1)"));
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

        message.enableMarkdown(true);
        message.setChatId(chat_id);
        message.setReplyMarkup(replyKeyboard);


        return message;
    }


}
