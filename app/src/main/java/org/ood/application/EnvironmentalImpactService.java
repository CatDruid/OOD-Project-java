package org.ood.application;

import java.util.LinkedList;
import java.util.List;

import org.ood.domain.ImpactCalculationStrategy;
import org.ood.domain.RegistryInterface;
import org.ood.infrastructure.ProductRegistry;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalImpactService {
    private RegistryInterface<ProductRegistry> productRegistry;
    private List<ImpactCalculationStrategy> strategies;

    public ImpactResult CalculateImpact(int productId, int strategyIndex) {
        return new ImpactResult(1, "test", (float)0);
    }

    public List<String> GetStringStrategies() {
        return new LinkedList<>();
    }
}
