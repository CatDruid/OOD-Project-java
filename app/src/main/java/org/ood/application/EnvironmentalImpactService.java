package org.ood.application;
import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.domain.impactStrategy.ImpactCalculationStrategy;
import org.ood.presentation.records.Results.ImpactResult;

/**
 * The service responsible for calculating the environmental impact of a product.
 */
public class EnvironmentalImpactService {
    private final RegistryInterface<ProductEntity> productRegistry;
    private final ImpactCalculationStrategy strategy;

    /**
     * Constructs the Environmental Impact.
     * @param productRegistry           The in-memory storage of products, to be utilized in the operations requiring them.
     * @param strategy                  The strategy implementation to calculate their environmental impact.
     */
    public EnvironmentalImpactService(RegistryInterface<ProductEntity> productRegistry, ImpactCalculationStrategy strategy) {
        this.productRegistry = productRegistry;
        this.strategy = strategy;
    }

    /**
     * Calculates a product's environmental impact.
     * @param productId                 The ID of the product whose impact is being calculated
     * @return                          An object with the calculated impact value, alongside ID and name of the product.
     */
    public ImpactResult CalculateImpact(int productId) {
        // Get the product
        ProductEntity product = productRegistry.RetrieveByID(productId);

        // return the result record with calculated environmental impact and name and id
        return new ImpactResult(productId, product.GetName(), strategy.CalculateImpact(product));
    }
}
