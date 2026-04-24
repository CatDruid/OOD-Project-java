package org.ood.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ood.domain.*;
import org.ood.infrastructure.ProductRegistry;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalImpactService {
    private final RegistryInterface<ProductEntity> productRegistry;
    private final List<ImpactCalculationStrategy> strategies;
    private final List<String> StringStrategies;

    public EnvironmentalImpactService(ProductRegistry productRegistry) {
        this.productRegistry = productRegistry;
        StringStrategies = GenerateStringStrategies();
        strategies = Arrays.asList(new ImpactCalculationStrategy[]{
                new SimpleSumStrategy(),
                new WeightedByLifespanStrategy()
        });
    }

    public ImpactResult CalculateImpact(int productId, int strategyIndex) {
        // Get the correct strategy
        ImpactCalculationStrategy strategy = strategies.get(strategyIndex);

        // Get the product
        ProductEntity product = productRegistry.RetrieveByID(productId);

        // return the result record with calculated environmental impact and name and id
        return new ImpactResult(productId, product.GetName(), strategy.CalculateImpact(product));
    }

    private List<String> GenerateStringStrategies() {
        // If there are no strategies return null
        if (strategies == null || strategies.isEmpty()) {return new ArrayList<>();}

        // Get the simple name from all the strategies and add them to the list
        List<String> StringStrategies = new ArrayList<>();
        for (ImpactCalculationStrategy strategy : strategies) {
            StringStrategies.add(strategy.getClass().getSimpleName());
        }

        return StringStrategies;
    }

    public List<String> GetStringStrategies() {return StringStrategies;}
}
