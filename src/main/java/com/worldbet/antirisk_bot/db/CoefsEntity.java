package com.worldbet.antirisk_bot.db;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coefs", schema = "games")
public class CoefsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    GameEntity game;


    @Column(name = "p1m")
    private BigDecimal p1m;
    @Column(name = "p2m")
    private BigDecimal p2m;
    @Column(name = "p1r")
    private BigDecimal p1r;
    @Column(name = "p2r")
    private BigDecimal p2r;
    @Column(name = "fat")
    private BigDecimal fat;
    @Column(name = "brut")
    private BigDecimal brut;
    @Column(name = "rut")
    private BigDecimal rut;
    @Column(name = "min_tt")
    private BigDecimal minTT;
    @Column(name = "tb_min_time")
    private BigDecimal tbMinTime;
    @Column(name = "tm_min_time")
    private BigDecimal tmMinTime;
    @Column(name = "mid_tt")
    private BigDecimal midTT;
    @Column(name = "tb_mid_time")
    private BigDecimal tbMidTime;
    @Column(name = "tm_mid_time")
    private BigDecimal tmMidTime;
    @Column(name = "max_tt")
    private BigDecimal maxTT;
    @Column(name = "tb_max_time")
    private BigDecimal tbMaxTime;
    @Column(name = "tm_max_time")
    private BigDecimal tmMaxTime;
    @Column(name = "fat_yes")
    private BigDecimal fatYes;
    @Column(name = "fat_no")
    private BigDecimal fatNo;
    @Column(name = "fw")
    private BigDecimal fw;
    @Column(name = "quantity_r_min_val")
    private BigDecimal quantityRMinVal;
    @Column(name = "quantity_r_mid_val")
    private BigDecimal quantityRMidVal;
    @Column(name = "quantity_r_max_val")
    private BigDecimal quantityRMaxVal;
    @Column(name = "tb_quantity_r_min")
    private BigDecimal tbQuantityRMin;
    @Column(name = "tb_quantity_r_mid")
    private BigDecimal tbQuantityRMid;
    @Column(name = "tb_quantity_r_max")
    private BigDecimal tbQuantityRMax;
    @Column(name = "tm_quantity_r_min")
    private BigDecimal tmQuantityRMin;
    @Column(name = "tm_quantity_r_mid")
    private BigDecimal tmQuantityRMid;
    @Column(name = "tm_quantity_r_max")
    private BigDecimal tmQuantityRMax;
    @Column(name = "create_dt")
    private LocalDateTime createDt;
    @Column (name = "round_num")
    private Integer roundNum;


    public CoefsEntity() {

    }

    public BigDecimal getFw() {
        return fw;
    }

    public void setFw(BigDecimal fw) {
        this.fw = fw;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public BigDecimal getP1m() {
        return p1m;
    }

    public void setP1m(BigDecimal p1m) {
        this.p1m = p1m;
    }

    public BigDecimal getP2m() {
        return p2m;
    }

    public void setP2m(BigDecimal p2m) {
        this.p2m = p2m;
    }

    public BigDecimal getP1r() {
        return p1r;
    }

    public void setP1r(BigDecimal p1r) {
        this.p1r = p1r;
    }

    public BigDecimal getP2r() {
        return p2r;
    }

    public void setP2r(BigDecimal p2r) {
        this.p2r = p2r;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }

    public BigDecimal getBrut() {
        return brut;
    }

    public void setBrut(BigDecimal brut) {
        this.brut = brut;
    }

    public BigDecimal getRut() {
        return rut;
    }

    public void setRut(BigDecimal rut) {
        this.rut = rut;
    }

    public BigDecimal getMinTT() {
        return minTT;
    }

    public void setMinTT(BigDecimal minTT) {
        this.minTT = minTT;
    }

    public BigDecimal getTbMinTime() {
        return tbMinTime;
    }

    public void setTbMinTime(BigDecimal tbMinTime) {
        this.tbMinTime = tbMinTime;
    }

    public BigDecimal getTmMinTime() {
        return tmMinTime;
    }

    public void setTmMinTime(BigDecimal tmMinTime) {
        this.tmMinTime = tmMinTime;
    }

    public BigDecimal getMidTT() {
        return midTT;
    }

    public void setMidTT(BigDecimal midTT) {
        this.midTT = midTT;
    }

    public BigDecimal getTbMidTime() {
        return tbMidTime;
    }

    public void setTbMidTime(BigDecimal tbMidTime) {
        this.tbMidTime = tbMidTime;
    }

    public BigDecimal getTmMidTime() {
        return tmMidTime;
    }

    public void setTmMidTime(BigDecimal tmMidTime) {
        this.tmMidTime = tmMidTime;
    }

    public BigDecimal getMaxTT() {
        return maxTT;
    }

    public void setMaxTT(BigDecimal maxTT) {
        this.maxTT = maxTT;
    }

    public BigDecimal getTbMaxTime() {
        return tbMaxTime;
    }

    public void setTbMaxTime(BigDecimal tbMaxTime) {
        this.tbMaxTime = tbMaxTime;
    }

    public BigDecimal getTmMaxTime() {
        return tmMaxTime;
    }

    public void setTmMaxTime(BigDecimal tmMaxTime) {
        this.tmMaxTime = tmMaxTime;
    }

    public BigDecimal getFatYes() {
        return fatYes;
    }

    public void setFatYes(BigDecimal fatYes) {
        this.fatYes = fatYes;
    }

    public BigDecimal getFatNo() {
        return fatNo;
    }

    public void setFatNo(BigDecimal fatNo) {
        this.fatNo = fatNo;
    }

    public BigDecimal getQuantityRMinVal() {
        return quantityRMinVal;
    }

    public void setQuantityRMinVal(BigDecimal quantityRMinVal) {
        this.quantityRMinVal = quantityRMinVal;
    }

    public BigDecimal getQuantityRMidVal() {
        return quantityRMidVal;
    }

    public void setQuantityRMidVal(BigDecimal quantityRMidVal) {
        this.quantityRMidVal = quantityRMidVal;
    }

    public BigDecimal getQuantityRMaxVal() {
        return quantityRMaxVal;
    }

    public void setQuantityRMaxVal(BigDecimal quantityRMaxVal) {
        this.quantityRMaxVal = quantityRMaxVal;
    }

    public BigDecimal getTbQuantityRMin() {
        return tbQuantityRMin;
    }

    public void setTbQuantityRMin(BigDecimal tbQuantityRMin) {
        this.tbQuantityRMin = tbQuantityRMin;
    }

    public BigDecimal getTbQuantityRMid() {
        return tbQuantityRMid;
    }

    public void setTbQuantityRMid(BigDecimal tbQuantityRMid) {
        this.tbQuantityRMid = tbQuantityRMid;
    }

    public BigDecimal getTbQuantityRMax() {
        return tbQuantityRMax;
    }

    public void setTbQuantityRMax(BigDecimal tbQuantityRMax) {
        this.tbQuantityRMax = tbQuantityRMax;
    }

    public BigDecimal getTmQuantityRMin() {
        return tmQuantityRMin;
    }

    public void setTmQuantityRMin(BigDecimal tmQuantityRMin) {
        this.tmQuantityRMin = tmQuantityRMin;
    }

    public BigDecimal getTmQuantityRMid() {
        return tmQuantityRMid;
    }

    public void setTmQuantityRMid(BigDecimal tmQuantityRMid) {
        this.tmQuantityRMid = tmQuantityRMid;
    }

    public BigDecimal getTmQuantityRMax() {
        return tmQuantityRMax;
    }

    public void setTmQuantityRMax(BigDecimal tmQuantityRMax) {
        this.tmQuantityRMax = tmQuantityRMax;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    @Override
    public String toString() {
        return "CoefsEntity{" +
                "id=" + id +
                ", p1m=" + p1m +
                ", p2m=" + p2m +
                ", p1r=" + p1r +
                ", p2r=" + p2r +
                ", fat=" + fat +
                ", brut=" + brut +
                ", rut=" + rut +
                ", minTT=" + minTT +
                ", tbMinTime=" + tbMinTime +
                ", tmMinTime=" + tmMinTime +
                ", midTT=" + midTT +
                ", tbMidTime=" + tbMidTime +
                ", tmMidTime=" + tmMidTime +
                ", maxTT=" + maxTT +
                ", tbMaxTime=" + tbMaxTime +
                ", tmMaxTime=" + tmMaxTime +
                ", fatYes=" + fatYes +
                ", fatNo=" + fatNo +
                ", fw=" + fw +
                ", quantityRMinVal=" + quantityRMinVal +
                ", quantityRMidVal=" + quantityRMidVal +
                ", quantityRMaxVal=" + quantityRMaxVal +
                ", tbQuantityRMin=" + tbQuantityRMin +
                ", tbQuantityRMid=" + tbQuantityRMid +
                ", tbQuantityRMax=" + tbQuantityRMax +
                ", tmQuantityRMin=" + tmQuantityRMin +
                ", tmQuantityRMid=" + tmQuantityRMid +
                ", tmQuantityRMax=" + tmQuantityRMax +
                ", createDt=" + createDt +
                ", roundNum=" + roundNum +
                '}';
    }
}
