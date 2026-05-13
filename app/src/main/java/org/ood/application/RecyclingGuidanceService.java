package org.ood.application;

import org.ood.domain.recyclingStrategy.GuidanceStrategy;
import org.ood.domain.RepositoryInterface;
import org.ood.infrastructure.repositories.ProductRepository;

public class RecyclingGuidanceService {
    private RepositoryInterface<ProductRepository> productRepository;
    private GuidanceStrategy defaultStrategy;

    public String UtilizeDefaultStrategy(int id) {return "";}

}
