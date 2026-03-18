package com.worldbet.antirisk_bot.db.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;

public class StrategyParams {

    private Long amountRounds;

    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    private ArrayList<Pair> pairs;


    public Long getAmountRounds() {
        return amountRounds;
    }

    public void setAmountRounds(Long amountRounds) {
        this.amountRounds = amountRounds;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<Pair> pairs) {
        this.pairs = pairs;
    }

    @Override
    public String toString() {
        return "StrategyParams{" +
                "amountRounds=" + amountRounds +
                ", strategyType=" + strategyType +
                ", pairs=" + pairs.toString() +
                '}';
    }
}
