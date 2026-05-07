package org.ood.domain.impactStrategy;

import org.ood.domain.entities.ProductEntity;

public interface ImpactCalculationStrategy {
    float CalculateImpact(ProductEntity product);
}
