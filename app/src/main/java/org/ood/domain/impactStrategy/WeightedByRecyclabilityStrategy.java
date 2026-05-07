package org.ood.domain.impactStrategy;

import org.ood.domain.ProductEntity;

public class WeightedByRecyclabilityStrategy extends AbstractImpactStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        // TODO method body; Needs recyclability score and end of life credit factor to work
        return  0.0f;
    }
}
