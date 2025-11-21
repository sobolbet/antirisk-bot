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
                Map.entry("UTC-12:00","Pacific/Kwajalein"),
                Map.entry("UTC−11:00","Pacific/Midway"),
                Map.entry("UTC−10:00","Pacific/Honolulu"),
                Map.entry("UTC−09:30","Pacific/Marquesas"),
                Map.entry("UTC−09:00","America/Anchorage"),
                Map.entry("UTC−08:00","America/Los_Angeles"),
                Map.entry("UTC−07:00","America/Denver"),
                Map.entry("UTC−06:00","America/Chicago"),
                Map.entry("UTC−05:00","America/New_York"),
                Map.entry("UTC−04:00","America/Santiago"),
                Map.entry("UTC−03:30","America/St_Johns"),
                Map.entry("UTC−03:00","America/Sao_Paulo"),
                Map.entry("UTC−02:00","Atlantic/South_Georgia"),
                Map.entry("UTC−01:00","Atlantic/Azores"),
                Map.entry("UTC−00:00","Europe/London"),
                Map.entry("UTC+01:00","Europe/Berlin"),
                Map.entry("UTC+02:00","Europe/Kiev"),
                Map.entry("UTC+03:00","Europe/Moscow"),
                Map.entry("UTC+03:30","Asia/Tehran"),
                Map.entry("UTC+04:00","Asia/Dubai"),
                Map.entry("UTC+04:30","Asia/Kabul"),
                Map.entry("UTC+05:00","Asia/Karachi"),
                Map.entry("UTC+05:30","Asia/Kolkata"),
                Map.entry("UTC+05:45","Asia/Kathmandu"),
                Map.entry("UTC+06:00","Asia/Almaty"),
                Map.entry("UTC+06:30","Asia/Yangon"),
                Map.entry("UTC+07:00","Asia/Bangkok"),
                Map.entry("UTC+08:00","Asia/Shanghai"),
                Map.entry("UTC+08:45","Australia/Eucla"),
                Map.entry("UTC+09:00","Asia/Tokyo"),
                Map.entry("UTC+09:30","Australia/Darwin"),
                Map.entry("UTC+10:00","Australia/Sydney"),
                Map.entry("UTC+10:30","Australia/Lord_Howe"),
                Map.entry("UTC+11:00","Pacific/Guadalcanal"),
                Map.entry("UTC+12:00","Pacific/Auckland"),
                Map.entry("UTC+12:45","Pacific/Chatham"),
                Map.entry("UTC+13:00","Pacific/Tongatapu"),
                Map.entry("UTC+14:00","Pacific/Kiritimati")
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
