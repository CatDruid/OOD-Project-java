package org.ood.domain.impactStrategy;

import org.ood.domain.entities.ProductEntity;

/**
 * The following {@link AbstractImpactStrategy} calculates the product carbon footprint through the simple sum formula.
 */
public class SimpleSumStrategy extends AbstractImpactStrategy {
    /**
     * The formula simply understands a product's carbon footprint as it's straightforward calculation without any additions atop.
     * @param product       The product whose impact is being evaluated.
     * @return              The raw carbon footprint of the product.
     * @see AbstractImpactStrategy#CalculateRawPCF(ProductEntity) 
     */
    @Override
    public float CalculateImpact(ProductEntity product) {
        return CalculateRawPCF(product);
    }
}
