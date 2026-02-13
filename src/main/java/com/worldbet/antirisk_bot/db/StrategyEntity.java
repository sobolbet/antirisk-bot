package com.worldbet.antirisk_bot.db;

import com.worldbet.antirisk_bot.db.models.StrategyParams;
import com.worldbet.antirisk_bot.services.StrategyParamsJsonbConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "strategies",schema = "users")
public class StrategyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "strategy_id")
    private Long id;

    @Column(name = "strategy_name", nullable = false,unique = true)
    private String name;

    @Column(name = "params", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = StrategyParamsJsonbConverter.class)
    private StrategyParams strategyParams;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StrategyParams getStrategyParams() {
        return strategyParams;
    }

    public void setStrategyParams(StrategyParams strategyParams) {
        this.strategyParams = strategyParams;
    }
}
