package org.ood.domain.impactStrategy;

import org.ood.domain.MaterialEntity;
import org.ood.domain.ProductEntity;

public abstract class AbstractImpactStrategy implements ImpactCalculationStrategy{

    protected float CalculateRawPCF(ProductEntity product) {
        float sum = 0.0f;
        for(MaterialEntity material : product.getMaterial()) {
            sum += material.GetMass() * material.GetEmissionFactor();
        }
        return  sum;
    }

    public float CalculateImpact(ProductEntity product) {
        return 0.0f;
    }
}
