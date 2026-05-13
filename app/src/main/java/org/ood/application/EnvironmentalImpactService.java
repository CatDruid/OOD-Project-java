package org.ood.application;
import org.ood.domain.*;
import org.ood.domain.entities.ProductEntity;
import org.ood.domain.impactStrategy.ImpactCalculationStrategy;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalImpactService {
    private final RegistryInterface<ProductEntity> productRegistry;
    private final ImpactCalculationStrategy strategy;

    public EnvironmentalImpactService(RegistryInterface<ProductEntity> productRegistry, ImpactCalculationStrategy strategy) {
        this.productRegistry = productRegistry;
        this.strategy = strategy;
    }

    public ImpactResult CalculateImpact(int productId, int strategyIndex) {
        // Get the product
        ProductEntity product = productRegistry.RetrieveByID(productId);

        // return the result record with calculated environmental impact and name and id
        return new ImpactResult(productId, product.GetName(), strategy.CalculateImpact(product));
    }
}
