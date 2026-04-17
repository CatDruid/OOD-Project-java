package org.ood.application;

import org.ood.domain.ImpactCalculationStrategy;
import org.ood.domain.RegistryInterface;
import org.ood.infrastructure.ProductRegistry;

public class EnvironmentalImpactService {
    private RegistryInterface<ProductRegistry> productRegistry;
    private ImpactCalculationStrategy weightedByLifespanStrategy;
    private ImpactCalculationStrategy simpleSumStrategy;
}
