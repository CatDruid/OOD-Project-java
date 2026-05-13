package org.ood.domain.recyclingStrategy;

import org.ood.domain.entities.ProductEntity;

public interface GuidanceStrategy {
    String CalculateGuidance(ProductEntity product);
}
