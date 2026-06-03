package org.ood.domain.impactStrategy;

import org.ood.domain.entities.ProductEntity;

/**
 * Provides the contract for calculating the environmental impact of a product.
 * @see AbstractImpactStrategy
 * @see SimpleSumStrategy
 * @see WeightedByLifespanStrategy
 * */
public interface ImpactCalculationStrategy {
    /**
     * Calculates the environmental impact of a product as expressed in a numerical value.
     * @param product       The product whose impact is being evaluated.
     * @return              The calculated number representing the product's environmental impact, as formulated per each strategy
     */
    float CalculateImpact(ProductEntity product);
}
