package com.worldbet.antirisk_bot.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

@Service
public class TimeZoneService {

    private  final Map<String,String> utcOffsetToTimezone;


    public TimeZoneService(Map<String, String> utcOffsetToTimezone) {
        this.utcOffsetToTimezone = initializeTimezoneMap();
    }


    private Map <String,String> initializeTimezoneMap () {
        return Map.ofEntries(
                Map.entry("UTC-12:00","GMT-12:00"),
                Map.entry("UTC−11:00","GMT-11:00"),
                Map.entry("UTC−10:00","GMT-10:00"),
                Map.entry("UTC−09:30","GMT-09:30"),
                Map.entry("UTC−09:00","GMT-09:00"),
                Map.entry("UTC−08:00","GMT-08:00"),
                Map.entry("UTC−07:00","GMT-07:00"),
                Map.entry("UTC−06:00","GMT-06:00"),
                Map.entry("UTC−05:00","GMT-05:00"),
                Map.entry("UTC−04:00","GMT-04:00"),
                Map.entry("UTC−03:30","GMT-03:30"),
                Map.entry("UTC−03:00","GMT-03:00"),
                Map.entry("UTC−02:00","GMT-02:00"),
                Map.entry("UTC−01:00","GMT-01:00"),
                Map.entry("UTC−00:00","GMT-00:00"),
                Map.entry("UTC+01:00","GMT+01:00"),
                Map.entry("UTC+02:00","GMT+02:00"),
                Map.entry("UTC+03:00","GMT+03:00"),
                Map.entry("UTC+03:30","GMT+03:30"),
                Map.entry("UTC+04:00","GMT+04:00"),
                Map.entry("UTC+04:30","GMT+04:30"),
                Map.entry("UTC+05:00","GMT+05:00"),
                Map.entry("UTC+05:30","GMT+05:30"),
                Map.entry("UTC+05:45","GMT+05:45"),
                Map.entry("UTC+06:00","GMT+06:00"),
                Map.entry("UTC+06:30","GMT+06:30"),
                Map.entry("UTC+07:00","GMT+07:00"),
                Map.entry("UTC+08:00","GMT+08:00"),
                Map.entry("UTC+08:45","GMT+08:45"),
                Map.entry("UTC+09:00","GMT+09:00"),
                Map.entry("UTC+09:30","GMT+09:30"),
                Map.entry("UTC+10:00","GMT+10:00"),
                Map.entry("UTC+10:30","GMT+10:30"),
                Map.entry("UTC+11:00","GMT+11:00"),
                Map.entry("UTC+12:00","GMT+12:00"),
                Map.entry("UTC+12:45","GMT+12:45"),
                Map.entry("UTC+13:00","GMT+13:00"),
                Map.entry("UTC+14:00","GMT+14:00")
        );
    }


    public Set getKeySet () {
        return utcOffsetToTimezone.keySet();
    }

    public String getStringTimezone (String telegramUtcOffset) {
        return utcOffsetToTimezone.get(telegramUtcOffset);
    }


    public TimeZone getTimeZone (String timezone) {

        return timezone != null ? TimeZone.getTimeZone(timezone) : null ;
    }

}
