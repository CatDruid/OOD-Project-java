package org.ood.application;

import org.ood.domain.ImpactCalculationStrategy;
import org.ood.domain.RegistryInterface;
import org.ood.infrastructure.ProductRegistry;

public class EnvironmentalImpactService {
    private RegistryInterface<ProductRegistry> productRegistry;
    private ImpactCalculationStrategy weightedByLifespanStrategy;
    private ImpactCalculationStrategy simpleSumStrategy;

    public float CalculateByWeighByLifespan(int id) {
        return 0.0f;
    }

    public float CalculateBySimpleLifespan(int id) {
        return 0.0f;
    }
}
