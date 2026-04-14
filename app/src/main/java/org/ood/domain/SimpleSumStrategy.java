package org.ood.domain;

public class SimpleSumStrategy implements ImpactCalculationStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        return 0;
    }
}
