package org.ood.domain.recyclingStrategy;

import org.ood.domain.ProductEntity;

public interface GuidanceStrategy {
    String CalculateGuidance(ProductEntity product);
}
