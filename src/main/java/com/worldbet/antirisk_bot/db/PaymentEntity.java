package com.worldbet.antirisk_bot.db;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "payments",schema = "users")
public class PaymentEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id",columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id ;

    @ManyToOne
    @JoinColumn (name = "chat_id" , referencedColumnName = "chat_id")
    private UserEntity userEntity;

    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    // добавить enum со статусами позже

    @Column (name = "subscribe_start_dt")
    private LocalDateTime subscribeStartDt;

    @Column (name = "subscribe_end_dt")
    private LocalDateTime subscribeEndDt;

    @Column (name = "create_date")
    private LocalDateTime createDt;

    // Фактически полученная сумма за вычетом комиссии
    @Column (name = "sum_pay", precision = 10 , scale = 5)
    private BigDecimal sumPay;

    @Column (name = "count_of_months")
    private Integer countOfMonths;

    // Запрошенная сумма
    @Column(name = "invoice_amount", precision = 18, scale = 8)
    private BigDecimal invoiceAmount;

    // Комиссия CryptoBot
    @Column(name = "fee_amount", precision = 18, scale = 8)
    private BigDecimal feeAmount;

    @Column(name = "crypto_invoice_id", unique = true)
    private Long cryptoInvoiceId;

    @Column(name = "payment_url", columnDefinition = "TEXT")
    private String paymentUrl;


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
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

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public BigDecimal getSumPay() {
        return sumPay;
    }

    public void setSumPay(BigDecimal sumPay) {
        this.sumPay = sumPay;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCountOfMonths() {
        return countOfMonths;
    }

    public void setCountOfMonths(Integer countOfMonths) {
        this.countOfMonths = countOfMonths;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Long getCryptoInvoiceId() {
        return cryptoInvoiceId;
    }

    public void setCryptoInvoiceId(Long cryptoInvoiceId) {
        this.cryptoInvoiceId = cryptoInvoiceId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
