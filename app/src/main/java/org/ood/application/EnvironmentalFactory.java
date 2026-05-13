package org.ood.application;

import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.domain.impactStrategy.ImpactCalculationStrategy;
import org.ood.domain.impactStrategy.SimpleSumStrategy;
import org.ood.domain.impactStrategy.WeightedByLifespanStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnvironmentalFactory {

    private static final List<ImpactCalculationStrategy> strategies = Arrays.asList(
            new SimpleSumStrategy(),
            new WeightedByLifespanStrategy());
    public static RegistryInterface<ProductEntity> productRegistry;

    public static EnvironmentalImpactService create(int strategyIndex){
        return new EnvironmentalImpactService(productRegistry, strategies.get(strategyIndex));
    }

    public static List<String> GetStringStrategies() {

        // If there are no strategies return null
        if (strategies == null || strategies.isEmpty()) {return new ArrayList<>();}

        // Get the simple name from all the strategies and add them to the list
        List<String> StringStrategies = new ArrayList<>();
        for (ImpactCalculationStrategy strategy : strategies) {
            StringStrategies.add(strategy.getClass().getSimpleName());
        }

        return StringStrategies;
    }

}
