package org.ood.domain.impactStrategy;

import org.ood.domain.entities.ProductEntity;

public class WeightedByLifespanStrategy extends AbstractImpactStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        // Error handling if product is null or lifespan <= 0
        if(product == null || product.GetEstimatedLifeSpan() <= 0.0f) {return 0.0f;}
        return CalculateRawPCF(product) / product.GetEstimatedLifeSpan();
    }
}
