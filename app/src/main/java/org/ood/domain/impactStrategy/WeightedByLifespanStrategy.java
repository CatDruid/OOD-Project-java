package org.ood.domain.impactStrategy;

import org.ood.domain.ProductEntity;

public class WeightedByLifespanStrategy extends AbstractImpactStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        return CalculateRawPCF(product) / product.GetEstimatedLifeSpan();
    }
}
