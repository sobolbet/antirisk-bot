package com.worldbet.antirisk_bot.db.models;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;

public class GameDto {

    private String eventId;

    private String gameNum;

    private String f1;

    private String f2;

    private Integer totalF1;

    private Integer totalF2;

    private Boolean gameWasEnd;

    private String r1TypeWinRes;

    private String r2TypeWinRes;

    private String r3TypeWinRes;

    private String r4TypeWinRes;

    private String r5TypeWinRes;

    private String r6TypeWinRes;

    private String r7TypeWinRes;

    private String r8TypeWinRes;

    private String r9TypeWinRes;

    private String r1TimeRes;

    private String r2TimeRes;

    private String r3TimeRes;

    private String r4TimeRes;

    private String r5TimeRes;

    private String r6TimeRes;

    private String r7TimeRes;

    private String r8TimeRes;

    private String r9TimeRes;

    private Integer r1Time;

    private Integer r2Time;

    private Integer r3Time;

    private Integer r4Time;

    private Integer r5Time;

    private Integer r6Time;

    private Integer r7Time;

    private Integer r8Time;

    private Integer r9Time;

    private Integer roundNumNow;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getGameNum() {
        return gameNum;
    }

    public void setGameNum(String gameNum) {
        this.gameNum = gameNum;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public Integer getTotalF1() {
        return totalF1;
    }

    public void setTotalF1(Integer totalF1) {
        this.totalF1 = totalF1;
    }

    public Integer getTotalF2() {
        return totalF2;
    }

    public void setTotalF2(Integer totalF2) {
        this.totalF2 = totalF2;
    }

    public Boolean getGameWasEnd() {
        return gameWasEnd;
    }

    public void setGameWasEnd(Boolean gameWasEnd) {
        this.gameWasEnd = gameWasEnd;
    }

    public String getR1TypeWinRes() {
        return r1TypeWinRes;
    }

    public void setR1TypeWinRes(String r1TypeWinRes) {
        this.r1TypeWinRes = r1TypeWinRes;
    }

    public String getR2TypeWinRes() {
        return r2TypeWinRes;
    }

    public void setR2TypeWinRes(String r2TypeWinRes) {
        this.r2TypeWinRes = r2TypeWinRes;
    }

    public String getR3TypeWinRes() {
        return r3TypeWinRes;
    }

    public void setR3TypeWinRes(String r3TypeWinRes) {
        this.r3TypeWinRes = r3TypeWinRes;
    }

    public String getR4TypeWinRes() {
        return r4TypeWinRes;
    }

    public void setR4TypeWinRes(String r4TypeWinRes) {
        this.r4TypeWinRes = r4TypeWinRes;
    }

    public String getR5TypeWinRes() {
        return r5TypeWinRes;
    }

    public void setR5TypeWinRes(String r5TypeWinRes) {
        this.r5TypeWinRes = r5TypeWinRes;
    }

    public String getR6TypeWinRes() {
        return r6TypeWinRes;
    }

    public void setR6TypeWinRes(String r6TypeWinRes) {
        this.r6TypeWinRes = r6TypeWinRes;
    }

    public String getR7TypeWinRes() {
        return r7TypeWinRes;
    }

    public void setR7TypeWinRes(String r7TypeWinRes) {
        this.r7TypeWinRes = r7TypeWinRes;
    }

    public String getR8TypeWinRes() {
        return r8TypeWinRes;
    }

    public void setR8TypeWinRes(String r8TypeWinRes) {
        this.r8TypeWinRes = r8TypeWinRes;
    }

    public String getR1TimeRes() {
        return r1TimeRes;
    }

    public void setR1TimeRes(String r1TimeRes) {
        this.r1TimeRes = r1TimeRes;
    }

    public String getR9TypeWinRes() {
        return r9TypeWinRes;
    }

    public void setR9TypeWinRes(String r9TypeWinRes) {
        this.r9TypeWinRes = r9TypeWinRes;
    }

    public String getR2TimeRes() {
        return r2TimeRes;
    }

    public void setR2TimeRes(String r2TimeRes) {
        this.r2TimeRes = r2TimeRes;
    }

    public String getR3TimeRes() {
        return r3TimeRes;
    }

    public void setR3TimeRes(String r3TimeRes) {
        this.r3TimeRes = r3TimeRes;
    }

    public String getR4TimeRes() {
        return r4TimeRes;
    }

    public void setR4TimeRes(String r4TimeRes) {
        this.r4TimeRes = r4TimeRes;
    }

    public String getR5TimeRes() {
        return r5TimeRes;
    }

    public void setR5TimeRes(String r5TimeRes) {
        this.r5TimeRes = r5TimeRes;
    }

    public String getR6TimeRes() {
        return r6TimeRes;
    }

    public void setR6TimeRes(String r6TimeRes) {
        this.r6TimeRes = r6TimeRes;
    }

    public String getR7TimeRes() {
        return r7TimeRes;
    }

    public void setR7TimeRes(String r7TimeRes) {
        this.r7TimeRes = r7TimeRes;
    }

    public String getR9TimeRes() {
        return r9TimeRes;
    }

    public void setR9TimeRes(String r9TimeRes) {
        this.r9TimeRes = r9TimeRes;
    }

    public String getR8TimeRes() {
        return r8TimeRes;
    }

    public void setR8TimeRes(String r8TimeRes) {
        this.r8TimeRes = r8TimeRes;
    }

    public Integer getR1Time() {
        return r1Time;
    }

    public void setR1Time(Integer r1Time) {
        this.r1Time = r1Time;
    }

    public Integer getR2Time() {
        return r2Time;
    }

    public void setR2Time(Integer r2Time) {
        this.r2Time = r2Time;
    }

    public Integer getR3Time() {
        return r3Time;
    }

    public void setR3Time(Integer r3Time) {
        this.r3Time = r3Time;
    }

    public Integer getR4Time() {
        return r4Time;
    }

    public void setR4Time(Integer r4Time) {
        this.r4Time = r4Time;
    }

    public Integer getR5Time() {
        return r5Time;
    }

    public void setR5Time(Integer r5Time) {
        this.r5Time = r5Time;
    }

    public Integer getR6Time() {
        return r6Time;
    }

    public void setR6Time(Integer r6Time) {
        this.r6Time = r6Time;
    }

    public Integer getR7Time() {
        return r7Time;
    }

    public void setR7Time(Integer r7Time) {
        this.r7Time = r7Time;
    }

    public Integer getR8Time() {
        return r8Time;
    }

    public void setR8Time(Integer r8Time) {
        this.r8Time = r8Time;
    }

    public Integer getR9Time() {
        return r9Time;
    }

    public void setR9Time(Integer r9Time) {
        this.r9Time = r9Time;
    }

    public Integer getRoundNumNow() {
        return roundNumNow;
    }

    public void setRoundNumNow(Integer roundNumNow) {
        this.roundNumNow = roundNumNow;
    }
}
