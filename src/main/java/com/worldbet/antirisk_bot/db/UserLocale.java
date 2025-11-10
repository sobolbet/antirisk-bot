package com.worldbet.antirisk_bot.db;

public enum UserLocale {
    RU("ru-RU"),
    EN("en-US"),
    UZ("uz-UZ")
    ;

    private final String locale;

    UserLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale(){
        return locale;
    }
}
