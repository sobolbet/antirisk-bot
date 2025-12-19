package com.worldbet.antirisk_bot.services;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.zone.ZoneRules;
import java.util.Date;

@Service
public class ConvertTimeService {


    public LocalTime convertTimeFromSourceToTarget(LocalTime sourceLocalTime, ZoneId sourceZoneId , ZoneId targetZoneId, LocalDate dateOffset) {

        ZoneRules sourceRules = sourceZoneId.getRules();
        ZoneRules targetRules = targetZoneId.getRules();

        ZoneOffset sourceOffset = sourceRules.getOffset(LocalDateTime.of(dateOffset,sourceLocalTime));
        ZoneOffset targetOffset = targetRules.getOffset(LocalDateTime.of(dateOffset,sourceLocalTime));

        Duration offsetDifference = Duration.ofSeconds(targetOffset.getTotalSeconds()- sourceOffset.getTotalSeconds());

        return sourceLocalTime.plus(offsetDifference);
    }

    public Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {

        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        Date date = Date.from(zonedDateTime.toInstant());

        return date;
    }




}
