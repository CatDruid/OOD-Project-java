package org.ood.domain.impactStrategy;

import org.ood.domain.MaterialEntity;
import org.ood.domain.ProductEntity;

public class SimpleSumStrategy implements ImpactCalculationStrategy {
    @Override
    public float CalculateImpact(ProductEntity product) {
        float sum = 0.0f;

        for(MaterialEntity material : product.getMaterial()) {
            sum += (material.GetMass() * material.GetEmissionFactor());
        }

        return sum;
    }
}
