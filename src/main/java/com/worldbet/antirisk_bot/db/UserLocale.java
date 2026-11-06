package com.worldbet.antirisk_bot.db;

public enum UserLocale {
    RU("ru-RU"),
    EN("en-US"),
    UZ("uz-UZ"),
    KZ("kk-KZ"),
    TJ("tg-TJ")
    ;

    private final String locale;

    UserLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale(){
        return locale;
    }
}
