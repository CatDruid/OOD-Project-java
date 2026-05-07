package org.ood.domain.impactStrategy;

import org.ood.domain.ProductEntity;

public class SimpleSumStrategy extends AbstractImpactStrategy{
    @Override
    public float CalculateImpact(ProductEntity product) {
        return CalculateRawPCF(product);
    }
}
