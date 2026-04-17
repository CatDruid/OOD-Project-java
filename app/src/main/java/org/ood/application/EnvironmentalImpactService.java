package org.ood.application;

import java.util.List;

import org.ood.domain.ImpactCalculationStrategy;
import org.ood.domain.RegistryInterface;
import org.ood.infrastructure.ProductRegistry;

public class EnvironmentalImpactService {
    private RegistryInterface<ProductRegistry> productRegistry;
    private List<ImpactCalculationStrategy> strategies;

    public float CalculateImpact(int productId, int strategyIndex) {
        return 0.0f;
    }
}
