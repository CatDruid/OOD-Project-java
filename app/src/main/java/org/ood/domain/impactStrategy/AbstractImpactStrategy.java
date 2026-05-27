package org.ood.domain.impactStrategy;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

public abstract class AbstractImpactStrategy implements ImpactCalculationStrategy{

    protected float CalculateRawPCF(ProductEntity product) {
        // Error handling, if product is null or if there are no materials in the product
        if(product == null || product.getMaterial().isEmpty()) {return 0.0f;}

        float sum = 0.0f;
        for(MaterialEntity material : product.getMaterial()) {
            sum += material.GetMass() * material.GetEmissionFactor();
        }
        return sum;
    }

    public abstract float CalculateImpact(ProductEntity product);
}
