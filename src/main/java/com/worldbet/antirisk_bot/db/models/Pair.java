package com.worldbet.antirisk_bot.db.models;

public class Pair {

    private String f1;

    private String f2;

    private String typeWin;

    private String resTimeType;

    private Double coefF ;

    private Boolean moreCoefF;

    private Boolean lessCoefF;

    private Double coefB;

    private Boolean moreCoefB;

    private Boolean lessCoefB;

    private Double coefR;

    private Boolean moreCoefR;

    private Boolean lessCoefR;

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

    public String getTypeWin() {
        return typeWin;
    }

    public void setTypeWin(String typeWin) {
        this.typeWin = typeWin;
    }

    public String getResTimeType() {
        return resTimeType;
    }

    public void setResTimeType(String resTimeType) {
        this.resTimeType = resTimeType;
    }

    public Double getCoefF() {
        return coefF;
    }

    public void setCoefF(Double coefF) {
        this.coefF = coefF;
    }

    public Boolean getMoreCoefF() {
        return moreCoefF;
    }

    public void setMoreCoefF(Boolean moreCoefF) {
        this.moreCoefF = moreCoefF;
    }

    public Boolean getLessCoefF() {
        return lessCoefF;
    }

    public void setLessCoefF(Boolean lessCoefF) {
        this.lessCoefF = lessCoefF;
    }

    public Double getCoefB() {
        return coefB;
    }

    public void setCoefB(Double coefB) {
        this.coefB = coefB;
    }

    public Boolean getMoreCoefB() {
        return moreCoefB;
    }

    public void setMoreCoefB(Boolean moreCoefB) {
        this.moreCoefB = moreCoefB;
    }

    public Boolean getLessCoefB() {
        return lessCoefB;
    }

    public void setLessCoefB(Boolean lessCoefB) {
        this.lessCoefB = lessCoefB;
    }

    public Double getCoefR() {
        return coefR;
    }

    public void setCoefR(Double coefR) {
        this.coefR = coefR;
    }

    public Boolean getMoreCoefR() {
        return moreCoefR;
    }

    public void setMoreCoefR(Boolean moreCoefR) {
        this.moreCoefR = moreCoefR;
    }

    public Boolean getLessCoefR() {
        return lessCoefR;
    }

    public void setLessCoefR(Boolean lessCoefR) {
        this.lessCoefR = lessCoefR;
    }
}
