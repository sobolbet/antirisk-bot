package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.models.StrategyParams;

public class StrategyParamsJsonbConverter extends JsonbConverter<StrategyParams>{

    public StrategyParamsJsonbConverter () {
        super(StrategyParams.class);
    }
}
