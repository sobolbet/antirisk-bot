package com.worldbet.antirisk_bot.calculation_logic;


import com.worldbet.antirisk_bot.db.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_fb_bet", schema = "users")
public class UserFBBetEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @JoinColumn (name = "user_id")
    @ManyToOne (fetch = FetchType.LAZY)
    private UserEntity user;

    @Column (name = "chat_id")
    private String chatId;


    @Column (name = "bet_amount")
    private Double betAmount;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    public UserFBBetEntity () {

    }

    public UserFBBetEntity(UserEntity user, String chatId, Double betAmount, LocalDateTime createdAt) {
        this.user = user;
        this.chatId = chatId;
        this.betAmount = betAmount;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
