package org.ood.domain.impactStrategy;

import org.ood.domain.entities.MaterialEntity;
import org.ood.domain.entities.ProductEntity;

/**
 * Base implementation of {@link ImpactCalculationStrategy} that is intended to provide a common method to calculate the PCF for various implementations.
 * @see SimpleSumStrategy
 * @see WeightedByLifespanStrategy
 */
public abstract class AbstractImpactStrategy implements ImpactCalculationStrategy{

    /**
     * Calculates the raw carbon footprint of the product per GHG Protocol standards, which is utilized in several later calculations.
     * @param product       The product whose raw carbon footprint is being evaluated.
     * @return              The raw product carbon footprint evaluated without any additional considerations atop (e.g. lifespan). Alternatively, 0 if it somehow contains no materials.
     */
    protected float CalculateRawPCF(ProductEntity product) {
        // Error handling, if product is null or if there are no materials in the product
        if(product == null || product.getMaterial().isEmpty()) {return 0.0f;}

        float sum = 0.0f;
        for(MaterialEntity material : product.getMaterial()) {
            sum += material.GetMass() * material.GetEmissionFactor();
        }
        return sum;
    }

    /** {@inheritDoc} */
    public abstract float CalculateImpact(ProductEntity product);
}
