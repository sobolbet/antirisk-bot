/*package com.worldbet.antirisk_bot.db;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "payments")
public class PaymentEntity {

    @Id
    @Column (name = "id")
    private UUID id ;

    @ManyToOne
    @JoinColumn (name = "chat_id" , referencedColumnName = "chat_id")
    private UserEntity userEntity;

    // добавить enum со статусами позже

    @Column (name = "subscribe_start_dt")
    private LocalDateTime subscribeStartDt;

    @Column (name = "subscribe_end_dt")
    private LocalDateTime subscribeEndDt;

    @Column (name = "create_dt")
    private LocalDateTime createDt;

    @Column (name = "sum_pay", precision = 10 , scale = 5)
    private BigDecimal sumPay;
}
*/