package org.ood.application;

import org.ood.domain.GuidanceStrategy;
import org.ood.domain.RepositoryInterface;
import org.ood.infrastructure.ProductRepository;

public class RecyclingGuidanceService {
    private RepositoryInterface<ProductRepository> productRepository;
    private GuidanceStrategy defaultStrategy;

    public String UtilizeDefaultStrategy(int id) {return "";}

}
