package org.ood.domain.impactStrategy;

import org.ood.domain.ProductEntity;

public interface ImpactCalculationStrategy {
    float CalculateImpact(ProductEntity product);
}
