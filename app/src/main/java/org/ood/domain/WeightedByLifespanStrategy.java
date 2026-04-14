package org.ood.domain;

public class WeightedByLifespanStrategy implements ImpactCalculationStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        return 0;
    }
}
