package com.worldbet.antirisk_bot.db.models;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class CoefDto {


    private BigDecimal p1m;
    private BigDecimal p2m;
    private BigDecimal p1r;
    private BigDecimal p2r;
    private BigDecimal fat;
    private BigDecimal brut;
    private BigDecimal rut;
    private BigDecimal minTT;
    private BigDecimal tbMinTime;
    private BigDecimal tmMinTime;
    private BigDecimal midTT;
    private BigDecimal tbMidTime;
    private BigDecimal tmMidTime;
    private BigDecimal maxTT;
    private BigDecimal tbMaxTime;
    private BigDecimal tmMaxTime;
    private BigDecimal fatYes;
    private BigDecimal fatNo;
    private BigDecimal fw;
    private BigDecimal quantityRMinVal;
    private BigDecimal quantityRMidVal;
    private BigDecimal quantityRMaxVal;
    private BigDecimal tbQuantityRMin;
    private BigDecimal tbQuantityRMid;
    private BigDecimal tbQuantityRMax;
    private BigDecimal tmQuantityRMin;
    private BigDecimal tmQuantityRMid;
    private BigDecimal tmQuantityRMax;
    private Integer roundNum;


    @Override
    public String toString() {
        return "CoefDto{" +
                "p1m=" + p1m +
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
                ", roundNum=" + roundNum +
                '}';
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
        this.minTT = minTT.setScale(1, RoundingMode.HALF_UP);
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
        this.midTT = midTT.setScale(1, RoundingMode.HALF_UP);
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
        this.maxTT = maxTT.setScale(1, RoundingMode.HALF_UP);
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

    public BigDecimal getFw() {
        return fw;
    }

    public void setFw(BigDecimal fw) {
        this.fw = fw;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
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
}


