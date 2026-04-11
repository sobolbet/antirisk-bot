package com.worldbet.antirisk_bot.db;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "games", schema = "games")
public class GameEntity {


@Id
@GeneratedValue (generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
@Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
private UUID id;

@Column(name = "event_id")
private String eventId;

@Column (name = "time_ev")
private LocalTime timeEv;

@Column (name = "date_ev")
private LocalDate dateEv;

@Column (name = "game_num")
private String gameNum;

@Column (name = "f1")
private String f1;

@Column (name = "f2")
private String f2;

@Column (name = "total_f1")
private Integer totalF1;

@Column (name = "total_f2")
private Integer totalF2;


@Column (name = "game_was_end")
private Boolean gameWasEnd;

@Column (name = "r1_typewin_res")
private String r1TypeWinRes;

@Column (name = "r2_typewin_res")
private String r2TypeWinRes;

@Column (name = "r3_typewin_res")
private String r3TypeWinRes;

@Column (name = "r4_typewin_res")
private String r4TypeWinRes;

@Column (name = "r5_typewin_res")
private String r5TypeWinRes;

@Column (name = "r6_typewin_res")
private String r6TypeWinRes;

@Column (name = "r7_typewin_res")
private String r7TypeWinRes;

@Column (name = "r8_typewin_res")
private String r8TypeWinRes;

@Column (name = "r9_typewin_res")
private String r9TypeWinRes;

@Column (name = "r1_time_res")
private String r1TimeRes;

@Column (name = "r2_time_res")
private String r2TimeRes;

@Column (name = "r3_time_res")
private String r3TimeRes;

@Column (name = "r4_time_res")
private String r4TimeRes;

@Column (name = "r5_time_res")
private String r5TimeRes;

@Column (name = "r6_time_res")
private String r6TimeRes;

@Column (name = "r7_time_res")
private String r7TimeRes;

@Column (name = "r8_time_res")
private String r8TimeRes;

@Column (name = "r9_time_res")
private String r9TimeRes;

@Column (name = "r1_time")
private Integer r1Time;

@Column (name = "r2_time")
private Integer r2Time;

@Column (name = "r3_time")
private Integer r3Time;

@Column (name = "r4_time")
private Integer r4Time;

@Column (name = "r5_time")
private Integer r5Time;

@Column (name = "r6_time")
private Integer r6Time;

@Column (name = "r7_time")
private Integer r7Time;

@Column (name = "r8_time")
private Integer r8Time;

@Column (name = "r9_time")
private Integer r9Time;

@Column (name = "updated_at")
private LocalDateTime updatedAt;

@Column (name = "round_num_now")
private Integer roundNumNow;


    public GameEntity() {
    }

    public GameEntity(String eventId, LocalTime timeEv, String gameNum, LocalDate dateEv, String f1, String f2) {
        this.eventId = eventId;
        this.gameNum = gameNum;
        this.timeEv = timeEv;
        this.dateEv = dateEv;
        this.f1 = f1;
        this.f2 = f2;
        this.totalF1 = 0;
        this.totalF2 = 0;
        this.gameWasEnd = false;
        this.updatedAt = LocalDateTime.now();
        this.roundNumNow = 0;
    }


    /*@Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity game = (GameEntity) o;
        return Objects.equals(id, game.id) && Objects.equals(eventId, game.eventId)
                && Objects.equals(timeEv, game.timeEv)
                && Objects.equals(dateEv, game.dateEv)
                && Objects.equals(gameNum, game.gameNum)
                && Objects.equals(f1, game.f1)
                && Objects.equals(f2, game.f2)
                && Objects.equals(totalF1, game.totalF1)
                && Objects.equals(totalF2, game.totalF2)
                && Objects.equals(gameWasEnd, game.gameWasEnd)
                && Objects.equals(r1TypeWinRes, game.r1TypeWinRes)
                && Objects.equals(r2TypeWinRes, game.r2TypeWinRes)
                && Objects.equals(r3TypeWinRes, game.r3TypeWinRes)
                && Objects.equals(r4TypeWinRes, game.r4TypeWinRes)
                && Objects.equals(r5TypeWinRes, game.r5TypeWinRes)
                && Objects.equals(r6TypeWinRes, game.r6TypeWinRes)
                && Objects.equals(r7TypeWinRes, game.r7TypeWinRes)
                && Objects.equals(r8TypeWinRes, game.r8TypeWinRes)
                && Objects.equals(r9TypeWinRes, game.r9TypeWinRes)
                && Objects.equals(r1TimeRes, game.r1TimeRes)
                && Objects.equals(r2TimeRes, game.r2TimeRes)
                && Objects.equals(r3TimeRes, game.r3TimeRes)
                && Objects.equals(r4TimeRes, game.r4TimeRes)
                && Objects.equals(r5TimeRes, game.r5TimeRes)
                && Objects.equals(r6TimeRes, game.r6TimeRes)
                && Objects.equals(r7TimeRes, game.r7TimeRes)
                && Objects.equals(r8TimeRes, game.r8TimeRes)
                && Objects.equals(r9TimeRes, game.r9TimeRes)
                && Objects.equals(r1Time, game.r1Time)
                && Objects.equals(r2Time, game.r2Time)
                && Objects.equals(r3Time, game.r3Time)
                && Objects.equals(r4Time, game.r4Time)
                && Objects.equals(r5Time, game.r5Time)
                && Objects.equals(r6Time, game.r6Time)
                && Objects.equals(r7Time, game.r7Time)
                && Objects.equals(r8Time, game.r8Time)
                && Objects.equals(r9Time, game.r9Time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, timeEv, dateEv, gameNum, f1, f2, totalF1, totalF2, gameWasEnd, r1TypeWinRes,
                r2TypeWinRes, r3TypeWinRes, r4TypeWinRes, r5TypeWinRes, r6TypeWinRes, r7TypeWinRes, r8TypeWinRes,
                r9TypeWinRes, r1TimeRes, r2TimeRes, r3TimeRes, r4TimeRes, r5TimeRes, r6TimeRes, r7TimeRes, r8TimeRes,
                r9TimeRes, r1Time, r2Time, r3Time, r4Time, r5Time, r6Time, r7Time, r8Time, r9Time);
    }*/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalTime getTimeEv() {
        return timeEv;
    }

    public void setTimeEv(LocalTime timeEv) {
        this.timeEv = timeEv;
    }

    public LocalDate getDateEv() {
        return dateEv;
    }

    public void setDateEv(LocalDate dateEv) {
        this.dateEv = dateEv;
    }

    public String getGameNum() {
        return gameNum;
    }

    public void setGameNum(String gameNum) {
        this.gameNum = gameNum;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
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

    public String getR9TypeWinRes() {
        return r9TypeWinRes;
    }

    public void setR9TypeWinRes(String r9TypeWinRes) {
        this.r9TypeWinRes = r9TypeWinRes;
    }

    public String getR1TimeRes() {
        return r1TimeRes;
    }

    public void setR1TimeRes(String r1TimeRes) {
        this.r1TimeRes = r1TimeRes;
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

    public String getR8TimeRes() {
        return r8TimeRes;
    }

    public void setR8TimeRes(String r8TimeRes) {
        this.r8TimeRes = r8TimeRes;
    }

    public String getR9TimeRes() {
        return r9TimeRes;
    }

    public void setR9TimeRes(String r9TimeRes) {
        this.r9TimeRes = r9TimeRes;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRoundNumNow() {
        return roundNumNow;
    }

    public void setRoundNumNow(Integer roundNumNow) {
        this.roundNumNow = roundNumNow;
    }
}
