package org.ood.domain.impactStrategy;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

public abstract class AbstractImpactStrategy implements ImpactCalculationStrategy{

    protected float CalculateRawPCF(ProductEntity product) {
        float sum = 0.0f;
        for(MaterialEntity material : product.getMaterial()) {
            sum += material.GetMass() * material.GetEmissionFactor();
        }
        return sum;
    }

    public abstract float CalculateImpact(ProductEntity product);
}
