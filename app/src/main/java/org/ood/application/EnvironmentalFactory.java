package org.ood.application;

import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.domain.impactStrategy.ImpactCalculationStrategy;
import org.ood.domain.impactStrategy.SimpleSumStrategy;
import org.ood.domain.impactStrategy.WeightedByLifespanStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Factory class dedicated to the creation of {@link EnvironmentalImpactService} with it's befitting strategy.
 * @see EnvironmentalImpactService
 */
public class EnvironmentalFactory {

    /**
     * A static list of all {@link ImpactCalculationStrategy} that currently exist.
     * @see ImpactCalculationStrategy
     * @see SimpleSumStrategy
     * @see WeightedByLifespanStrategy
     */
    private static final List<ImpactCalculationStrategy> strategies = Arrays.asList(
            new SimpleSumStrategy(),
            new WeightedByLifespanStrategy());
    private final RegistryInterface<ProductEntity> productRegistry;

    /**
     * Initializes the environmental factory with the parameter it'll require to inject into the service.
     * @param productRegistry   A registry handling the in-memory storage of products.
     */
    public EnvironmentalFactory(RegistryInterface<ProductEntity> productRegistry) {
        this.productRegistry = productRegistry;
    }

    /**
     * Creates the Environmental Impact Service with the selected impact strategy.
     * @param strategyIndex     The index within the list of all impact strategies.
     * @return                  The service with the selected strategy.
     */
    public EnvironmentalImpactService create(int strategyIndex){
        return new EnvironmentalImpactService(productRegistry, strategies.get(strategyIndex));
    }

    /**
     * Retrieves the currently-existing strategies for visualization in the presentation layer.
     * @return                  A string with the current impact calculation strategies' names.
     */
    public static List<String> GetStringStrategies() {

        // If there are no strategies return null
        if (strategies.isEmpty()) {return new ArrayList<>();}

        // Get the simple name from all the strategies and add them to the list
        List<String> StringStrategies = new ArrayList<>();
        for (ImpactCalculationStrategy strategy : strategies) {
            StringStrategies.add(strategy.getClass().getSimpleName());
        }

        return StringStrategies;
    }

}
