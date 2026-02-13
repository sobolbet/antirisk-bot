package com.worldbet.antirisk_bot.db.models;

import java.math.BigDecimal;

public class TotalTimeCoef {
    private BigDecimal time;

    private BigDecimal coef;

    public TotalTimeCoef(BigDecimal time, BigDecimal coef) {
        this.time = time;
        this.coef = coef;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public BigDecimal getCoef() {
        return coef;
    }

    public void setCoef(BigDecimal coef) {
        this.coef = coef;
    }
}
