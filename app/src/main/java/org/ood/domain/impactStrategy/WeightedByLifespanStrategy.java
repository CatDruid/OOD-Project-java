package org.ood.domain.impactStrategy;

import org.ood.domain.entities.ProductEntity;


/**
 * An {@link AbstractImpactStrategy} that weights the PCF by the product's estimated lifespan.
 */
public class WeightedByLifespanStrategy extends AbstractImpactStrategy{
    /**
     * It calculates a product's given impact varying per its lifespan.
     * @param product       The product whose environmental impact is being evaluated.
     * @return              The evaluated product carbon footprint with its lifespan factored in. Alternatively, 0 if the lifespan is non-positive somehow.
     */
    @Override
    public float CalculateImpact(ProductEntity product) {
        // Error handling if product is null or lifespan <= 0
        if(product == null || product.GetEstimatedLifeSpan() <= 0.0f) {return 0.0f;}
        return CalculateRawPCF(product) / product.GetEstimatedLifeSpan();
    }
}
