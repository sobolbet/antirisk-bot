package com.worldbet.antirisk_bot.db;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_messages", schema = "users")
public class UserMessagesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private UserEntity user;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "message_id")
    private Integer messageId;

    @Column (name = "event_id")
    private String eventId;

    @Column (name = "round_num_now")
    private Integer roundNumNow;

    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;

    public UserMessagesEntity() {
        // обязательно пустой конструктор
    }


    public UserMessagesEntity( UserEntity user, String chatId, Integer messageId, String eventId, Integer roundNumNow, LocalDateTime lastUpdateAt) {
        this.user = user;
        this.chatId = chatId;
        this.messageId = messageId;
        this.eventId = eventId;
        this.roundNumNow = roundNumNow;
        this.lastUpdateAt = lastUpdateAt;
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

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getRoundNumNow() {
        return roundNumNow;
    }

    public void setRoundNumNow(Integer roundNumNow) {
        this.roundNumNow = roundNumNow;
    }

    public LocalDateTime getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(LocalDateTime lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
}
