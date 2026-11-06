package com.worldbet.antirisk_bot.services;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class InstructionsService {

    private final ResourceLoader resourceLoader;

    public InstructionsService(MessageSource instructionMessageSource, ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public String getInstruction (Locale locale) {

        // locale.toString() выдаст "ru_RU", "uz_UZ" и т.д.
        String lang = locale.toString();

        // 1. Пытаемся найти точный файл (например, instruction_ru_RU.txt)
        Resource resource = resourceLoader.getResource("classpath:instruction_" + lang + ".txt");

        // 2. Фолбэк 1: если ru_RU нет, ищем просто по языку (instruction_ru.txt)
        if (!resource.exists()) {
            resource = resourceLoader.getResource("classpath:instruction_" + locale.getLanguage() + ".txt");
        }

        // 3. Фолбэк 2: если и его нет, берем дефолтный файл без суффиксов (instruction.txt)
        if (!resource.exists()) {
            resource = resourceLoader.getResource("classpath:instruction.txt");
        }

        // Защита от падения Telegram-бота, если файлов вообще нет
        if (!resource.exists()) {
            return "Извините, инструкция на вашем языке временно недоступна.";
        }

        try {
            // Читаем весь текстовый файл целиком в одну строку
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "Ошибка при чтении файла инструкции.";
        }

    }


}
