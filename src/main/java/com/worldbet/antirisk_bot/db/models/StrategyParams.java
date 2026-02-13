package com.worldbet.antirisk_bot.db.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;

public class StrategyParams {

    private Long amountRounds;

    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    private ArrayList<Pair> pairs;
}
