package com.worldbet.antirisk_bot.db;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users",schema = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id",columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username")
    private String userName;

    @Column (name = "chat_id")
    private String chatId;

    @Enumerated(EnumType.STRING)
    @Column (name = "state")
    private BotState state;

    @Column (name = "trial")
    private Boolean trial;

    @Column (name = "local_time")
    private LocalTime localTime;

    @Column (name = "timezone")
    private String timeZone;

    @Column (name = "moscow_time")
    private LocalTime moscowTime;

    @Column (name = "time_job")
    private Long timeJob;

    @Column (name = "trial_start_dt")
    private LocalDateTime trialStartDt;

    @Column (name = "trial_end_dt")
    private LocalDateTime trialEndDt;

    @Column (name = "subscribe_start_dt")
    private LocalDateTime subscribeStartDt;

    @Column (name = "subscribe_end_dt")
    private LocalDateTime subscribeEndDt;

    @Column (name = "update_date")
    private LocalDateTime updateDt;

    @Column (name = "strategy_id")
    private Integer strategyId;

   /* @OneToMany (mappedBy = "userEntity")
    private List<PaymentEntity> paymentsList;*/


    public UserEntity() {
    }

    public UserEntity(String userName, String chatId) {
        this.userName = userName;
        this.chatId = chatId;
    }


 /*   public List<PaymentEntity> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<PaymentEntity> paymentsList) {
        this.paymentsList = paymentsList;
    }*/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public BotState getState() {
        return state;
    }

    public void setState(BotState state) {
        this.state = state;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
        this.trial = trial;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalTime getMoscowTime() {
        return moscowTime;
    }

    public void setMoscowTime(LocalTime moscowTime) {
        this.moscowTime = moscowTime;
    }

    public Long getTimeJob() {
        return timeJob;
    }

    public void setTimeJob(Long timeJob) {
        this.timeJob = timeJob;
    }

    public LocalDateTime getTrialStartDt() {
        return trialStartDt;
    }

    public void setTrialStartDt(LocalDateTime trialStartDt) {
        this.trialStartDt = trialStartDt;
    }

    public LocalDateTime getTrialEndDt() {
        return trialEndDt;
    }

    public void setTrialEndDt(LocalDateTime trialEndDt) {
        this.trialEndDt = trialEndDt;
    }

    public LocalDateTime getSubscribeStartDt() {
        return subscribeStartDt;
    }

    public void setSubscribeStartDt(LocalDateTime subscribeStartDt) {
        this.subscribeStartDt = subscribeStartDt;
    }

    public LocalDateTime getSubscribeEndDt() {
        return subscribeEndDt;
    }

    public void setSubscribeEndDt(LocalDateTime subscribeEndDt) {
        this.subscribeEndDt = subscribeEndDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }
}
